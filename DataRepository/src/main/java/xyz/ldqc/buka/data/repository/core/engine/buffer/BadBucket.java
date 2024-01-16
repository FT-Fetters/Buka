package xyz.ldqc.buka.data.repository.core.engine.buffer;

import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import xyz.ldqc.buka.data.repository.core.engine.query.Conditional;
import xyz.ldqc.buka.data.repository.core.engine.query.Sieve;
import xyz.ldqc.buka.data.repository.core.engine.structure.DataLink;
import xyz.ldqc.buka.data.repository.core.engine.structure.support.SkipListDataLink;
import xyz.ldqc.buka.data.repository.exception.BadBucketException;
import xyz.ldqc.buka.util.IoUtil;
import xyz.ldqc.buka.util.XorUtil;
import xyz.ldqc.tightcall.buffer.SimpleByteData;
import xyz.ldqc.tightcall.util.StringUtil;

/**
 * @author Fetters
 */
public class BadBucket extends AbstractBucket {

  private static final String BUKA_MAGIC_NUMBER = "BK_dm";

  private final List<JSONObject> dataMapping;

  public BadBucket(String name) {
    this.fieldMap = new HashMap<>();
    if (StringUtil.isBlank(name)) {
      throw new BadBucketException("name can not be null");
    }
    this.name = name;
    this.dataMapping = new ArrayList<>();
  }


  public void put(String json) {
    JSONObject jsonObj = toJson(json);
    doPut(jsonObj);
    jsonObj.clear();
  }

  public  void put(JSONObject json){
    doPut(json);
  }

  public void doPut(JSONObject json){
    Set<Entry<String, Object>> entrySet = json.entrySet();
    if (entrySet.isEmpty()) {
      return;
    }
    this.dataMapping.add(json);
    int index = this.dataMapping.lastIndexOf(json);
    handlerEntrySet(entrySet, index);
  }

  private void handlerEntrySet(Set<Entry<String, Object>> entrySet, int id) {
    entrySet.forEach(e -> handlerEachEntry(e, id));
  }

  private void handlerEachEntry(Entry<String, Object> entry, int id) {
    DataLink<?> fieldDataLink = getFieldDataLink(entry);
    Class<?> dataType = fieldDataLink.getDataType();
    Object value = entry.getValue();
    if (!dataType.isAssignableFrom(value.getClass())) {
      throw new ClassCastException("Error type");
    }
    long sectionId = fieldDataLink.addDataSection(id, value);
    entry.setValue(sectionId);
  }

  private DataLink<?> getFieldDataLink(Entry<String, Object> entry) {
    String key = entry.getKey();
    Object value = entry.getValue();
    DataLink<?> dataLink = this.fieldMap.get(key);
    if (dataLink == null) {
      dataLink = new SkipListDataLink<>(value.getClass());
    }
    if (!dataLink.getDataType().isAssignableFrom(value.getClass())) {
      throw new ClassCastException("Class type not match");
    }
    this.fieldMap.put(key, dataLink);
    return dataLink;
  }

  private JSONObject toJson(String json) {
    try {
      return JSONObject.parse(json);
    } catch (Exception e) {
      throw new JSONException("Illegal JSON");
    }
  }

  @Override
  public boolean storage(String path, String pass) {
    File pathFile = new File(path);
    if (!pathFile.exists()) {
      throw new InvalidPathException("Illegal path", path + "is not exists");
    }
    if (!pathFile.isDirectory()) {
      throw new InvalidPathException("Illegal path", path + "is not path");
    }
    byte[] pk = StringUtil.isBlank(pass) ? null : pass.getBytes(StandardCharsets.UTF_8);
    byte[] dataMappingBytes = getDataMappingBytes(pk);
    doStorageDataMapping(pathFile.getAbsolutePath(), dataMappingBytes);
    storageFieldMap(pathFile.getAbsolutePath(), pk);
    return true;
  }

  @Override
  public List<String> find(Sieve sieve) {
    Map<String, Entry<String, Conditional>> conditionalMap = sieve.getConditionalMap();
    Set<String> conditionalField = conditionalMap.keySet();
    List<Long> dataIdList = getDataIdList(conditionalField);
    return doFind(dataIdList, sieve);
  }

  private List<Long> getDataIdList(Set<String> conditionalField) {
    Set<Long> idSet = null;
    for (String f : conditionalField) {
      if (!fieldMap.containsKey(f)) {
        throw new BadBucketException("Error sieve");
      }
      DataLink<?> dataLink = fieldMap.get(f);
      List<? extends Entry<?, List<Long>>> sectionList = dataLink.sectionList();
      Set<Long> ns = new HashSet<>();
      sectionList.forEach(s -> ns.addAll(s.getValue()));
      if (idSet == null) {
        idSet = ns;
      } else {
        idSet.retainAll(ns);
      }
    }
    assert idSet != null;
    return new ArrayList<>(idSet);
  }

  private List<String> doFind(List<Long> il, Sieve sieve) {
    List<String> rl = new LinkedList<>();
    il.forEach(i -> {
      JSONObject j = dataMapping.get(Math.toIntExact(i));
      Set<Entry<String, Entry<String, Conditional>>> entrySet = sieve.entrySet();
      JSONObject nj = new JSONObject();
      if (toTargetJson(j, nj, entrySet)){
        rl.add(nj.toString());
      }
    });
    return rl;
  }

  private boolean toTargetJson(JSONObject oj, JSONObject nj,
      Set<Entry<String, Entry<String, Conditional>>> entrySet){
    for (Entry<String, Entry<String, Conditional>> entry : entrySet) {
      String key = entry.getKey();
      Long id = ((Long) oj.get(key));
      Object data = fieldMap.get(key).getSectionById(id);
      Entry<String, Conditional> conditionalEntry = entry.getValue();
      Conditional conditional = conditionalEntry.getValue();
      if (conditional.judge(data)){
        nj.put(conditionalEntry.getKey(), data);
      }else {
        return false;
      }
    }
    return true;
  }

  private byte[] getDataMappingBytes(byte[] pk) {
    if (dataMapping.isEmpty()) {
      return new byte[]{};
    }
    StringBuilder sb = new StringBuilder(BUKA_MAGIC_NUMBER);
    dataMapping.forEach(j -> {
      j.forEach((k, v) -> sb.append(k).append(':').append(v).append(','));
      sb.setCharAt(sb.length() - 1, '\n');
    });
    byte[] bs = sb.toString().getBytes(StandardCharsets.UTF_8);
    bs = XorUtil.encrypt(bs, pk);
    return bs;
  }

  private void doStorageDataMapping(String basePath, byte[] data) {
    String fileName = basePath + '/' + this.getName()+ "_dm" + ".buk";
    if (!IoUtil.fileWriteBytes(fileName, data, true)) {
      throw new BadBucketException("Storage data mapping fail");
    }
  }

  private void storageFieldMap(String basePath, byte[] pk){
    List<byte[]> fieldMapBytes = getFieldMapBytes(pk);
    int i = 0;
    for (byte[] b : fieldMapBytes) {
      String fileName = basePath + '/' + this.getName() + "_fm_" + i++ + ".buk";
      if (!IoUtil.fileWriteBytes(fileName, b, true)){
        throw new BadBucketException("Storage field map fail");
      }
    }

  }

  private List<byte[]> getFieldMapBytes(byte[] pk){
    List<byte[]> r = new LinkedList<>();
    Set<Entry<String, DataLink<?>>> entrySet = this.fieldMap.entrySet();
    entrySet.forEach( e -> {
      SimpleByteData byteData = new SimpleByteData(1024, Integer.MAX_VALUE);
      byteData.writeBytes(e.getKey().getBytes(StandardCharsets.UTF_8));
      byteData.writeBytes("bk@".getBytes(StandardCharsets.UTF_8));
      DataLink<?> dl = e.getValue();
      byte[] linkBytes = dl.toLinkBytes();
      byteData.writeBytes(linkBytes);
      byte[] bytes = byteData.readBytes();
      if (pk != null && pk.length > 0){
        bytes = XorUtil.encrypt(bytes, pk);
      }
      r.add(bytes);
    });
    return r;
  }
}

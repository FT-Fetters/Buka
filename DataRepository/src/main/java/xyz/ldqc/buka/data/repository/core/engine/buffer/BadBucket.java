package xyz.ldqc.buka.data.repository.core.engine.buffer;

import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import xyz.ldqc.buka.data.repository.core.engine.structure.DataLink;
import xyz.ldqc.buka.data.repository.core.engine.structure.support.SkipListDataLink;
import xyz.ldqc.buka.data.repository.exception.BadBucketException;
import xyz.ldqc.buka.util.IoUtil;
import xyz.ldqc.buka.util.XorUtil;
import xyz.ldqc.tightcall.util.StringUtil;

/**
 * @author Fetters
 */
public class BadBucket extends AbstractBucket {

  private static final String BUKA_MAGIC_NUMBER = "BK_dm";

  private final List<JSONObject> dataMapping;

  public BadBucket(String name) {
    this.fieldMap = new HashMap<>();
    if (StringUtil.isBlank(name)){
      throw new BadBucketException("name can not be null");
    }
    this.name = name;
    this.dataMapping = new ArrayList<>();
  }


  public void put(String json) {
    JSONObject jsonObj = toJson(json);
    Set<Entry<String, Object>> entrySet = jsonObj.entrySet();
    if (entrySet.isEmpty()) {
      return;
    }
    this.dataMapping.add(jsonObj);
    int index = this.dataMapping.lastIndexOf(jsonObj);
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
    byte[] pk = pass.getBytes(StandardCharsets.UTF_8);
    byte[] dataMappingBytes = getDataMappingBytes(pk);
    doStorageDataMapping(pathFile.getAbsolutePath(), dataMappingBytes);
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

  private void doStorageDataMapping(String basePath, byte[] data){
    String fileName = basePath + '/' +this.getName() + ".buk";
    if (!IoUtil.fileWriteBytes(fileName, data)) {
      throw new BadBucketException("Storage data mapping fail");
    }
  }
}

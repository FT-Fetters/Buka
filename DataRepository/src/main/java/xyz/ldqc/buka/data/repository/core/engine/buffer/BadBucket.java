package xyz.ldqc.buka.data.repository.core.engine.buffer;

import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import xyz.ldqc.buka.data.repository.core.engine.structure.DataLink;
import xyz.ldqc.buka.data.repository.core.engine.structure.support.SkipListDataLink;

/**
 * @author Fetters
 */
public class BadBucket extends AbstractBucket {

  private final List<JSONObject> dataMapping;

  public BadBucket(String name) {
    this.fieldMap = new HashMap<>();
    this.name = name;
    this.dataMapping = new ArrayList<>();
  }


  public void put(String json){
    JSONObject jsonObj = toJson(json);
    Set<Entry<String, Object>> entrySet = jsonObj.entrySet();
    if (entrySet.isEmpty()){
      return;
    }
    this.dataMapping.add(jsonObj);
    int index = this.dataMapping.lastIndexOf(jsonObj);
    handlerEntrySet(entrySet, index);
  }

  private void handlerEntrySet(Set<Entry<String, Object>> entrySet, int id){
    entrySet.forEach(e -> handlerEachEntry(e, id));
  }

  private void handlerEachEntry(Entry<String, Object> entry, int id){
    DataLink<?> fieldDataLink = getFieldDataLink(entry);
    Class<?> dataType = fieldDataLink.getDataType();
    Object value = entry.getValue();
    if (!dataType.isAssignableFrom(value.getClass())) {
      throw new ClassCastException("Error type");
    }
    fieldDataLink.addDataSection(id,value);
  }

  private DataLink<?> getFieldDataLink(Entry<String, Object> entry){
    String key = entry.getKey();
    Object value = entry.getValue();
    DataLink<?> dataLink = this.fieldMap.get(key);
    if (dataLink == null){
      dataLink = new SkipListDataLink<>(value.getClass());
    }
    if (!dataLink.getDataType().isAssignableFrom(value.getClass())) {
      throw new ClassCastException("Class type not match");
    }
    this.fieldMap.put(key, dataLink);
    return dataLink;
  }

  private JSONObject toJson(String json){
    try {
      return JSONObject.parse(json);
    }catch (Exception e){
      throw new JSONException("Illegal JSON");
    }
  }

}

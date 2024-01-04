package xyz.ldqc.buka.data.repository.core.engine.structure.support;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import xyz.ldqc.buka.data.repository.core.engine.structure.DataLink;

/**
 * @author Fetters
 */
public class SkipListDataLink<T> implements DataLink<T> {

  private final ConcurrentSkipListMap<DataId, ConcurrentSkipListMap<Long, T>> dataMap;

  private final ConcurrentHashMap<String, Long> idMap;

  private final AtomicLong increaseId;

  public SkipListDataLink() {
    this.dataMap = new ConcurrentSkipListMap<>();
    this.idMap = new ConcurrentHashMap<>();
    this.increaseId = new AtomicLong(0);
  }

  @Override
  public void addDataSection(long dataSourceId, String sectionName ,T section) {
    long id = idMap.containsKey(sectionName) ? idMap.get(sectionName) : increaseId.getAndIncrement();
    DataId dataId = new DataId(id, sectionName);
    ConcurrentSkipListMap<Long, T> sectionList = this.dataMap.computeIfAbsent(dataId,
        tDataId -> new ConcurrentSkipListMap<>());
    idMap.put(sectionName, id);
    sectionList.put(dataSourceId, section);
  }

  @Override
  public List<T> getSectionData(String sectionName){
    Long id = idMap.get(sectionName);
    if (id == null){
      return Collections.emptyList();
    }
    DataId dataId = new DataId(id, sectionName);
    ConcurrentSkipListMap<Long, T> sectionMap = this.dataMap.get(dataId);
    Collection<T> values = sectionMap.values();
    return values.stream().distinct().collect(Collectors.toList());
  }


  private static class DataId implements Comparable<DataId> {

    private final long id;

    private final String sectionName;


    public DataId(long id, String sectionName) {
      this.id = id;
      this.sectionName = sectionName;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      }
      if (this == obj) {
        return true;
      }
      if (obj instanceof DataId) {
        DataId dataId = (DataId) obj;
        if (dataId.getId() != this.getId()) {
          return false;
        }
        return dataId.getSectionName().equals(this.sectionName);
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (this.getId() == -1){
        return getSectionName().hashCode();
      }
      return super.hashCode();
    }

    public long getId() {
      return id;
    }

    public String getSectionName() {
      return sectionName;
    }


    @Override
    public int compareTo(DataId o) {
      long l = this.getId() - o.getId();
      if (l == 0){
        return 0;
      }
      return l > 0 ? 1 : -1;
    }
  }
}

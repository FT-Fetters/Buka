package xyz.ldqc.buka.data.repository.core.engine.structure.support;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import xyz.ldqc.buka.data.repository.core.engine.structure.DataLink;
import xyz.ldqc.buka.data.repository.core.engine.structure.DataTypeEnum;

/**
 * @author Fetters
 */
public class SkipListDataLink<T> implements DataLink<T> {

  private final ConcurrentSkipListMap<DataId, ConcurrentSkipListMap<Long, T>> dataMap;

  private final ConcurrentHashMap<T, Long> idMap;

  private final AtomicLong increaseId;

  private Class<T> type;

  public SkipListDataLink(Class<T> type) {
    this.dataMap = new ConcurrentSkipListMap<>();
    this.idMap = new ConcurrentHashMap<>();
    this.increaseId = new AtomicLong(0);
    this.type = type;
  }

  @SuppressWarnings("unchecked")
  private void handlerDataType(DataTypeEnum dataType) {
    if (dataType.equals(DataTypeEnum.STRING)) {
      this.type = (Class<T>) String.class;
    } else if (dataType.equals(DataTypeEnum.NUMBER)) {
      this.type = (Class<T>) Long.class;
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public long addDataSection(long dataSourceId, Object section) {
    if (type.isAssignableFrom(section.getClass())) {
      T t = (T) section;
      return doAddDataSection(dataSourceId, t);
    }else {
      throw new ClassCastException("Error type class");
    }
  }

  public long doAddDataSection(long dataSourceId, T section) {
    long id = idMap.containsKey(section) ? idMap.get(section) : increaseId.getAndIncrement();
    DataId dataId = new DataId(id, section);
    ConcurrentSkipListMap<Long, T> sectionList = this.dataMap.computeIfAbsent(dataId,
        tDataId -> new ConcurrentSkipListMap<>());
    idMap.put(section, id);
    sectionList.put(dataSourceId, section);
    return id;
  }

  @Override
  public List<T> getSectionData(T section) {
    Long id = idMap.get(section);
    if (id == null) {
      return Collections.emptyList();
    }
    DataId dataId = new DataId(id, section);
    ConcurrentSkipListMap<Long, T> sectionMap = this.dataMap.get(dataId);
    Collection<T> values = sectionMap.values();
    return values.stream().distinct().collect(Collectors.toList());
  }

  @Override
  public Class<?> getDataType() {
    return this.type;
  }


  private class DataId implements Comparable<DataId> {

    private final long id;

    private final T section;


    public DataId(long id, T section) {
      this.id = id;
      this.section = section;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
      if (obj == null || obj.getClass() != this.getClass()) {
        return false;
      }
      if (this == obj) {
        return true;
      }
      DataId.class.isAssignableFrom(obj.getClass());
      DataId dataId = (DataId) obj;
      if (dataId.getId() != this.getId()) {
        return false;
      }
      return dataId.getSection().equals(this.section);
    }

    @Override
    public int hashCode() {
      if (this.getId() == -1) {
        return getSection().hashCode();
      }
      return super.hashCode();
    }

    public long getId() {
      return id;
    }

    public T getSection() {
      return section;
    }


    @Override
    public int compareTo(DataId o) {
      long l = this.getId() - o.getId();
      if (l == 0) {
        return 0;
      }
      return l > 0 ? 1 : -1;
    }
  }
}

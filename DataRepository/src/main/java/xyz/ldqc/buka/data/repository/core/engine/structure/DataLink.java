package xyz.ldqc.buka.data.repository.core.engine.structure;

import java.util.List;
import java.util.Map.Entry;

/**
 * @author Fetters
 */
public interface DataLink<T> {

  /**
   * 添加数据段
   * @param dataSourceId 源数据所在id
   * @param sectionName 数据段名
   * @param section 数据段内容
   */
  long addDataSection(long dataSourceId ,Object section);

  /**
   * 获取数据段
   * @param section 数据段名
   * @return 去重后的数据列表
   */
  List<Long> getSectionDataSourceIdList(T section);

  /**
   * 获取数据的类型
   * @return Class
   */
  Class<?> getDataType();

  /**
   * 获取所有数据
   * @return list
   */
  List<Entry<T, List<Long>>> sectionList();

  /**
   * 通过id获取数据
   * @param id id
   * @return T
   */
  T getSectionById(long id);

}

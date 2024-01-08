package xyz.ldqc.buka.data.repository.core.engine.structure;

import java.util.List;

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
  void addDataSection(long dataSourceId ,Object section);

  /**
   * 获取数据段
   * @param sectionName 数据段名
   * @return 去重后的数据列表
   */
  List<T> getSectionData(T section);

  /**
   * 获取数据的类型
   * @return Class
   */
  Class<?> getDataType();

}

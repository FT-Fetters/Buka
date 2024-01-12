package xyz.ldqc.buka.data.repository.core.engine.structure;

import java.util.List;

/**
 * @author Fetters
 * 数据索引
 */
public interface DataIndex<T> {

  /**
   * 相等匹配
   * @param obj 匹配对象
   * @return 符合条件的列表
   */
  List<T> eq(Object obj);

  /**
   * 不相等匹配
   * @param obj 匹配对象
   * @return 符合条件的列表
   */
  List<T> ne(Object obj);

  /**
   * 大于等于匹配
   * @param obj 匹配对象
   * @return 符合条件的列表
   */
  List<T> ge(Object obj);

  /**
   * 小于等于匹配
   * @param obj 匹配对象
   * @return 符合条件的列表
   */
  List<T> le(Object obj);


  /**
   * 大于匹配
   * @param obj 匹配对象
   * @return 符合条件的列表
   */
  List<T> gt(Object obj);


  /**
   * 小于匹配
   * @param obj 匹配对象
   * @return 符合条件的列表
   */
  List<T> lt(Object obj);
}

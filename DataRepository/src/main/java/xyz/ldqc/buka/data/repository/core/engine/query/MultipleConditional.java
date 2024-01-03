package xyz.ldqc.buka.data.repository.core.engine.query;

import java.util.List;

/**
 * @author Fetters
 */
public interface MultipleConditional<T> extends Conditional<T>{

  /**
   * 获取条件列表
   * @return list
   */
  List<Conditional<T>> getConditionals();

  /**
   * 添加条件
   * @param conditional 条件
   */
  void addConditional(Conditional<T> conditional, BoolTypeEnum type);

}

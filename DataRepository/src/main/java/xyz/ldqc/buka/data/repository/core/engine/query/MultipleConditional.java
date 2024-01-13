package xyz.ldqc.buka.data.repository.core.engine.query;

import java.util.List;

/**
 * @author Fetters
 */
public interface MultipleConditional extends Conditional {

  /**
   * 获取条件列表
   * @return list
   */
  List<Conditional> getConditionals();

  /**
   * 添加条件
   * @param conditional 条件
   */
  void addConditional(Conditional conditional, BoolTypeEnum type);

}

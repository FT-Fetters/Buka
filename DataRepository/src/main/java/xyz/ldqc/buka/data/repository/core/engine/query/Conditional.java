package xyz.ldqc.buka.data.repository.core.engine.query;

/**
 * @author Fetters
 */
public interface Conditional {

  /**
   * 判断
   * @param obj 与条件判断的对象
   * @return bool
   */
  boolean judge(Object obj);

}

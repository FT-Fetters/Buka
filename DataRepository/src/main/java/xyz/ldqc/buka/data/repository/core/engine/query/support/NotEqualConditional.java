package xyz.ldqc.buka.data.repository.core.engine.query.support;

import xyz.ldqc.buka.data.repository.core.engine.query.Conditional;

/**
 * @author Fetters
 */
public class NotEqualConditional<T> implements Conditional<T> {

  private final T val;

  public NotEqualConditional(T val){
    this.val = val;
  }

  @Override
  public boolean judge(T obj) {
    return !obj.equals(val);
  }
}

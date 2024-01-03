package xyz.ldqc.buka.data.repository.core.engine.query.support;

import xyz.ldqc.buka.data.repository.core.engine.query.Conditional;

/**
 * @author Fetters
 */
public class EqualConditional<T> implements Conditional<T> {

  private final T val;

  public EqualConditional(T val){
    this.val = val;
  }


  @Override
  public boolean judge(T obj) {
    return this.val.equals(obj);
  }
}

package xyz.ldqc.buka.data.repository.core.engine.query.support;

import xyz.ldqc.buka.data.repository.core.engine.query.Conditional;

/**
 * @author Fetters
 */
public class GreaterThanConditional<T> implements Conditional<T> {

  private final Comparable<T> val;

  public GreaterThanConditional(Comparable<T> val){
    this.val = val;
  }

  @Override
  public boolean judge(T obj) {
    int i = val.compareTo(obj);
    return i > 0;
  }
}

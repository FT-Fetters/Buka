package xyz.ldqc.buka.data.repository.core.engine.query.support;

import xyz.ldqc.buka.data.repository.core.engine.query.Conditional;
import xyz.ldqc.buka.data.repository.exception.ConditionalException;

/**
 * @author Fetters
 */
public class GreaterEqualThanConditional<T> implements Conditional<T> {

  private final Comparable<T> val;

  public GreaterEqualThanConditional(Comparable<T> val){
    if (val == null){
      throw new ConditionalException("val must not be null");
    }
    this.val = val;
  }

  @Override
  public boolean judge(T obj) {
    int i = val.compareTo(obj);
    return i >= 0;
  }
}

package xyz.ldqc.buka.data.repository.core.engine.query.support;

import xyz.ldqc.buka.data.repository.core.engine.query.Conditional;

/**
 * @author Fetters
 */
public class TextMatchConditional<T> implements Conditional<T> {

  private final T val;


  public TextMatchConditional(T val){
    this.val = val;
  }

  @Override
  public boolean judge(T obj) {
    return obj.toString().contains(val.toString());
  }



}

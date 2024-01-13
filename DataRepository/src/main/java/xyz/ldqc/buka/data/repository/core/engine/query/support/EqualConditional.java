package xyz.ldqc.buka.data.repository.core.engine.query.support;

import xyz.ldqc.buka.data.repository.core.engine.query.Conditional;

/**
 * @author Fetters
 */
public class EqualConditional implements Conditional {

  private final Object val;

  public EqualConditional(Object val){
    this.val = val;
  }


  @Override
  public boolean judge(Object obj) {
    return this.val.equals(obj);
  }
}

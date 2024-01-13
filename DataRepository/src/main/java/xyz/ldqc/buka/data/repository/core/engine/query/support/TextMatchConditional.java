package xyz.ldqc.buka.data.repository.core.engine.query.support;

import xyz.ldqc.buka.data.repository.core.engine.query.Conditional;

/**
 * @author Fetters
 */
public class TextMatchConditional implements Conditional {

  private final Object val;


  public TextMatchConditional(Object val){
    this.val = val;
  }

  @Override
  public boolean judge(Object obj) {
    return obj.toString().contains(val.toString());
  }



}

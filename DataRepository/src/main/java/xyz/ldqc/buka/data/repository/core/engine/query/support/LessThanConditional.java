package xyz.ldqc.buka.data.repository.core.engine.query.support;


/**
 * @author Fetters
 */
public class LessThanConditional extends BaseComparableConditional {

  public LessThanConditional(Object val){
    initDataType(val);
  }

  @Override
  public boolean judge(Object obj) {
    return compare(obj) < 0;
  }
}

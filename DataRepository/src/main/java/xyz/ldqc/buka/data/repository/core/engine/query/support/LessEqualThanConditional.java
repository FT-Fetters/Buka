package xyz.ldqc.buka.data.repository.core.engine.query.support;

/**
 * @author Fetters
 */
public class LessEqualThanConditional extends BaseComparableConditional {

  public LessEqualThanConditional(Object val) {
    initDataType(val);
  }

  @Override
  public boolean judge(Object obj) {
    return compare(obj) <= 0;
  }
}

package xyz.ldqc.buka.data.repository.core.engine.query.support;

import xyz.ldqc.buka.data.repository.exception.ConditionalException;

/**
 * @author Fetters
 */
public class GreaterEqualThanConditional extends BaseComparableConditional {

  public GreaterEqualThanConditional(Object val) {
    if (val == null) {
      throw new ConditionalException("Value must not be null");
    }
    initDataType(val);
  }

  @Override
  public boolean judge(Object obj) {
    return compare(obj) >= 0;
  }
}

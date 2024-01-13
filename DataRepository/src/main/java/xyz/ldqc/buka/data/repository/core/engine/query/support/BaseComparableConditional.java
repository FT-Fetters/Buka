package xyz.ldqc.buka.data.repository.core.engine.query.support;

import xyz.ldqc.buka.data.repository.core.engine.query.Conditional;
import xyz.ldqc.buka.data.repository.core.engine.structure.DataTypeEnum;

/**
 * @author Fetters
 */
public abstract class BaseComparableConditional implements Conditional {

  private DataTypeEnum dataType;

  private Object val;

  /**
   * 获取数据类型
   *
   * @param val val
   */
  protected void initDataType(Object val) {
    this.val = val;
    if (Number.class.isAssignableFrom(val.getClass())) {
      this.dataType = DataTypeEnum.NUMBER;
      return;
    }
    if (val instanceof CharSequence) {
      this.dataType = DataTypeEnum.STRING;
      return;
    }
    if (Boolean.class.isAssignableFrom(val.getClass()) || boolean.class.isAssignableFrom(
        val.getClass())) {
      this.dataType = DataTypeEnum.BOOLEAN;
      return;
    }
    this.dataType = DataTypeEnum.OTHER;
  }

  protected DataTypeEnum getDataType() {
    return dataType;
  }

  protected Object getVal() {
    return val;
  }

  protected int compare(Object obj) {
    if (dataType.equals(DataTypeEnum.STRING)) {
      return stringCompare(obj);
    }
    if (dataType.equals(DataTypeEnum.NUMBER)){
      return numberCompare(obj);
    }
    if (dataType.equals(DataTypeEnum.BOOLEAN)){
      return booleanCompare(obj);
    }
    return otherCompare(obj);
  }

  private int stringCompare(Object obj) {
    CharSequence charSequence = (CharSequence) obj;
    return obj.toString().compareTo(String.valueOf(charSequence));
  }

  private int numberCompare(Object obj){
    Number number = (Number) obj;
    return number.intValue() - ((Number) this.val).intValue() ;
  }

  private int booleanCompare(Object obj){
    Boolean bol = (Boolean) obj;
    Boolean vb = (Boolean) val;
    return vb.compareTo(bol);
  }

  private int otherCompare(Object obj){
    int vh = val.hashCode();
    int th = obj.hashCode();
    return th - vh;
  }

}

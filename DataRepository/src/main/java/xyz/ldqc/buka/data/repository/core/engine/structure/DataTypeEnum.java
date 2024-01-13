package xyz.ldqc.buka.data.repository.core.engine.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fetters
 */

public enum DataTypeEnum {

  /**
   * 字符串
   */
  STRING(String.class, CharSequence.class, char.class, Character.class),
  /**
   * 数字
   */
  NUMBER(int.class, long.class, Integer.class, Long.class),
  /**
   * 日期
   */
  DATE(String.class),
  /**
   * 布尔
   */
  BOOLEAN(boolean.class, Boolean.class),
  /**
   * 其它
   */
  OTHER();

  private final List<Class<?>> targetClass;

  DataTypeEnum(Class<?>... clas){
    this.targetClass = new ArrayList<>(List.of(clas));
  }

  public boolean judge(Class<?> clazz){
    return targetClass.contains(clazz);
  }

  @Override
  public String toString() {
    return this.name() + targetClass.toString();
  }
}

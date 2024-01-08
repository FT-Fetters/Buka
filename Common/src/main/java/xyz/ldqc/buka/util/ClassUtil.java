package xyz.ldqc.buka.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Fetters
 */
public class ClassUtil {

  private ClassUtil(){}

  public static boolean genericClassEqual(Object o1, Object o2){
    return genericClassEqual(o1, o2.getClass());
  }

  public static boolean genericClassEqual(Object o, Class<?> clazz){
    Type generic = o.getClass().getGenericSuperclass();
    Type tarClass = clazz.getGenericSuperclass();
    if (generic.equals(tarClass)){
      return true;
    }
    // 判断泛型类型是否相同
    if (generic instanceof ParameterizedType && tarClass instanceof ParameterizedType) {
      Type[] typeArgs1 = ((ParameterizedType) generic).getActualTypeArguments();
      Type[] typeArgs2 = ((ParameterizedType) tarClass).getActualTypeArguments();

      if (typeArgs1.length > 0 && typeArgs2.length > 0) {
        Type typeArg1 = typeArgs1[0];
        Type typeArg2 = typeArgs2[0];
        return typeArg1.equals(typeArg2);
      }
    }
    return false;
  }



}

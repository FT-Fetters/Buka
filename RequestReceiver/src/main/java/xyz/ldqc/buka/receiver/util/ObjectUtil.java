package xyz.ldqc.buka.receiver.util;

import com.alibaba.fastjson2.JSONObject;

/**
 * @author Fetters
 */
public class ObjectUtil {

  private ObjectUtil(){
    throw new UnsupportedOperationException();
  }

  public static  <T> T json2Obj(String json, Class<T> target){
    return JSONObject.parseObject(json, target);
  }

}

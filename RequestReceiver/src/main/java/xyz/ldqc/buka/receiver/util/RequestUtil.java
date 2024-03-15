package xyz.ldqc.buka.receiver.util;

import xyz.ldqc.tightcall.protocol.http.HttpNioRequest;

/**
 * @author Fetters
 */
public class RequestUtil {

  private RequestUtil()
  {
    throw new UnsupportedOperationException();
  }

  public static  <T> T body2Obj(HttpNioRequest request, Class<T> clazz){
    String body = request.getBody();
    return ObjectUtil.json2Obj(body, clazz);
  }

}

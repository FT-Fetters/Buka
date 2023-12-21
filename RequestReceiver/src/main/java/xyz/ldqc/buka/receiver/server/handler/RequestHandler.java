package xyz.ldqc.buka.receiver.server.handler;

import xyz.ldqc.tightcall.protocol.http.HttpNioRequest;
import xyz.ldqc.tightcall.protocol.http.HttpNioResponse;

/**
 * @author Fetters
 */
public interface RequestHandler {

  /**
   * 处理http请求
   * @param request 请求体
   * @return 响应体
   */
  HttpNioResponse doHandler(HttpNioRequest request);

}

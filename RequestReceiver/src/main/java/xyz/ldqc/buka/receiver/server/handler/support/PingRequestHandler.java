package xyz.ldqc.buka.receiver.server.handler.support;

import xyz.ldqc.buka.data.repository.core.action.ActionResult;
import xyz.ldqc.buka.receiver.server.handler.RequestHandler;
import xyz.ldqc.buka.receiver.server.handler.annotation.RequestHandlerClass;
import xyz.ldqc.buka.receiver.server.response.Response;
import xyz.ldqc.tightcall.protocol.http.HttpNioRequest;
import xyz.ldqc.tightcall.protocol.http.HttpNioResponse;

/**
 * 响应测试请求处理器
 * @author Fetters
 */
@RequestHandlerClass(path = "/ping", method = "GET")
public class PingRequestHandler implements RequestHandler {

    @Override
    public HttpNioResponse doHandler(HttpNioRequest request) {
        return Response.okJson(new ActionResult("ok"));
    }
}

package xyz.ldqc.buka.receiver.server.chain;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.nio.channels.Channel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import xyz.ldqc.buka.receiver.server.handler.RequestHandler;
import xyz.ldqc.buka.receiver.server.handler.annotation.RequestHandlerClass;
import xyz.ldqc.buka.receiver.server.response.Response;
import xyz.ldqc.tightcall.protocol.http.HttpNioRequest;
import xyz.ldqc.tightcall.protocol.http.HttpNioResponse;
import xyz.ldqc.tightcall.util.PackageUtil;

/**
 * @author Fetters
 */
public class FilterRequestChain extends AbstractTransitiveInBoundChain {

  private final static String HANDLER_SUPPORT_PACKAGE = "xyz.ldqc.buka.receiver.server.handler.support";

  private final Map<String, RequestHandler> handlerMap;

  public FilterRequestChain() {
    handlerMap = new HashMap<>();
    List<Class<?>> classes;
    try {
      classes = PackageUtil.getPackageClasses(HANDLER_SUPPORT_PACKAGE);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    if (classes == null) {
      return;
    }
    for (Class<?> clazz : classes) {
      loadHandler(clazz);
    }
  }

  private void loadHandler(Class<?> clazz) {
    if (!RequestHandler.class.isAssignableFrom(clazz)) {
      return;
    }
    RequestHandlerClass handlerAnnotation = clazz.getAnnotation(RequestHandlerClass.class);
    if (handlerAnnotation == null) {
      return;
    }
    String path = handlerAnnotation.path();
    try {
      RequestHandler requestHandler = (RequestHandler) clazz.getConstructor().newInstance();
      handlerMap.put(path, requestHandler);
    } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
             IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void doHandler(Channel channel, Object o) {
    if (!(o instanceof HttpNioRequest)) {
      next(channel, Response.error("unknown server error"));
      return;
    }
    HttpNioRequest request = (HttpNioRequest) o;
    URI uri = request.getUri();
    RequestHandler handler = handlerMap.get(uri.getPath());
    if (handler == null) {
      next(channel, Response.notFound());
      return;
    }
    RequestHandlerClass annotation = handler.getClass().getAnnotation(RequestHandlerClass.class);
    String targetMethod = annotation.method();
    String method = request.getMethod();
    if (!method.equals(targetMethod)) {
      next(channel, Response.methodNotAllowed(
          targetMethod + " method is required, but " + method + " method is sent"));
      return;
    }
    HttpNioResponse response = handler.doHandler(request);
    next(channel, response);
  }
}

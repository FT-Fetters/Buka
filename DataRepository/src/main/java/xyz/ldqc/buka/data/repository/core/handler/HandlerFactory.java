package xyz.ldqc.buka.data.repository.core.handler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import xyz.ldqc.buka.data.repository.core.action.Action;
import xyz.ldqc.buka.data.repository.core.action.ActionResult;
import xyz.ldqc.buka.data.repository.exception.HandlerFactoryException;
import xyz.ldqc.tightcall.util.PackageUtil;

/**
 * @author Fetters
 */
public class HandlerFactory {

  private static final String HANDLER_PACKAGE = "xyz.ldqc.buka.data.repository.core.handler.support";

  private final Map<Class<?>, ActionHandler<?>> handlerMap;

  private HandlerFactory(){
    List<Class<?>> classes;
    try {
      classes = PackageUtil.getPackageClasses(HANDLER_PACKAGE);
      handlerMap = new HashMap<>();
      for (Class<?> clazz : classes) {
        if (!Arrays.asList(clazz.getInterfaces()).contains(ActionHandler.class)) {
          continue;
        }
        ActionHandler<?> handler = (ActionHandler<?>) clazz.getConstructor().newInstance();
        Class<?> actionClass = handler.getActionClass();
        handlerMap.put(actionClass, handler);
      }
    } catch (IOException | InvocationTargetException | InstantiationException |
             IllegalAccessException | NoSuchMethodException e) {
      throw new HandlerFactoryException("factory build fail: " + e.getMessage());
    }

  }

  public static HandlerFactory getInstance(){
    return new HandlerFactory();
  }

  @SuppressWarnings("unchecked")
  public ActionResult handler(Action action){
    Class<? extends Action> actionClass = action.getClass();
    ActionHandler<Action> handler = (ActionHandler<Action>) handlerMap.get(actionClass);
    return handler.handler(action);
  }

}

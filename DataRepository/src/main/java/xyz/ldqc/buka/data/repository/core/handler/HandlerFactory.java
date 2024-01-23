package xyz.ldqc.buka.data.repository.core.handler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import xyz.ldqc.buka.data.repository.core.action.Action;
import xyz.ldqc.buka.data.repository.core.action.ActionResult;
import xyz.ldqc.buka.data.repository.core.aware.DataBufferPoolAware;
import xyz.ldqc.buka.data.repository.core.engine.RepositoryEngine;
import xyz.ldqc.buka.data.repository.core.engine.buffer.DataBufferPool;
import xyz.ldqc.buka.data.repository.exception.ActionHandlerNotFoundException;
import xyz.ldqc.buka.data.repository.exception.HandlerFactoryException;
import xyz.ldqc.tightcall.util.PackageUtil;

/**
 * @author Fetters
 */
public class HandlerFactory {

  private static final String HANDLER_PACKAGE = "xyz.ldqc.buka.data.repository.core.handler.support";

  private final Map<Class<?>, ActionHandler<?>> handlerMap;

  private final RepositoryEngine engine;


  private HandlerFactory(RepositoryEngine engine){
    this.engine = engine;
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
        setHandlerAware(handler);
        handlerMap.put(actionClass, handler);
      }
    } catch (IOException | InvocationTargetException | InstantiationException |
             IllegalAccessException | NoSuchMethodException e) {
      throw new HandlerFactoryException("factory build fail: " + e.getMessage());
    }

  }

  private void setHandlerAware(Object handler){
    if (handler instanceof DataBufferPoolAware){
      setRepositoryBufferAware(((DataBufferPoolAware) handler));
    }
  }

  private void setRepositoryBufferAware(DataBufferPoolAware aware){
    if (engine == null){
      throw new HandlerFactoryException("Engine is null");
    }
    DataBufferPool dataBufferPool = engine.getDataBufferPool();
    if (dataBufferPool == null){
      throw new HandlerFactoryException("Data buffer pool is null");
    }
    aware.setDataBufferPool(dataBufferPool);
  }

  public static HandlerFactory getInstance(RepositoryEngine engine){
    return new HandlerFactory(engine);
  }

  @SuppressWarnings("unchecked")
  public ActionResult handler(Action action){
    Class<? extends Action> actionClass = action.getClass();
    ActionHandler<Action> handler = (ActionHandler<Action>) handlerMap.get(actionClass);
    if (handler == null){
      throw new ActionHandlerNotFoundException("Action handler not found: " + action.getClass().getName());
    }
    return handler.handler(action);
  }

}

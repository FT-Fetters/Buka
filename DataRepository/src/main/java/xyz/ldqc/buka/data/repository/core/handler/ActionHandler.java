package xyz.ldqc.buka.data.repository.core.handler;

import xyz.ldqc.buka.data.repository.core.action.Action;
import xyz.ldqc.buka.data.repository.core.action.ActionResult;

/**
 * @author Fetters
 */
public interface ActionHandler<T extends Action> {

  /**
   * 获取Handler对应处理的Action
   * @return class
   */
  Class<T> getActionClass();

  /**
   * 处理action
   * @param action action
   * @return ActionResult
   */
  ActionResult handler(T action);


}

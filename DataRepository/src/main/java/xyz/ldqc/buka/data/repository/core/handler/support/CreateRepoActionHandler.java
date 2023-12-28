package xyz.ldqc.buka.data.repository.core.handler.support;

import xyz.ldqc.buka.data.repository.core.action.Action;
import xyz.ldqc.buka.data.repository.core.action.ActionResult;
import xyz.ldqc.buka.data.repository.core.action.support.CreateRepoAction;
import xyz.ldqc.buka.data.repository.core.handler.ActionHandler;

/**
 * @author Fetters
 */
public class CreateRepoActionHandler implements ActionHandler<CreateRepoAction> {

  @Override
  public Class<CreateRepoAction> getActionClass() {
    return CreateRepoAction.class;
  }

  @Override
  public ActionResult handler(CreateRepoAction action) {

    return null;
  }
}

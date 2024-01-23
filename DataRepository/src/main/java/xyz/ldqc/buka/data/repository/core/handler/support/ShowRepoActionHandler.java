package xyz.ldqc.buka.data.repository.core.handler.support;

import java.util.List;
import xyz.ldqc.buka.data.repository.core.action.ActionResult;
import xyz.ldqc.buka.data.repository.core.action.support.ShowRepositoryAction;
import xyz.ldqc.buka.data.repository.core.aware.DataBufferPoolAware;
import xyz.ldqc.buka.data.repository.core.engine.buffer.DataBufferPool;
import xyz.ldqc.buka.data.repository.core.handler.ActionHandler;

/**
 * @author Fetters
 */
public class ShowRepoActionHandler implements ActionHandler<ShowRepositoryAction>,
    DataBufferPoolAware {

  private DataBufferPool dataBufferPool;

  @Override
  public void setDataBufferPool(DataBufferPool dataBufferPool) {
    this.dataBufferPool = dataBufferPool;
  }

  @Override
  public Class<ShowRepositoryAction> getActionClass() {
    return ShowRepositoryAction.class;
  }

  @Override
  public ActionResult handler(ShowRepositoryAction action) {
    List<String> allRepositoryName = dataBufferPool.getAllRepositoryName();
    return new ActionResult("ok", allRepositoryName);
  }
}

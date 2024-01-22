package xyz.ldqc.buka.data.repository.core.handler.support;

import xyz.ldqc.buka.data.repository.core.action.ActionResult;
import xyz.ldqc.buka.data.repository.core.action.support.DeleteRepoAction;
import xyz.ldqc.buka.data.repository.core.aware.DataBufferPoolAware;
import xyz.ldqc.buka.data.repository.core.engine.buffer.DataBufferPool;
import xyz.ldqc.buka.data.repository.core.handler.ActionHandler;

/**
 * @author Fetters
 */
public class DeleteRepoActionHandler implements ActionHandler<DeleteRepoAction>,
    DataBufferPoolAware {

  private DataBufferPool dataBufferPool;

  @Override
  public Class<DeleteRepoAction> getActionClass() {
    return DeleteRepoAction.class;
  }

  @Override
  public ActionResult handler(DeleteRepoAction action) {
    String repoName = action.getRepoName();
    dataBufferPool.deleteRepository(repoName);
    return new ActionResult("Deleted");
  }

  @Override
  public void setDataBufferPool(DataBufferPool dataBufferPool) {
    this.dataBufferPool = dataBufferPool;
  }
}

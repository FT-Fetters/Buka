package xyz.ldqc.buka.data.repository.core.handler.support;

import xyz.ldqc.buka.data.repository.core.action.ActionResult;
import xyz.ldqc.buka.data.repository.core.action.support.CreateRepoAction;
import xyz.ldqc.buka.data.repository.core.aware.DataBufferPoolAware;
import xyz.ldqc.buka.data.repository.core.engine.buffer.DataBufferPool;
import xyz.ldqc.buka.data.repository.core.engine.buffer.RepositoryBuffer;
import xyz.ldqc.buka.data.repository.core.handler.ActionHandler;

/**
 * @author Fetters
 */
public class CreateRepoActionHandler implements ActionHandler<CreateRepoAction>,
    DataBufferPoolAware {

  private DataBufferPool dataBufferPool;

  @Override
  public Class<CreateRepoAction> getActionClass() {
    return CreateRepoAction.class;
  }

  @Override
  public ActionResult handler(CreateRepoAction action) {
    String repoName = action.getRepoName();
    RepositoryBuffer repository = dataBufferPool.createRepository(repoName);
    if (repository == null) {
      return new ActionResult("Create fail");
    } else {
      return new ActionResult("Created");
    }
  }

  @Override
  public void setDataBufferPool(DataBufferPool dataBufferPool) {
    this.dataBufferPool = dataBufferPool;
  }
}

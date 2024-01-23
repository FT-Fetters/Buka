package xyz.ldqc.buka.data.repository.core.handler.support;

import xyz.ldqc.buka.data.repository.core.action.ActionResult;
import xyz.ldqc.buka.data.repository.core.action.support.DeleteBucketAction;
import xyz.ldqc.buka.data.repository.core.aware.DataBufferPoolAware;
import xyz.ldqc.buka.data.repository.core.engine.buffer.DataBufferPool;
import xyz.ldqc.buka.data.repository.core.engine.buffer.RepositoryBuffer;
import xyz.ldqc.buka.data.repository.core.handler.ActionHandler;
import xyz.ldqc.buka.data.repository.exception.RepositoryNotFoundException;

/**
 * @author Fetters
 */
public class DeleteBucketActionHandler implements ActionHandler<DeleteBucketAction>,
    DataBufferPoolAware {

  private DataBufferPool dataBufferPool;

  @Override
  public Class<DeleteBucketAction> getActionClass() {
    return DeleteBucketAction.class;
  }

  @Override
  public ActionResult handler(DeleteBucketAction action) {
    String name = action.getName();
    String repo = action.getRepo();
    RepositoryBuffer repositoryBuffer = dataBufferPool.loadRepository(repo);
    if (repositoryBuffer == null){
      throw new RepositoryNotFoundException("Unknown repository");
    }
    if (!repositoryBuffer.hasBucket(name)) {
      return new ActionResult("bucket not exist");
    }
    boolean deleted = repositoryBuffer.deleteBucket(name);
    if (deleted){
      return new ActionResult("Deleted");
    }else {
      return new ActionResult("Delete fail");
    }
  }

  @Override
  public void setDataBufferPool(DataBufferPool dataBufferPool) {
    this.dataBufferPool = dataBufferPool;
  }
}

package xyz.ldqc.buka.data.repository.core.handler.support;

import xyz.ldqc.buka.data.repository.core.action.ActionResult;
import xyz.ldqc.buka.data.repository.core.action.support.CreateBucketAction;
import xyz.ldqc.buka.data.repository.core.aware.DataBufferPoolAware;
import xyz.ldqc.buka.data.repository.core.engine.buffer.BadBucket;
import xyz.ldqc.buka.data.repository.core.engine.buffer.DataBufferPool;
import xyz.ldqc.buka.data.repository.core.engine.buffer.RepositoryBuffer;
import xyz.ldqc.buka.data.repository.core.handler.ActionHandler;
import xyz.ldqc.buka.data.repository.exception.BadBucketException;
import xyz.ldqc.buka.data.repository.exception.RepositoryNotFoundException;

/**
 * @author Fetters
 */
public class CreateBucketActionHandler implements ActionHandler<CreateBucketAction>,
    DataBufferPoolAware {

  private DataBufferPool dataBufferPool;

  @Override
  public void setDataBufferPool(DataBufferPool dataBufferPool) {
    this.dataBufferPool = dataBufferPool;
  }

  @Override
  public Class<CreateBucketAction> getActionClass() {
    return CreateBucketAction.class;
  }

  @Override
  public ActionResult handler(CreateBucketAction action) {
    String repo = action.getRepo();
    String name = action.getName();
    RepositoryBuffer repositoryBuffer = dataBufferPool.loadRepository(repo);
    if (repositoryBuffer == null) {
      throw new RepositoryNotFoundException("Unknown repository");
    }
    BadBucket bucket = repositoryBuffer.createBucket(name);
    if (bucket == null){
      throw new BadBucketException("Create fail");
    }
    return new ActionResult("Created");
  }
}

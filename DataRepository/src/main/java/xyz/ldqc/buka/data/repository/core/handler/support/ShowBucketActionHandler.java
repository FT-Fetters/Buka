package xyz.ldqc.buka.data.repository.core.handler.support;

import java.util.List;
import xyz.ldqc.buka.data.repository.core.action.ActionResult;
import xyz.ldqc.buka.data.repository.core.action.support.ShowBucketAction;
import xyz.ldqc.buka.data.repository.core.aware.DataBufferPoolAware;
import xyz.ldqc.buka.data.repository.core.engine.buffer.DataBufferPool;
import xyz.ldqc.buka.data.repository.core.engine.buffer.RepositoryBuffer;
import xyz.ldqc.buka.data.repository.core.handler.ActionHandler;

/**
 * @author Fetters
 */
public class ShowBucketActionHandler implements ActionHandler<ShowBucketAction>,
    DataBufferPoolAware {

  private DataBufferPool dataBufferPool;


  @Override
  public void setDataBufferPool(DataBufferPool dataBufferPool) {
    this.dataBufferPool = dataBufferPool;
  }

  @Override
  public Class<ShowBucketAction> getActionClass() {
    return ShowBucketAction.class;
  }

  @Override
  public ActionResult handler(ShowBucketAction action) {
    String repoName = action.getRepoName();
    RepositoryBuffer repositoryBuffer = dataBufferPool.loadRepository(repoName);
    if (repositoryBuffer == null){
      return new ActionResult("unknown");
    }
    List<String> allBucketNames = repositoryBuffer.getAllBucketNames();
    return new ActionResult("ok", allBucketNames);
  }
}

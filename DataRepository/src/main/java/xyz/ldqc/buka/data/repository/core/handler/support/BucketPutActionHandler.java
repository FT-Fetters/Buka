package xyz.ldqc.buka.data.repository.core.handler.support;

import com.alibaba.fastjson2.JSONObject;
import java.util.List;
import xyz.ldqc.buka.data.repository.core.action.ActionResult;
import xyz.ldqc.buka.data.repository.core.action.support.BucketPutAction;
import xyz.ldqc.buka.data.repository.core.aware.DataBufferPoolAware;
import xyz.ldqc.buka.data.repository.core.engine.buffer.BadBucket;
import xyz.ldqc.buka.data.repository.core.engine.buffer.DataBufferPool;
import xyz.ldqc.buka.data.repository.core.engine.buffer.RepositoryBuffer;
import xyz.ldqc.buka.data.repository.core.handler.ActionHandler;

/**
 * @author Fetters
 */
public class BucketPutActionHandler implements ActionHandler<BucketPutAction>, DataBufferPoolAware {

  private DataBufferPool dataBufferPool;

  @Override
  public Class<BucketPutAction> getActionClass() {
    return BucketPutAction.class;
  }

  @Override
  public ActionResult handler(BucketPutAction action) {
    String repo = action.getRepo();
    String bucket = action.getBucket();
    List<JSONObject> data = action.getData();
    RepositoryBuffer repositoryBuffer = dataBufferPool.loadRepository(repo);
    if (repositoryBuffer == null){
      return new ActionResult("unknown repository");
    }
    BadBucket badBucket = (BadBucket) repositoryBuffer.loadBucket(bucket);
    if (badBucket == null){
      return new ActionResult("unknown bucket");
    }
    data.forEach(badBucket::put);
    badBucket.storage(repositoryBuffer.getRepoPath(), null);
    return new ActionResult("ok");
  }

  @Override
  public void setDataBufferPool(DataBufferPool dataBufferPool) {
    this.dataBufferPool = dataBufferPool;
  }
}

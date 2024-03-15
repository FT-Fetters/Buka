package xyz.ldqc.buka.data.repository.core.handler.support;

import java.util.List;
import xyz.ldqc.buka.data.repository.core.action.ActionResult;
import xyz.ldqc.buka.data.repository.core.action.support.QueryBucketAction;
import xyz.ldqc.buka.data.repository.core.aware.DataBufferPoolAware;
import xyz.ldqc.buka.data.repository.core.engine.buffer.AbstractBucket;
import xyz.ldqc.buka.data.repository.core.engine.buffer.Box;
import xyz.ldqc.buka.data.repository.core.engine.buffer.BoxFactory;
import xyz.ldqc.buka.data.repository.core.engine.buffer.DataBufferPool;
import xyz.ldqc.buka.data.repository.core.engine.buffer.RepositoryBuffer;
import xyz.ldqc.buka.data.repository.core.engine.query.Sieve;
import xyz.ldqc.buka.data.repository.core.handler.ActionHandler;
import xyz.ldqc.tightcall.util.StringUtil;

/**
 * @author Fetters
 */
public class QueryBucketActionHandler implements ActionHandler<QueryBucketAction>,
    DataBufferPoolAware {

    private DataBufferPool dataBufferPool;

    @Override
    public Class<QueryBucketAction> getActionClass() {
        return QueryBucketAction.class;
    }

    @Override
    public ActionResult handler(QueryBucketAction action) {
        Sieve sieve = action.getSieve();
        String repoName = action.getRepo();
        String bucketName = action.getBucket();
        String boxName = action.getBox();

        RepositoryBuffer repositoryBuffer = dataBufferPool.loadRepository(repoName);
        if (repositoryBuffer == null) {
            return new ActionResult("fail", "repository not found");
        }

        AbstractBucket abstractBucket = repositoryBuffer.loadBucket(bucketName);
        if (abstractBucket == null) {
            return new ActionResult("fail", "bucket not found");
        }

        List<String> findRes = abstractBucket.find(sieve);

        if (StringUtil.isNotBlank(boxName)) {
            Box box = BoxFactory.getBox(boxName);
            if (box != null) {
                box.putAll(findRes, true);
            }
        }

        return new ActionResult("ok", findRes);
    }

    @Override
    public void setDataBufferPool(DataBufferPool dataBufferPool) {
        this.dataBufferPool = dataBufferPool;
    }
}

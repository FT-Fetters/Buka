package xyz.ldqc.buka.data.repository.core.action.support;

import xyz.ldqc.buka.data.repository.core.action.Action;
import xyz.ldqc.buka.data.repository.core.engine.query.Sieve;

/**
 * 查询Bucket Action
 * @author Fetters
 */
public class QueryBucketAction implements Action {

    /**
     * 筛选
     */
    private final Sieve sieve;

    /**
     * 仓库名
     */
    private final String repo;

    /**
     * 桶名
     */
    private final String bucket;

    /**
     * 箱子名
     */
    private final String box;


    public QueryBucketAction(Sieve sieve, String repo, String bucket, String box) {
        this.sieve = sieve;
        this.repo = repo;
        this.bucket = bucket;
        this.box = box;
    }

    public Sieve getSieve() {
        return sieve;
    }

    public String getRepo() {
        return repo;
    }

    public String getBucket() {
        return bucket;
    }

    public String getBox() {
        return box;
    }
}

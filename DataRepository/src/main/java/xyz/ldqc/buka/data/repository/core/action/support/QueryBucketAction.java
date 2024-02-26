package xyz.ldqc.buka.data.repository.core.action.support;

import xyz.ldqc.buka.data.repository.core.action.Action;
import xyz.ldqc.buka.data.repository.core.engine.query.Sieve;

/**
 * @author Fetters
 */
public class QueryBucketAction implements Action {

    private final Sieve sieve;

    private final String repo;

    private final String bucket;


    public QueryBucketAction(Sieve sieve, String repo, String bucket) {
        this.sieve = sieve;
        this.repo = repo;
        this.bucket = bucket;
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
}

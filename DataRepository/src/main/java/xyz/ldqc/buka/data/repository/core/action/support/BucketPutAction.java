package xyz.ldqc.buka.data.repository.core.action.support;

import com.alibaba.fastjson2.JSONObject;
import java.util.List;
import xyz.ldqc.buka.data.repository.core.action.Action;

/**
 * Bucket放入数据Action
 * @author Fetters
 */
public class BucketPutAction implements Action {

  private final String repo;

  private final String bucket;

  private final List<JSONObject> data;

  public BucketPutAction(String repo, String bucket, List<JSONObject> data) {
    this.repo = repo;
    this.bucket = bucket;
    this.data = data;
  }

  public String getRepo() {
    return repo;
  }

  public String getBucket() {
    return bucket;
  }

  public List<JSONObject> getData() {
    return data;
  }
}

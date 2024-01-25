package xyz.ldqc.buka.receiver.entity;

import com.alibaba.fastjson2.JSONObject;
import java.util.List;

/**
 * @author Fetters
 */
public class BucketPutEntity {

  private String repo;

  private String bucket;

  private List<JSONObject> data;


  public BucketPutEntity(String repo, String bucket, List<JSONObject> data) {
    this.repo = repo;
    this.bucket = bucket;
    this.data = data;
  }


  public String getRepo() {
    return repo;
  }

  public void setRepo(String repo) {
    this.repo = repo;
  }

  public String getBucket() {
    return bucket;
  }

  public void setBucket(String bucket) {
    this.bucket = bucket;
  }

  public List<JSONObject> getData() {
    return data;
  }

  public void setData(List<JSONObject> data) {
    this.data = data;
  }
}

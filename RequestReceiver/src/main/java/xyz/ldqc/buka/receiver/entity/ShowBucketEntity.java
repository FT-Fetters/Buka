package xyz.ldqc.buka.receiver.entity;

/**
 * @author Fetters
 */
public class ShowBucketEntity {

  private String repo;

  public ShowBucketEntity(String repo) {
    this.repo = repo;
  }

  public String getRepo() {
    return repo;
  }

  public void setRepo(String repo) {
    this.repo = repo;
  }
}

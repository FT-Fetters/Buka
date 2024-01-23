package xyz.ldqc.buka.receiver.entity;

/**
 * @author Fetters
 */
public class CreateBucketEntity {

  private String repo;

  private String name;

  public CreateBucketEntity(String repo, String name) {
    this.repo = repo;
    this.name = name;
  }

  public CreateBucketEntity() {
  }

  public String getRepo() {
    return repo;
  }

  public void setRepo(String repo) {
    this.repo = repo;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}

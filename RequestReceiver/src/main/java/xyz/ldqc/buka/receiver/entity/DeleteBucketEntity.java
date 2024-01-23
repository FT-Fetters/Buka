package xyz.ldqc.buka.receiver.entity;

/**
 * @author Fetters
 */
public class DeleteBucketEntity {


  private String repo;

  private String name;

  public DeleteBucketEntity(String repo, String name) {
    this.repo = repo;
    this.name = name;
  }

  public DeleteBucketEntity() {
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

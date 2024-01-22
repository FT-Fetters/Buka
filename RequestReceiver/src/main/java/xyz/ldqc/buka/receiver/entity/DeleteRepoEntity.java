package xyz.ldqc.buka.receiver.entity;

/**
 * @author Fetters
 */
public class DeleteRepoEntity {

  public DeleteRepoEntity(String name) {
    this.name = name;
  }

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}

package xyz.ldqc.buka.data.repository.core.action.support;

import xyz.ldqc.buka.data.repository.core.action.Action;

/**
 * @author Fetters
 */
public class DeleteBucketAction implements Action {

  private final String repo;

  private final String name;

  public DeleteBucketAction(String repo, String name) {
    this.repo = repo;
    this.name = name;
  }

  public String getRepo() {
    return repo;
  }

  public String getName() {
    return name;
  }
}

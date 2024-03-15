package xyz.ldqc.buka.data.repository.core.action.support;

import xyz.ldqc.buka.data.repository.core.action.Action;

/**
 * 删除Bucket Action
 * @author Fetters
 */
public class DeleteBucketAction implements Action {

  /**
   * 仓库名
   */
  private final String repo;

  /**
   * Bucket名
   */
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

package xyz.ldqc.buka.data.repository.core.action.support;

import xyz.ldqc.buka.data.repository.core.action.Action;

/**
 * 获取所有桶名
 * @author Fetters
 */
public class ShowBucketAction implements Action {

  private final String repoName;

  public ShowBucketAction(String repoName) {
    this.repoName = repoName;
  }

  public String getRepoName() {
    return repoName;
  }
}

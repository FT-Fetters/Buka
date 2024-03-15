package xyz.ldqc.buka.data.repository.core.action.support;

import xyz.ldqc.buka.data.repository.core.action.Action;

/**
 * 创建仓库Action
 * @author Fetters
 */
public class CreateRepoAction implements Action {

  /**
   * 仓库名
   */
  private final String repoName;

  public CreateRepoAction(String repoName){
    this.repoName = repoName;
  }

  public String getRepoName() {
    return repoName;
  }
}

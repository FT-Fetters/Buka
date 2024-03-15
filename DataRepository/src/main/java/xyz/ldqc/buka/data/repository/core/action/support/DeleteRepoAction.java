package xyz.ldqc.buka.data.repository.core.action.support;

import xyz.ldqc.buka.data.repository.core.action.Action;

/**
 * 删除仓库Action
 * @author Fetters
 */
public class DeleteRepoAction implements Action {

  /**
   * 仓库名
   */
  private final String repoName;

  public DeleteRepoAction(String repoName){
    this.repoName = repoName;
  }

  public String getRepoName() {
    return repoName;
  }

}

package xyz.ldqc.buka.data.repository.core.action.support;

import xyz.ldqc.buka.data.repository.core.action.Action;

/**
 * @author Fetters
 */
public class DeleteRepoAction implements Action {

  private final String repoName;

  public DeleteRepoAction(String repoName){
    this.repoName = repoName;
  }

  public String getRepoName() {
    return repoName;
  }

}

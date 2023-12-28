package xyz.ldqc.buka.data.repository;

import xyz.ldqc.buka.data.repository.config.DataRepositoryConfig;
import xyz.ldqc.buka.data.repository.core.RepositoryContext;
import xyz.ldqc.buka.data.repository.core.action.Action;
import xyz.ldqc.buka.data.repository.core.action.ActionResult;

/**
 * @author Fetters
 */
public class DataRepositoryApplication {

  private final RepositoryContext repositoryContext;

  private DataRepositoryApplication(RepositoryContext repositoryContext){
    this.repositoryContext = repositoryContext;
  }

  public static DataRepositoryApplication run(DataRepositoryConfig config){
    RepositoryContext repositoryContext = new RepositoryContext(config);
    return new DataRepositoryApplication(repositoryContext);
  }

  public ActionResult execute(String script){
    System.out.println(script);
    return new ActionResult();
  }

  public ActionResult execute(Action action){
    return repositoryContext.getHandlerFactory().handler(action);
  }
}

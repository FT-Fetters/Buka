package xyz.ldqc.buka.data.repository;

import xyz.ldqc.buka.data.repository.config.DataRepositoryConfig;

/**
 * @author Fetters
 */
public class DataRepositoryApplication {

  private DataRepositoryApplication(){}

  public static DataRepositoryApplication run(DataRepositoryConfig config){
    return new DataRepositoryApplication();
  }

  public String execute(String script){
    System.out.println(script);
    return "yes";
  }
}

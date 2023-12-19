package xyz.ldqc.buka;

import xyz.ldqc.buka.boot.ApplicationContext;

/**
 * @author Fetters
 */
public class BukaBootstrap {

  private final ApplicationContext context;

  /**
   * 私有化构造函数
   */
  private BukaBootstrap(ApplicationContext context){
    this.context = context;
  }

  public static BukaBootstrap run(){
    ApplicationContext context = new ApplicationContext();
    context.loadContext();
    return new BukaBootstrap(context);
  }

}

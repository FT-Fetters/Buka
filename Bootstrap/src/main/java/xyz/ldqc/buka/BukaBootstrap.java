package xyz.ldqc.buka;

import xyz.ldqc.buka.boot.ApplicationContext;

/**
 * @author Fetters
 */
public class BukaBootstrap {

  /**
   * 私有化构造函数
   */
  private BukaBootstrap(){
  }

  public static BukaApplication run(){
    ApplicationContext context = new ApplicationContext();
    BukaApplication bukaApplication = new BukaApplication(context);
    context.loadContext();
    return bukaApplication;
  }

}

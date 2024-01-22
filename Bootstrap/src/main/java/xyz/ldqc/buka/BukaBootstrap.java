package xyz.ldqc.buka;

import xyz.ldqc.buka.boot.BootApplicationContext;

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
    BootApplicationContext context = new BootApplicationContext();
    BukaApplication bukaApplication = new BukaApplication(context);
    context.loadContext();
    return bukaApplication;
  }

}

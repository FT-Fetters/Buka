package xyz.ldqc.buka.receiver.server;

import xyz.ldqc.buka.data.repository.DataRepositoryApplication;
import xyz.ldqc.buka.receiver.server.chain.AuthVerifyChain;
import xyz.ldqc.buka.receiver.server.chain.FilterRequestChain;
import xyz.ldqc.buka.receiver.server.chain.HandleRequestChain;
import xyz.ldqc.buka.receiver.server.chain.HttpExceptionCatchChain;
import xyz.ldqc.tightcall.chain.ChainGroup;
import xyz.ldqc.tightcall.chain.support.DefaultChannelChainGroup;
import xyz.ldqc.tightcall.server.HttpServerApplication;
import xyz.ldqc.tightcall.server.exec.support.NioServerExec;

/**
 * @author Fetters
 */
public class ReceiveServerApplication {

  private final HttpServerApplication httpServerApplication;


  private ReceiveServerApplication(HttpServerApplication httpServerApplication) {
    this.httpServerApplication = httpServerApplication;
  }

  public static ReceiveServerApplication boot(int port,
      DataRepositoryApplication dataRepositoryApplication, String authKey) {
    int availableProcessors = Runtime.getRuntime().availableProcessors();
    HttpServerApplication httpServer = HttpServerApplication.builder()
        .bind(port)
        .chain(buildChainGroup(dataRepositoryApplication, authKey))
        .execNum(availableProcessors * 2)
        .executor(NioServerExec.class)
        .boot();
    return new ReceiveServerApplication(httpServer);
  }

  private static ChainGroup buildChainGroup(DataRepositoryApplication dataRepositoryApplication, String authKey) {
    ChainGroup chainGroup = new DefaultChannelChainGroup();
    chainGroup.addLast(new HttpExceptionCatchChain(chainGroup));
    chainGroup.addLast(new FilterRequestChain(chainGroup));
    chainGroup.addLast(new AuthVerifyChain(chainGroup, authKey));
    chainGroup.addLast(new HandleRequestChain(dataRepositoryApplication));
    return chainGroup;
  }

}

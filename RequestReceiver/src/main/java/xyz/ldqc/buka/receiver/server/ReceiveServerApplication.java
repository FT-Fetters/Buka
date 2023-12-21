package xyz.ldqc.buka.receiver.server;

import xyz.ldqc.buka.receiver.server.chain.FilterRequestChain;
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

  public static ReceiveServerApplication boot(int port) {
    int availableProcessors = Runtime.getRuntime().availableProcessors();
    HttpServerApplication httpServer = HttpServerApplication.builder()
        .bind(port)
        .chain(buildChainGroup())
        .execNum(availableProcessors * 2)
        .executor(NioServerExec.class)
        .boot();
    return new ReceiveServerApplication(httpServer);
  }

  private static ChainGroup buildChainGroup(){
    ChainGroup chainGroup = new DefaultChannelChainGroup();
    chainGroup.addLast(new HttpExceptionCatchChain(chainGroup));
    chainGroup.addLast(new FilterRequestChain());
    return chainGroup;
  }

}

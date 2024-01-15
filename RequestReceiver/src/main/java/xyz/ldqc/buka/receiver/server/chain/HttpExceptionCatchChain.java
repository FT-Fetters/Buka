package xyz.ldqc.buka.receiver.server.chain;

import java.nio.channels.Channel;
import java.nio.channels.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.ldqc.buka.receiver.server.response.Response;
import xyz.ldqc.tightcall.chain.ChainGroup;
import xyz.ldqc.tightcall.chain.ChannelChainGroup;
import xyz.ldqc.tightcall.protocol.http.HttpNioResponse;

/**
 * @author Fetters
 */
public class HttpExceptionCatchChain extends AbstractTransitiveInBoundChain {

  private static final Logger log = LoggerFactory.getLogger(HttpExceptionCatchChain.class);

  private final ChainGroup chainGroup;

  public HttpExceptionCatchChain(ChainGroup chainGroup){
    this.chainGroup = chainGroup;
  }

  @Override
  public void doHandler(Channel channel, Object o) {
    try {
      next(channel, o);
    }catch (Exception e){
      log.error("Http chain exception: {}",e.getMessage(), e);
      if (!(chainGroup instanceof ChannelChainGroup)){
        return;
      }
      ChannelChainGroup channelChainGroup = (ChannelChainGroup) chainGroup;
      HttpNioResponse response = Response.error(e.getMessage());
      channelChainGroup.doOutBoundChain((SocketChannel) channel, response);
    }
  }
}

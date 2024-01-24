package xyz.ldqc.buka.receiver.server.chain;

import java.nio.channels.Channel;
import java.nio.channels.SocketChannel;
import xyz.ldqc.buka.receiver.server.response.Response;
import xyz.ldqc.tightcall.chain.ChainGroup;
import xyz.ldqc.tightcall.chain.ChannelChainGroup;
import xyz.ldqc.tightcall.protocol.http.HttpNioRequest;
import xyz.ldqc.tightcall.protocol.http.HttpNioResponse;

/**
 * @author Fetters
 */
public class FilterRequestChain extends AbstractTransitiveInBoundChain {

  private final ChainGroup chainGroup;

  public FilterRequestChain(ChainGroup chainGroup){
    this.chainGroup = chainGroup;
  }
  @Override
  public void doHandler(Channel channel, Object o) {
    if (!(o instanceof HttpNioRequest)) {
      ChannelChainGroup channelChainGroup = (ChannelChainGroup) chainGroup;
      HttpNioResponse response = Response.error("unknown server error");
      channelChainGroup.doOutBoundChain((SocketChannel) channel, response);
    }else {
      next(channel, o);
    }
  }
}

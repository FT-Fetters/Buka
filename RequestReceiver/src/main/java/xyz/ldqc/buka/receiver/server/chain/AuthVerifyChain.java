package xyz.ldqc.buka.receiver.server.chain;

import java.nio.channels.Channel;
import java.nio.channels.SocketChannel;
import java.util.Map;
import xyz.ldqc.buka.receiver.server.response.Response;
import xyz.ldqc.tightcall.chain.ChainGroup;
import xyz.ldqc.tightcall.chain.ChannelChainGroup;
import xyz.ldqc.tightcall.protocol.http.HttpNioRequest;
import xyz.ldqc.tightcall.protocol.http.HttpNioResponse;
import xyz.ldqc.tightcall.util.StringUtil;

/**
 * @author Fetters
 */
public class AuthVerifyChain extends AbstractTransitiveInBoundChain {

  private final String authKey;

  private final ChannelChainGroup chainGroup;

  public AuthVerifyChain(ChainGroup chainGroup, String authKey) {
    this.chainGroup = (ChannelChainGroup) chainGroup;
    this.authKey = authKey;
  }

  @Override
  public void doHandler(Channel channel, Object o) {
    HttpNioRequest request = (HttpNioRequest) o;
    Map<String, String> param = request.getParam();
    String key = param.get("auth");
    if (StringUtil.isBlank(key) || !key.equals(authKey)) {
      HttpNioResponse response = Response.error("Unauthorized");
      chainGroup.doOutBoundChain((SocketChannel) channel, response);
      return;
    }
    next(channel, request);
  }
}

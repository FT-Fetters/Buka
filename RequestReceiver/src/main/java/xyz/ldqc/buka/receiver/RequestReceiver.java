package xyz.ldqc.buka.receiver;

import xyz.ldqc.buka.receiver.server.ReceiveServerApplication;

/**
 * @author Fetters
 */
public class RequestReceiver {

  private ReceiveServerApplication serverApplication;

  private RequestReceiver(){

  }

  public static RequestReceiver create(int port){
    ReceiveServerApplication receiveServerApplication = ReceiveServerApplication.boot(port);
    RequestReceiver requestReceiver = new RequestReceiver();
    requestReceiver.serverApplication = receiveServerApplication;
    return requestReceiver;
  }



}

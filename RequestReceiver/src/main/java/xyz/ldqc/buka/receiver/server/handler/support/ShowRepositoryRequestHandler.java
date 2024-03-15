package xyz.ldqc.buka.receiver.server.handler.support;

import xyz.ldqc.buka.data.repository.DataRepositoryApplication;
import xyz.ldqc.buka.data.repository.core.action.ActionResult;
import xyz.ldqc.buka.data.repository.core.action.support.ShowRepositoryAction;
import xyz.ldqc.buka.receiver.server.handler.DataRepositoryRequestHandler;
import xyz.ldqc.buka.receiver.server.handler.annotation.RequestHandlerClass;
import xyz.ldqc.buka.receiver.server.response.Response;
import xyz.ldqc.tightcall.protocol.http.HttpNioRequest;
import xyz.ldqc.tightcall.protocol.http.HttpNioResponse;

/**
 * 获取仓库列表请求处理器
 * @author Fetters
 */
@RequestHandlerClass(path = "/show/repo", method = "GET")
public class ShowRepositoryRequestHandler implements DataRepositoryRequestHandler {

  private DataRepositoryApplication dataRepositoryApplication;

  @Override
  public HttpNioResponse doHandler(HttpNioRequest request) {
    ActionResult result = dataRepositoryApplication.execute(new ShowRepositoryAction());
    return Response.okJson(result);
  }

  @Override
  public void setDataRepositoryApplication(DataRepositoryApplication dataRepositoryApplication) {
    this.dataRepositoryApplication = dataRepositoryApplication;
  }
}

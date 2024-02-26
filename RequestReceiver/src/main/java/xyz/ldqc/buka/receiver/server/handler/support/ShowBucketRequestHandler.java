package xyz.ldqc.buka.receiver.server.handler.support;

import xyz.ldqc.buka.data.repository.DataRepositoryApplication;
import xyz.ldqc.buka.data.repository.core.action.ActionResult;
import xyz.ldqc.buka.data.repository.core.action.support.ShowBucketAction;
import xyz.ldqc.buka.receiver.entity.ShowBucketEntity;
import xyz.ldqc.buka.receiver.server.handler.DataRepositoryRequestHandler;
import xyz.ldqc.buka.receiver.server.handler.annotation.RequestHandlerClass;
import xyz.ldqc.buka.receiver.server.response.Response;
import xyz.ldqc.buka.receiver.util.RequestUtil;
import xyz.ldqc.tightcall.protocol.http.HttpNioRequest;
import xyz.ldqc.tightcall.protocol.http.HttpNioResponse;
import xyz.ldqc.tightcall.util.StringUtil;

/**
 * @author Fetters
 */
@RequestHandlerClass(path = "/show/bucket", method = "POST")
public class ShowBucketRequestHandler implements DataRepositoryRequestHandler {

  private DataRepositoryApplication dataRepositoryApplication;

  @Override
  public void setDataRepositoryApplication(DataRepositoryApplication dataRepositoryApplication) {
    this.dataRepositoryApplication = dataRepositoryApplication;
  }

  @Override
  public HttpNioResponse doHandler(HttpNioRequest request) {
    ShowBucketEntity showBucketEntity = RequestUtil.body2Obj(request, ShowBucketEntity.class);
    String repo = showBucketEntity.getRepo();
    if (StringUtil.isBlank(repo)){
      return Response.okJson("Unknown repository");
    }
    ActionResult result = dataRepositoryApplication.execute(new ShowBucketAction(repo));
    return Response.okJson(result);
  }
}

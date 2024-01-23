package xyz.ldqc.buka.receiver.server.handler.support;

import xyz.ldqc.buka.data.repository.DataRepositoryApplication;
import xyz.ldqc.buka.data.repository.core.action.ActionResult;
import xyz.ldqc.buka.data.repository.core.action.support.DeleteBucketAction;
import xyz.ldqc.buka.receiver.entity.DeleteBucketEntity;
import xyz.ldqc.buka.receiver.entity.WebResponseEntity;
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
@RequestHandlerClass(path = "/del/bucket", method = "POST")
public class DeleteBucketRequestHandler implements DataRepositoryRequestHandler {

  private DataRepositoryApplication dataRepositoryApplication;

  @Override
  public void setDataRepositoryApplication(DataRepositoryApplication dataRepositoryApplication) {
    this.dataRepositoryApplication = dataRepositoryApplication;
  }

  @Override
  public HttpNioResponse doHandler(HttpNioRequest request) {
    DeleteBucketEntity deleteBucketEntity = RequestUtil.body2Obj(request, DeleteBucketEntity.class);
    String repo = deleteBucketEntity.getRepo();
    String name = deleteBucketEntity.getName();
    if (StringUtil.isAnyBlank(repo, name)) {
      return Response.okJson(WebResponseEntity.fail("repo and bucket name can not be null"));
    }
    DeleteBucketAction deleteBucketAction = new DeleteBucketAction(repo, name);
    ActionResult result = dataRepositoryApplication.execute(deleteBucketAction);
    return Response.okJson(result);
  }
}

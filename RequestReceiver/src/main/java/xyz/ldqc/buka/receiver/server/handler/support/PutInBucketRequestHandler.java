package xyz.ldqc.buka.receiver.server.handler.support;

import xyz.ldqc.buka.data.repository.DataRepositoryApplication;
import xyz.ldqc.buka.data.repository.core.action.ActionResult;
import xyz.ldqc.buka.data.repository.core.action.support.BucketPutAction;
import xyz.ldqc.buka.receiver.entity.BucketPutEntity;
import xyz.ldqc.buka.receiver.server.handler.DataRepositoryRequestHandler;
import xyz.ldqc.buka.receiver.server.handler.annotation.RequestHandlerClass;
import xyz.ldqc.buka.receiver.server.response.Response;
import xyz.ldqc.buka.receiver.util.RequestUtil;
import xyz.ldqc.tightcall.protocol.http.HttpNioRequest;
import xyz.ldqc.tightcall.protocol.http.HttpNioResponse;

/**
 * @author Fetters
 */
@RequestHandlerClass(path = "/bucket/put", method = "POST")
public class PutInBucketRequestHandler implements DataRepositoryRequestHandler {

  private DataRepositoryApplication dataRepositoryApplication;

  @Override
  public void setDataRepositoryApplication(DataRepositoryApplication dataRepositoryApplication) {
    this.dataRepositoryApplication = dataRepositoryApplication;
  }

  @Override
  public HttpNioResponse doHandler(HttpNioRequest request) {
    BucketPutEntity bucketPutEntity = RequestUtil.body2Obj(request, BucketPutEntity.class);
    BucketPutAction action = new BucketPutAction(bucketPutEntity.getRepo(),
        bucketPutEntity.getBucket(), bucketPutEntity.getData());
    ActionResult result = dataRepositoryApplication.execute(action);
    return Response.okJson(result);
  }
}

package xyz.ldqc.buka.receiver.server.handler.support;

import xyz.ldqc.buka.data.repository.DataRepositoryApplication;
import xyz.ldqc.buka.data.repository.core.action.ActionResult;
import xyz.ldqc.buka.data.repository.core.action.support.CreateBucketAction;
import xyz.ldqc.buka.receiver.entity.CreateBucketEntity;
import xyz.ldqc.buka.receiver.entity.WebResponseEntity;
import xyz.ldqc.buka.receiver.server.handler.DataRepositoryRequestHandler;
import xyz.ldqc.buka.receiver.server.handler.annotation.RequestHandlerClass;
import xyz.ldqc.buka.receiver.server.response.Response;
import xyz.ldqc.buka.receiver.util.RequestUtil;
import xyz.ldqc.tightcall.protocol.http.HttpNioRequest;
import xyz.ldqc.tightcall.protocol.http.HttpNioResponse;
import xyz.ldqc.tightcall.util.StringUtil;

/**
 * 创建Bucket请求处理器
 * @author Fetters
 */
@RequestHandlerClass(path = "/create/bucket", method = "POST")
public class CreateBucketRequestHandler implements DataRepositoryRequestHandler {

  private DataRepositoryApplication dataRepositoryApplication;

  @Override
  public void setDataRepositoryApplication(DataRepositoryApplication dataRepositoryApplication) {
    this.dataRepositoryApplication = dataRepositoryApplication;
  }

  @Override
  public HttpNioResponse doHandler(HttpNioRequest request) {
    CreateBucketEntity createBucketEntity = RequestUtil.body2Obj(request, CreateBucketEntity.class);
    String repo = createBucketEntity.getRepo();
    String name = createBucketEntity.getName();
    if (StringUtil.isAnyBlank(repo, name)) {
      return Response.okJson(WebResponseEntity.fail("repo and name cannot be empty"));
    }
    CreateBucketAction createBucketAction = new CreateBucketAction(repo, name);
    ActionResult result = dataRepositoryApplication.execute(createBucketAction);
    return Response.okJson(result);
  }
}

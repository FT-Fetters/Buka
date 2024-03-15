package xyz.ldqc.buka.receiver.server.handler.support;

import xyz.ldqc.buka.data.repository.DataRepositoryApplication;
import xyz.ldqc.buka.data.repository.core.action.ActionResult;
import xyz.ldqc.buka.data.repository.core.action.support.CreateRepoAction;
import xyz.ldqc.buka.receiver.aware.DataRepositoryAware;
import xyz.ldqc.buka.receiver.entity.CreateRepoEntity;
import xyz.ldqc.buka.receiver.entity.WebResponseEntity;
import xyz.ldqc.buka.receiver.server.handler.DataRepositoryRequestHandler;
import xyz.ldqc.buka.receiver.server.handler.RequestHandler;
import xyz.ldqc.buka.receiver.server.handler.annotation.RequestHandlerClass;
import xyz.ldqc.buka.receiver.server.response.Response;
import xyz.ldqc.buka.receiver.util.ObjectUtil;
import xyz.ldqc.tightcall.protocol.http.HttpNioRequest;
import xyz.ldqc.tightcall.protocol.http.HttpNioResponse;
import xyz.ldqc.tightcall.util.StringUtil;

/**
 * 创建仓库请求处理器
 * @author Fetters
 */
@RequestHandlerClass(path = "/create/repo", method = "POST")
public class CreateRepositoryRequestHandler implements DataRepositoryRequestHandler {

  private DataRepositoryApplication dataRepositoryApplication;

  @Override
  public HttpNioResponse doHandler(HttpNioRequest request) {
    String body = request.getBody();
    CreateRepoEntity createRepoEntity = ObjectUtil.json2Obj(body, CreateRepoEntity.class);
    String name = createRepoEntity.getName();
    if (StringUtil.isBlank(name)){
      return Response.okJson(WebResponseEntity.fail("name cannot be empty"));
    }
    CreateRepoAction createRepoAction = new CreateRepoAction(name);
    ActionResult result = dataRepositoryApplication.execute(createRepoAction);
    return Response.okJson(result);
  }

  @Override
  public void setDataRepositoryApplication(DataRepositoryApplication dataRepositoryApplication) {
    this.dataRepositoryApplication = dataRepositoryApplication;
  }
}

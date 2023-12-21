package xyz.ldqc.buka.receiver.server.handler.support;

import xyz.ldqc.buka.data.repository.DataRepositoryApplication;
import xyz.ldqc.buka.receiver.aware.DataRepositoryAware;
import xyz.ldqc.buka.receiver.entity.CreateRepoEntity;
import xyz.ldqc.buka.receiver.server.handler.RequestHandler;
import xyz.ldqc.buka.receiver.server.handler.annotation.RequestHandlerClass;
import xyz.ldqc.buka.receiver.server.response.Response;
import xyz.ldqc.buka.receiver.util.ObjectUtil;
import xyz.ldqc.tightcall.protocol.http.HttpNioRequest;
import xyz.ldqc.tightcall.protocol.http.HttpNioResponse;

/**
 * @author Fetters
 */
@RequestHandlerClass(path = "/create/repo", method = "POST")
public class CreateRepositoryRequestHandler implements RequestHandler, DataRepositoryAware {

  private DataRepositoryApplication dataRepositoryApplication;

  @Override
  public HttpNioResponse doHandler(HttpNioRequest request) {
    String body = request.getBody();
    CreateRepoEntity createRepoEntity = ObjectUtil.json2Obj(body, CreateRepoEntity.class);
    String name = createRepoEntity.getName();
    String execute = dataRepositoryApplication.execute(name);
    HttpNioResponse response = Response.error(execute);
    return response;
  }

  @Override
  public void setDataRepositoryApplication(DataRepositoryApplication dataRepositoryApplication) {
    this.dataRepositoryApplication = dataRepositoryApplication;
  }
}

package xyz.ldqc.buka.receiver.server.handler.support;

import xyz.ldqc.buka.data.repository.DataRepositoryApplication;
import xyz.ldqc.buka.data.repository.core.action.ActionResult;
import xyz.ldqc.buka.data.repository.core.action.support.DeleteRepoAction;
import xyz.ldqc.buka.receiver.entity.DeleteRepoEntity;
import xyz.ldqc.buka.receiver.entity.WebResponseEntity;
import xyz.ldqc.buka.receiver.server.handler.DataRepositoryRequestHandler;
import xyz.ldqc.buka.receiver.server.handler.annotation.RequestHandlerClass;
import xyz.ldqc.buka.receiver.server.response.Response;
import xyz.ldqc.buka.receiver.util.RequestUtil;
import xyz.ldqc.tightcall.protocol.http.HttpNioRequest;
import xyz.ldqc.tightcall.protocol.http.HttpNioResponse;
import xyz.ldqc.tightcall.util.StringUtil;

/**
 * 删除仓库请求处理器
 * @author Fetters
 */
@RequestHandlerClass(path = "/del/repo", method = "POST")
public class DeleteRepositoryRequestHandler implements DataRepositoryRequestHandler {

    private DataRepositoryApplication dataRepositoryApplication;


    @Override
    public void setDataRepositoryApplication(DataRepositoryApplication dataRepositoryApplication) {
        this.dataRepositoryApplication = dataRepositoryApplication;
    }

    @Override
    public HttpNioResponse doHandler(HttpNioRequest request) {
        DeleteRepoEntity deleteRepoEntity = RequestUtil.body2Obj(request, DeleteRepoEntity.class);
        String name = deleteRepoEntity.getName();
        if (StringUtil.isBlank(name)) {
            return Response.okJson(WebResponseEntity.fail("name cannot be empty"));
        }
        DeleteRepoAction deleteRepoAction = new DeleteRepoAction(name);
        ActionResult result = dataRepositoryApplication.execute(deleteRepoAction);
        return Response.okJson(result);
    }
}

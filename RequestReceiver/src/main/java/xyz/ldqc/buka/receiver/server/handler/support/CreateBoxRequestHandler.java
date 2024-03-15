package xyz.ldqc.buka.receiver.server.handler.support;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import xyz.ldqc.buka.data.repository.DataRepositoryApplication;
import xyz.ldqc.buka.data.repository.core.action.ActionResult;
import xyz.ldqc.buka.data.repository.core.action.support.CreateBoxAction;
import xyz.ldqc.buka.receiver.entity.WebResponseEntity;
import xyz.ldqc.buka.receiver.server.handler.DataRepositoryRequestHandler;
import xyz.ldqc.buka.receiver.server.handler.annotation.RequestHandlerClass;
import xyz.ldqc.buka.receiver.server.response.Response;
import xyz.ldqc.tightcall.protocol.http.HttpNioRequest;
import xyz.ldqc.tightcall.protocol.http.HttpNioResponse;

/**
 * 创建Box请求处理器
 * @author Fetters
 */
@RequestHandlerClass(path = "/create/box", method = "POST")
public class CreateBoxRequestHandler implements DataRepositoryRequestHandler {

    private DataRepositoryApplication dataRepositoryApplication;

    @Override
    public void setDataRepositoryApplication(DataRepositoryApplication dataRepositoryApplication) {
        this.dataRepositoryApplication = dataRepositoryApplication;
    }

    @Override
    public HttpNioResponse doHandler(HttpNioRequest request) {
        String body = request.getBody();
        JSONObject bodyJson = JSONObject.parseObject(body);
        if (bodyJson == null) {
            return Response.okJson(WebResponseEntity.fail("fail"));
        }
        String name = bodyJson.getString("name");
        JSONArray latticeList = bodyJson.getJSONArray("lattice");

        if (name == null || latticeList == null) {
            return Response.okJson(WebResponseEntity.fail("Args are missing"));
        }
        CreateBoxAction createBoxAction = new CreateBoxAction(name, latticeList);
        ActionResult result = dataRepositoryApplication.execute(createBoxAction);
        return Response.okJson(result);
    }
}

package xyz.ldqc.buka.receiver.server.handler.support;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import xyz.ldqc.buka.data.repository.DataRepositoryApplication;
import xyz.ldqc.buka.data.repository.core.action.ActionResult;
import xyz.ldqc.buka.data.repository.core.action.support.QueryBucketAction;
import xyz.ldqc.buka.data.repository.core.engine.query.Sieve;
import xyz.ldqc.buka.data.repository.core.engine.query.SieveBuilder;
import xyz.ldqc.buka.data.repository.core.engine.query.SieveJsonParser;
import xyz.ldqc.buka.receiver.entity.WebResponseEntity;
import xyz.ldqc.buka.receiver.exception.BucketQueryException;
import xyz.ldqc.buka.receiver.server.handler.DataRepositoryRequestHandler;
import xyz.ldqc.buka.receiver.server.handler.annotation.RequestHandlerClass;
import xyz.ldqc.buka.receiver.server.response.Response;
import xyz.ldqc.tightcall.protocol.http.HttpNioRequest;
import xyz.ldqc.tightcall.protocol.http.HttpNioResponse;
import xyz.ldqc.tightcall.util.StringUtil;

/**
 * @author Fetters
 */
@RequestHandlerClass(path = "/query/bucket", method = "POST")
public class QueryBucketRequestHandler implements DataRepositoryRequestHandler {

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
        String repoName = bodyJson.getString("repo");
        String bucketName = bodyJson.getString("bucket");

        if (StringUtil.isAnyBlank(repoName, bucketName)) {
            return Response.okJson(WebResponseEntity.fail("repo or bucket is empty"));
        }
        JSONArray query = bodyJson.getJSONArray("query");
        if (query == null) {
            return Response.okJson(WebResponseEntity.fail("query is empty"));
        }

        ActionResult result = dataRepositoryApplication.execute(
            new QueryBucketAction(buildSieve(query), repoName, bucketName));
        return Response.okJson(result);
    }

    private Sieve buildSieve(JSONArray query) {
        SieveBuilder sieveBuilder = SieveBuilder.get();
        query.forEach(o -> {
            JSONObject q = (JSONObject) o;
            String name = q.getString("name");
            JSONObject condition = q.getJSONObject("condition");
            if (StringUtil.isBlank(name)) {
                throw new BucketQueryException("unknown name");
            }

            if (condition != null) {
                SieveJsonParser.parse(sieveBuilder, condition, name);
            }
        });
        return sieveBuilder.build();
    }


}

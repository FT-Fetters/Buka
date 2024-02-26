package xyz.ldqc.buka.data.repository.core.engine.query;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Fetters
 */
public class SieveJsonParser {

    private static Map<String, Parser> parserByType;

    private static final String VALUE = "value";


    public static void parse(SieveBuilder sieve, JSONObject json, String name) {
        if (parserByType == null) {
            initParser();
        }

        String type = json.getString("type");
        ConditionalConstruct conditionalConstruct = new ConditionalConstruct();
        parserByType.get(type).parse(conditionalConstruct, json);
        sieve.set(name, name, conditionalConstruct.getConditional());
    }

    private static Object preCheck(JSONObject json){
        Object o = json.get(VALUE);
        if (o == null){
            throw new IllegalArgumentException(VALUE);
        }
        return o;
    }

    private static void initParser() {
        Map<String, Parser> map = new HashMap<>(16);

        map.put("equal", (construct, json) -> {
            Object o = preCheck(json);
            construct.eq(o);
        });

        map.put("not equal", (construct, json) -> {
            Object o = preCheck(json);
            construct.ne(o);
        });

        map.put("less", (construct, json) -> {
            Object o = preCheck(json);
            construct.lt(o);
         });

        map.put("less equal", (construct, json) -> {
            Object o = preCheck(json);
            construct.le(o);
        });

        map.put("greater", (construct, json) -> {
            Object o = preCheck(json);
            construct.gt(o);
        });

        map.put("greater equal", (construct, json) -> {
            Object o = preCheck(json);
            construct.ge(o);
        });

        map.put("and", (construct, json) -> {
            Object o = preCheck(json);
            JSONArray andArray = (JSONArray) o;
            andArray.forEach( e -> {
                JSONObject andSingle = (JSONObject) e;
                String type = andSingle.getString("type");
                ConditionalConstruct conditionalConstruct = new ConditionalConstruct();
                parserByType.get(type).parse(conditionalConstruct, andSingle);
                construct.and(conditionalConstruct.getConditional());
            });
        });

        map.put("or", (construct, json) -> {
            Object o = preCheck(json);
            JSONArray andArray = (JSONArray) o;
            andArray.forEach( e -> {
                JSONObject andSingle = (JSONObject) e;
                String type = andSingle.getString("type");
                ConditionalConstruct conditionalConstruct = new ConditionalConstruct();
                parserByType.get(type).parse(conditionalConstruct, andSingle);
                construct.or(conditionalConstruct.getConditional());
            });
        });

        map.put("text match",(construct, json) -> {
            Object o = preCheck(json);
            construct.textMatch(o);
        });

        map.put("text limit",(construct, json) -> {
            Object o = preCheck(json);
            String lenStr = ((String) o).trim();
            int minLen = Integer.parseInt(lenStr.split(",")[0]);
            int maxLen = Integer.parseInt(lenStr.split(",")[1]);
            construct.textLimit(minLen, maxLen);
        });

        parserByType = map;
    }


    private interface Parser {

        /**
         * parse
         * @param construct construct
         * @param json json
         */
        void parse(ConditionalConstruct construct, JSONObject json);
    }
}

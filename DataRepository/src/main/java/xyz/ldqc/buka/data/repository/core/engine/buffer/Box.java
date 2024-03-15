package xyz.ldqc.buka.data.repository.core.engine.buffer;

import com.alibaba.fastjson2.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import xyz.ldqc.buka.data.repository.core.engine.structure.DataTypeEnum;
import xyz.ldqc.buka.data.repository.exception.BoxException;
import xyz.ldqc.buka.util.StrUtil;

/**
 * @author Fetters
 */
public class Box {

    private final String[] boxLatticeName;

    private final DataTypeEnum[] boxLatticeType;

    private final Map<String, ConcurrentSkipListMap<Object, Long>> indexMap;

    private final List<Object[]> items;

    private final String name;

    public Box(String[] boxLatticeName, DataTypeEnum[] boxLatticeType, String name) {
        this.boxLatticeName = boxLatticeName;
        this.boxLatticeType = boxLatticeType;
        indexMap = new HashMap<>();
        items = new ArrayList<>();
        for (String latticeName : this.boxLatticeName) {
            indexMap.put(latticeName, new ConcurrentSkipListMap<>());
        }
        this.name = name;
    }

    public void put(String item) {
        JSONObject j = JSONObject.parseObject(item);
        checkField(j);
        doPut(j);
    }

    private void doPut(JSONObject j) {
        int fieldLen = boxLatticeName.length;
        long id = items.size();
        Object[] itm = new Object[fieldLen];
        for (int i = 0; i < fieldLen; i++) {
            String name = boxLatticeName[i];
            ConcurrentSkipListMap<Object, Long> index = this.indexMap.get(name);
            itm[i] = j.get(name);
            index.put(itm[i], id);
        }
        items.add(itm);
    }

    public void putAll(Collection<String> items, boolean preCheck) {
        List<JSONObject> jsonObjects = new LinkedList<>();
        if (preCheck) {
            items.forEach(item -> {
                JSONObject json = JSONObject.parse(item);
                checkField(json);
                jsonObjects.add(json);
            });
            jsonObjects.forEach(this::doPut);
        } else {
            items.forEach(this::put);
        }
    }

    public void putAll(String... items) {
        Arrays.stream(items).forEach(this::put);
    }

    private void checkField(JSONObject j) {
        for (int i = 0; i < boxLatticeName.length; i++) {
            String n = boxLatticeName[i];
            DataTypeEnum t = boxLatticeType[i];
            if (!j.containsKey(n)) {
                // 如果字段不存在则抛出异常
                throw new BoxException("field (" + n + ") not found");
            }
            Object o = j.get(n);
            if (o != null && !t.judge(j.get(n).getClass())) {
                // 如果传入的参数与对应的类型不匹配则报错
                throw new BoxException(
                    StrUtil.format("{0} type should be {1}, but value [{2}] type is {3}", n, t, o)
                );
            }

        }
    }

    @Override
    public String toString() {
        return "Box{" +
            "boxLatticeName=" + Arrays.toString(boxLatticeName) +
            ", boxLatticeType=" + Arrays.toString(boxLatticeType) +
            '}';
    }

    public String getName() {
        return name;
    }
}

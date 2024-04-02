package xyz.ldqc.buka.data.repository.core.engine.buffer;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.ldqc.buka.data.repository.core.engine.structure.DataTypeEnum;
import xyz.ldqc.buka.data.repository.exception.BoxException;
import xyz.ldqc.buka.util.StrUtil;

/**
 * @author Fetters
 */
public class Box {

    private static final Logger log = LoggerFactory.getLogger(Box.class);

    private final String[] boxLatticeName;

    private final DataTypeEnum[] boxLatticeType;

    private final Map<String, ConcurrentSkipListMap<Object, Long>> indexMap;

    private final List<Object[]> items;

    private final String boxName;


    public Box(String[] boxLatticeName, DataTypeEnum[] boxLatticeType, String name) {
        this.boxLatticeName = boxLatticeName;
        this.boxLatticeType = boxLatticeType;
        indexMap = new HashMap<>();
        items = new ArrayList<>();
        for (String latticeName : this.boxLatticeName) {
            indexMap.put(latticeName, new ConcurrentSkipListMap<>());
        }
        this.boxName = name;
    }

    public void put(String item) {
        JSONObject j = JSONObject.parseObject(item);
        checkField(j);
        doPut(j);
    }

    private void doPut(JSONObject json) {
        int fieldLen = boxLatticeName.length;
        long id = items.size();
        Object[] itm = new Object[fieldLen];
        for (int i = 0; i < fieldLen; i++) {
            String name = boxLatticeName[i];
            ConcurrentSkipListMap<Object, Long> index = this.indexMap.get(name);
            itm[i] = json.get(name);
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

    public void putAll(JSONArray items) {
        items.forEach(item -> doPut((JSONObject) item));
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

    public String getBoxName() {
        return boxName;
    }

    /**
     * 拿取数据
     *
     * @param lattices 数据位
     */
    public JSONArray take(int limit, String... lattices) {
        if (lattices.length == 0) {
            lattices = Arrays.copyOf(boxLatticeName, boxLatticeName.length);
        }
        int[] idx = new int[lattices.length];
        // 检查数据位是否存在，并设置index
        for (int i = 0; i < lattices.length; i++) {
            for (String lattice : lattices) {
                if (!indexMap.containsKey(lattice)) {
                    throw new BoxException(StrUtil.format("Lattice ({1}) not found", lattice));
                }

            }
            for (int j = 0; j < boxLatticeName.length; j++) {
                if (boxLatticeName[i].equals(lattices[i])) {
                    idx[i] = j;
                    break;
                }
            }
        }
        JSONArray resultJsonArray = new JSONArray();
        for (Object[] item : items) {
            JSONObject resultJsonObject = new JSONObject();
            for (int i = 0; i < idx.length; i++) {
                resultJsonObject.put(lattices[i], item[idx[i]]);
            }
            resultJsonArray.add(resultJsonObject);
            if (limit > 0 && resultJsonArray.size() == limit) {
                break;
            }
        }
        return resultJsonArray;
    }

    public void storage(Path path) throws IOException {
        File storageFile = new File(path.toString());
        if (storageFile.exists()) {
            throw new FileAlreadyExistsException("File is exists");
        }
        File parentFile = storageFile.getParentFile();
        if (!parentFile.exists()) {
            Files.createDirectories(parentFile.toPath());
        }
        JSONArray data = take(-1, boxLatticeName);
        JSONObject storageData = new JSONObject();
        storageData.put("latticeName", boxLatticeName);
        storageData.put("latticeType", boxLatticeType);
        storageData.put("data", data);
        storageData.put("boxName", boxName);
        byte[] dataBytes = storageData.toString().getBytes(StandardCharsets.UTF_8);
        try (RandomAccessFile randomFile = new RandomAccessFile(storageFile, "rw")) {
            randomFile.write(dataBytes);
        } catch (Exception e) {
            log.error("Storage fail", e);
        }
    }


}

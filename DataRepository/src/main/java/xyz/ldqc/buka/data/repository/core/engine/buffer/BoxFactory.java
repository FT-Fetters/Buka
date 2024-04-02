package xyz.ldqc.buka.data.repository.core.engine.buffer;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import xyz.ldqc.buka.data.repository.core.engine.structure.DataTypeEnum;
import xyz.ldqc.buka.data.repository.exception.BoxFactoryException;
import xyz.ldqc.tightcall.util.StringUtil;

/**
 * @author Fetters
 */
public class BoxFactory {

    private static final ThreadLocal<BoxDefinition> DEFINITION_THREAD_LOCAL = new InheritableThreadLocal<>();

    private static final BoxFactory FACTORY_INSTANCE = new BoxFactory();

    private BoxFactory() {
    }

    public static BoxFactory get() {
        return BoxFactory.FACTORY_INSTANCE;
    }

    public static Box load(Path path, String rename) throws IOException {
        File loadFile = path.toFile();
        if (!loadFile.exists()) {
            throw new FileNotFoundException("File not found");
        }
        try (RandomAccessFile randomFile = new RandomAccessFile(loadFile, "r")) {
            byte[] dataBytes = new byte[(int) randomFile.length()];
            randomFile.read(dataBytes);
            JSONObject json = JSONObject.parseObject(new String(dataBytes, StandardCharsets.UTF_8));
            return doLoadFromJson(json, rename);
        }
    }

    public static Box load(Path path) throws IOException {
        return load(path, null);
    }

    private static Box doLoadFromJson(JSONObject json, String rename) {
        String boxName = StringUtil.isBlank(rename) ? json.getString("boxName") : rename;
        JSONArray latticeName = json.getJSONArray("latticeName");
        JSONArray latticeType = json.getJSONArray("latticeType");
        JSONArray data = json.getJSONArray("data");
        if (latticeName == null || latticeType == null || data == null
            || latticeName.size() != latticeType.size()) {
            return null;
        }
        BoxFactory boxFactory = BoxFactory.get();
        for (int i = 0; i < latticeName.size(); i++) {
            boxFactory.lattice(latticeName.getString(i),
                DataTypeEnum.valueOf(latticeType.getString(i)));
        }
        Box loadedBox = boxFactory.create(boxName);
        loadedBox.putAll(data);
        return loadedBox;

    }


    public BoxFactory lattice(String name, DataTypeEnum type) {
        BoxDefinition boxDefinition = DEFINITION_THREAD_LOCAL.get();
        if (boxDefinition == null) {
            boxDefinition = new BoxDefinition();
        }
        if (StringUtil.isBlank(name) || type == null) {
            throw new BoxFactoryException("Name can not be empty and type can not be null");
        }
        boxDefinition.getLatticeName().add(name);
        boxDefinition.getLatticeType().add(type);
        DEFINITION_THREAD_LOCAL.set(boxDefinition);
        return this;
    }

    public Box create(String name) {
        if (StringUtil.isBlank(name)){
            throw new BoxFactoryException("Name can not be empty");
        }
        BoxDefinition boxDefinition = DEFINITION_THREAD_LOCAL.get();
        if (boxDefinition == null) {
            return null;
        }
        Box box = new Box(boxDefinition.getLatticeName().toArray(new String[]{}),
            boxDefinition.latticeType.toArray(new DataTypeEnum[]{}), name);
        DEFINITION_THREAD_LOCAL.remove();
        return box;
    }

    public void clear() {
        DEFINITION_THREAD_LOCAL.remove();
    }


    private static class BoxDefinition {

        private final List<String> latticeName;

        private final List<DataTypeEnum> latticeType;

        public BoxDefinition() {
            this.latticeName = new ArrayList<>();
            this.latticeType = new ArrayList<>();
        }

        public List<String> getLatticeName() {
            return latticeName;
        }

        public List<DataTypeEnum> getLatticeType() {
            return latticeType;
        }
    }
}

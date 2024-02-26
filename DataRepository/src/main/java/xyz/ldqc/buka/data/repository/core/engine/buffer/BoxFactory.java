package xyz.ldqc.buka.data.repository.core.engine.buffer;

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

    private static final Map<String, Box> BOX_MAP = new ConcurrentHashMap<>();

    private static final ThreadLocal<BoxDefinition> DEFINITION_THREAD_LOCAL = new InheritableThreadLocal<>();

    private static final BoxFactory FACTORY_INSTANCE = new BoxFactory();

    private BoxFactory() {
    }

    public static BoxFactory get() {
        return BoxFactory.FACTORY_INSTANCE;
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
        BoxDefinition boxDefinition = DEFINITION_THREAD_LOCAL.get();
        if (boxDefinition == null) {
            return null;
        }
        Box box = new Box(boxDefinition.getLatticeName().toArray(new String[]{}),
            boxDefinition.latticeType.toArray(new DataTypeEnum[]{}), name);
        DEFINITION_THREAD_LOCAL.remove();
        BOX_MAP.put(name, box);
        return box;
    }

    public void clear() {
        DEFINITION_THREAD_LOCAL.remove();
    }

    public static Box getBox(String name) {
        return BOX_MAP.get(name);
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

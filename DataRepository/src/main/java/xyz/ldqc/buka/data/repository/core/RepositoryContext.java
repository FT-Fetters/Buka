package xyz.ldqc.buka.data.repository.core;

import java.lang.reflect.InvocationTargetException;
import xyz.ldqc.buka.data.repository.config.DataRepositoryConfig;
import xyz.ldqc.buka.data.repository.core.engine.RepositoryEngine;
import xyz.ldqc.buka.data.repository.core.handler.HandlerFactory;
import xyz.ldqc.buka.data.repository.exception.ContextException;
import xyz.ldqc.tightcall.util.ClassUtil;
import xyz.ldqc.tightcall.util.StringUtil;

/**
 * @author Fetters
 */
public class RepositoryContext {

    private final HandlerFactory handlerFactory;

    private RepositoryEngine engine;

    public RepositoryContext(DataRepositoryConfig config) {
        loadEngine(config);
        this.handlerFactory = HandlerFactory.getInstance(engine);
    }

    private void loadEngine(DataRepositoryConfig config) {
        String storageLocation = config.getStorageLocation();
        if (StringUtil.isBlank(storageLocation)) {
            storageLocation = config.getDefaultStorageLocation();
            config.setStorageLocation(storageLocation);
        }
        this.engine = doLoadEngine(config.getEngineClassName(), config);
    }


    /**
     * 加载仓库引擎
     *
     * @param engineClassName 引擎类名
     * @return RepositoryEngine
     */
    private RepositoryEngine doLoadEngine(String engineClassName, DataRepositoryConfig config) {
        ClassLoader classLoader = ClassUtil.getClassLoader();
        try {
            Class<?> engineClass = classLoader.loadClass(engineClassName);
            return (RepositoryEngine) engineClass.getConstructor(DataRepositoryConfig.class)
                .newInstance(config);
        } catch (ClassNotFoundException e) {
            throw new ContextException("unknown engine class");
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new ContextException(e.getMessage());
        }
    }

    public HandlerFactory getHandlerFactory() {
        return handlerFactory;
    }
}

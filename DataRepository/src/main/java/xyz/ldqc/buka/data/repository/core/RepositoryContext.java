package xyz.ldqc.buka.data.repository.core;

import xyz.ldqc.buka.data.repository.config.DataRepositoryConfig;
import xyz.ldqc.buka.data.repository.core.engine.RepositoryEngine;
import xyz.ldqc.buka.data.repository.core.handler.HandlerFactory;
import xyz.ldqc.tightcall.util.StringUtil;

/**
 * @author Fetters
 */
public class RepositoryContext {

  private final HandlerFactory handlerFactory;

  private RepositoryEngine engine;

  public RepositoryContext(DataRepositoryConfig config){
    this.handlerFactory = HandlerFactory.getInstance();
    loadEngine(config);
  }

  private void loadEngine(DataRepositoryConfig config){
    String storageLocation = config.getStorageLocation();
    if (StringUtil.isBlank(storageLocation)){
      storageLocation = config.getDefaultStorageLocation();
    }

  }

  public HandlerFactory getHandlerFactory() {
    return handlerFactory;
  }
}

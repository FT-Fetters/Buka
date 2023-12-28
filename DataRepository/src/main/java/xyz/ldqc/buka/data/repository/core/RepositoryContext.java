package xyz.ldqc.buka.data.repository.core;

import xyz.ldqc.buka.data.repository.config.DataRepositoryConfig;
import xyz.ldqc.buka.data.repository.core.handler.HandlerFactory;

/**
 * @author Fetters
 */
public class RepositoryContext {

  private HandlerFactory handlerFactory;

  public RepositoryContext(DataRepositoryConfig config){
    this.handlerFactory = HandlerFactory.getInstance();
  }

  public HandlerFactory getHandlerFactory() {
    return handlerFactory;
  }
}

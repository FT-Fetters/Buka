package xyz.ldqc.buka.data.repository.core.engine.support;

import xyz.ldqc.buka.data.repository.config.DataRepositoryConfig;
import xyz.ldqc.buka.data.repository.core.engine.RepositoryEngine;
import xyz.ldqc.buka.data.repository.core.engine.buffer.DataBufferPool;

/**
 * @author Fetters
 */
public class BukaRepositoryEngine implements RepositoryEngine {

  private final DataRepositoryConfig config;

  private final DataBufferPool bufferPool;

  public BukaRepositoryEngine(DataRepositoryConfig config){
    this.config = config;
    this.bufferPool = new DataBufferPool(config);
  }

  @Override
  public DataBufferPool getDataBufferPool() {
    return bufferPool;
  }
}

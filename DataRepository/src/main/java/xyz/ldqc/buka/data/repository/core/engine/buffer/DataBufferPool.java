package xyz.ldqc.buka.data.repository.core.engine.buffer;

import java.util.concurrent.ConcurrentHashMap;
import xyz.ldqc.buka.data.repository.config.DataRepositoryConfig;

/**
 * @author Fetters
 */
public class DataBufferPool {

  private ConcurrentHashMap<String, RepositoryBuffer> repoBuffer;

  public DataBufferPool(DataRepositoryConfig config){

  }
}

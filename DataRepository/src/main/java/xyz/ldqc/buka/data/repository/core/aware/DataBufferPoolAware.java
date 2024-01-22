package xyz.ldqc.buka.data.repository.core.aware;

import xyz.ldqc.buka.data.repository.core.engine.buffer.DataBufferPool;

/**
 * @author Fetters
 */
public interface DataBufferPoolAware {

  /**
   * 传入 dataBufferPool
   * @param dataBufferPool dataBufferPool
   */
  void setDataBufferPool(DataBufferPool dataBufferPool);

}

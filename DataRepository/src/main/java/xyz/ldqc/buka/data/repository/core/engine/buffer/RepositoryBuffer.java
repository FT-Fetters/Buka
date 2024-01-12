package xyz.ldqc.buka.data.repository.core.engine.buffer;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Fetters
 */
public class RepositoryBuffer {

  private String name;

  private ConcurrentHashMap<String, AbstractBucket> bucketMap;

  private ConcurrentHashMap<String, Box> boxMap;


}

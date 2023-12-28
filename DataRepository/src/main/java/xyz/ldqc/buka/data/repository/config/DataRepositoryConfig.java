package xyz.ldqc.buka.data.repository.config;

import xyz.ldqc.buka.data.repository.core.engine.RepositoryEngine;

/**
 * @author Fetters
 */
public class DataRepositoryConfig {

  private String storageLocation;

  private RepositoryEngine engine;

  public String getStorageLocation() {
    return storageLocation;
  }

  public void setStorageLocation(String storageLocation) {
    this.storageLocation = storageLocation;
  }

  public RepositoryEngine getEngine() {
    return engine;
  }

  public void setEngine(RepositoryEngine engine) {
    this.engine = engine;
  }

  public String getDefaultStorageLocation() {
    return DataRepositoryConfig.class
        .getProtectionDomain().getCodeSource().getLocation().getPath() + "/data";
  }
}

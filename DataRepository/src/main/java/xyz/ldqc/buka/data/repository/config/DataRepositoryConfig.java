package xyz.ldqc.buka.data.repository.config;


/**
 * @author Fetters
 */
public class DataRepositoryConfig {

  private String storageLocation;

  private String engineClassName;

  public String getEngineClassName() {
    return engineClassName;
  }

  public void setEngineClassName(String engineClassName) {
    this.engineClassName = engineClassName;
  }

  public String getStorageLocation() {
    return storageLocation;
  }

  public void setStorageLocation(String storageLocation) {
    this.storageLocation = storageLocation;
  }

  public String getDefaultStorageLocation() {
    return DataRepositoryConfig.class
        .getProtectionDomain().getCodeSource().getLocation().getPath() + "/data";
  }
}

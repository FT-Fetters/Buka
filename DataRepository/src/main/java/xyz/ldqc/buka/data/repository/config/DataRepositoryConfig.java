package xyz.ldqc.buka.data.repository.config;


/**
 * 数据仓库配置
 * @author Fetters
 */
public class DataRepositoryConfig {

  /**
   * 仓库物理磁盘存储位置
   */
  private String storageLocation;
  /**
   * 引擎类名
   */
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

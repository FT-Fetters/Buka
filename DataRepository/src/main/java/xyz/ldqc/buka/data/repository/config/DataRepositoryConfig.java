package xyz.ldqc.buka.data.repository.config;


import java.util.List;
import xyz.ldqc.buka.util.FileUtil;

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
  /**
   * 是否集群
   */
  private boolean isCluster;
  /**
   * 集群节点
   */
  private List<String> nodes;

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
    return FileUtil.getJarPath() + "/data";
  }

  public boolean isCluster() {
    return isCluster;
  }

  public void setCluster(boolean cluster) {
    isCluster = cluster;
  }

  public List<String> getNodes() {
    return nodes;
  }

  public void setNodes(List<String> nodes) {
    this.nodes = nodes;
  }
}

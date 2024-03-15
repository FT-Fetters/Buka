package xyz.ldqc.buka.boot.config;

/**
 * 配置项枚举
 * @author Fetters
 */
public enum ConfigEnum {

  /**
   * 端口
   */
  PORT("server.port", "8778"),
  /**
   * 数据仓库本地磁盘存储位置
   */
  DATA_REPOSITORY_STORAGE_LOCATION("data.repository.storage.location", null),
  /**
   * 数据仓库引擎完整类名
   */
  DATA_REPOSITORY_ENGINE("data.repository.engine", "xyz.ldqc.buka.data.repository.core.engine.support.BukaRepositoryEngine"),
  /**
   * 认证key
   */
  AUTH_KEY("auth.key", "123456");

  private final String property;

  private final String defaultValue;

  ConfigEnum(String property, String defaultValue){
    this.property = property;
    this.defaultValue = defaultValue;
  }

  public String getDefaultValue(){
    return this.defaultValue;
  }

  @Override
  public String toString() {
    return property;
  }
}

package xyz.ldqc.buka.boot.config;

/**
 * @author Fetters
 */
public interface ConfigLoader {

  /**
   * 获取配置项的值
   * @param key key
   * @return value
   */
  public String getConfigItem(String key);



}

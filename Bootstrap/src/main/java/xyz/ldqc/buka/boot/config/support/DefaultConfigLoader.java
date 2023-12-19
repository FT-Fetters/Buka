package xyz.ldqc.buka.boot.config.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import xyz.ldqc.buka.boot.config.ConfigLoader;
import xyz.ldqc.buka.boot.config.Resource;

/**
 * @author Fetters
 */
public class DefaultConfigLoader implements ConfigLoader {

  private final Resource configResource;

  private Properties properties;

  public DefaultConfigLoader(Resource resource){
    this.configResource = resource;
    doLoadConfig();
  }

  private void doLoadConfig(){
    InputStream inputStream = configResource.getInputStream();
    Properties properties = new Properties();
    try {
      properties.load(inputStream);
      configResource.getInputStream().close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.properties = properties;
  }

  @Override
  public String getConfigItem(String key) {
    return properties.getProperty(key);
  }
}

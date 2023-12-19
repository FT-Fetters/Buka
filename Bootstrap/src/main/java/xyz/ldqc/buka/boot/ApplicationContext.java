package xyz.ldqc.buka.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.ldqc.buka.boot.config.ConfigEntity;
import xyz.ldqc.buka.boot.config.ConfigEnum;
import xyz.ldqc.buka.boot.config.ConfigLoader;
import xyz.ldqc.buka.boot.config.Resource;
import xyz.ldqc.buka.boot.config.support.DefaultConfigLoader;
import xyz.ldqc.buka.boot.config.support.DefaultResourceLoader;
import xyz.ldqc.buka.receiver.RequestReceiver;
import xyz.ldqc.tightcall.util.StringUtil;

/**
 * @author Fetters
 */
public class ApplicationContext {

  private final static Logger log = LoggerFactory.getLogger(ApplicationContext.class);

  private Resource configResource;

  private ConfigLoader configLoader;

  private ConfigEntity config;

  private RequestReceiver receiver;


  public void loadContext() {
    loadConfigResource();
    loadConfig();
    loadReceiver();
  }

  private void loadConfigResource() {
    DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
    this.configResource = resourceLoader.load("config.properties");
  }

  private void loadConfig() {
    if (configResource == null){
      loadDefaultConfig();
      return;
    }
    this.configLoader = new DefaultConfigLoader(configResource);
    ConfigEnum[] properties = ConfigEnum.values();
    this.config = new ConfigEntity();
    for (ConfigEnum property : properties) {
      String value = configLoader.getConfigItem(property.toString());
      if (StringUtil.isNotBlank(value)){
        config.setProperty(property, value);
        log.info("config property {} set value: {}", property, value);
      }else {
        log.info("config property {} set default value: {}", property, property.getDefaultValue());
        config.setPropertyDefault(property);
      }
    }
  }

  private void loadDefaultConfig(){
    ConfigEnum[] properties = ConfigEnum.values();
    this.config = new ConfigEntity();
    for (ConfigEnum property : properties) {
      config.setPropertyDefault(property);
    }
  }

  private void loadReceiver(){
    int port = Integer.parseInt(config.getValue(ConfigEnum.PORT));
    this.receiver = RequestReceiver.create(port);
  }

}

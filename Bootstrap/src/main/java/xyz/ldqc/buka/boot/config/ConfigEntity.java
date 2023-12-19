package xyz.ldqc.buka.boot.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Fetter
 */
public class ConfigEntity {

  private final Map<ConfigEnum, String> map;

  public ConfigEntity(){
    this.map = new HashMap<>();
  }

  public void setProperty(ConfigEnum property, String value){
    this.map.put(property, value);
  }

  public void setPropertyDefault(ConfigEnum property){
    this.map.put(property, property.getDefaultValue());
  }
  public String getValue(ConfigEnum property){
    return map.get(property);
  }




}

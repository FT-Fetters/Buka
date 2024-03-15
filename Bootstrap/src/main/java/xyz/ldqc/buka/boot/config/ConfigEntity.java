package xyz.ldqc.buka.boot.config;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置实体类
 * @author Fetter
 */
public class ConfigEntity {

  private final Map<ConfigEnum, String> map;

  public ConfigEntity(){
    this.map = new EnumMap<>(ConfigEnum.class);
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

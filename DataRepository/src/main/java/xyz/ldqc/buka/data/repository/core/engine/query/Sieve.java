package xyz.ldqc.buka.data.repository.core.engine.query;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 筛选类，用于存放筛选条件
 * @author Fetters
 */
public class Sieve {

  /**
   * 字段对应的筛选条件
   */
  private final Map<String, Entry<String, Conditional>> conditionalMap;

  public Sieve(Map<String, Entry<String, Conditional>> conditionalMap){
    this.conditionalMap = conditionalMap;
  }
  public Map<String, Entry<String, Conditional>> getConditionalMap(){
    return this.conditionalMap;
  }

  public Entry<String, Conditional> getConditional(String name){
    return conditionalMap.get(name);
  }

  public Set<Entry<String, Entry<String, Conditional>>> entrySet(){
    return conditionalMap.entrySet();
  }

}

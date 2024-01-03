package xyz.ldqc.buka.data.repository.core.engine.structure;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author Fetters
 */
public class FieldTable {

  private ConcurrentSkipListMap<String, DataIndex> fieldDataIndexMap;

  public FieldTable(){
    this.fieldDataIndexMap = new ConcurrentSkipListMap<>();

  }



}

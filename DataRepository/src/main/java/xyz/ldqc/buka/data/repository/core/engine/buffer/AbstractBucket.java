package xyz.ldqc.buka.data.repository.core.engine.buffer;

import java.util.Map;
import xyz.ldqc.buka.data.repository.core.engine.structure.DataLink;

/**
 * @author Fetters
 */
public abstract class AbstractBucket {

  protected String name;

  protected Map<String, DataLink<?>> fieldMap;

  public String getName() {
    return name;
  }
}

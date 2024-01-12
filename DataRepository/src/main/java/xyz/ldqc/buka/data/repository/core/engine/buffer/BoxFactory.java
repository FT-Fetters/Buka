package xyz.ldqc.buka.data.repository.core.engine.buffer;

import java.util.ArrayList;
import java.util.List;
import xyz.ldqc.buka.data.repository.core.engine.structure.DataTypeEnum;
import xyz.ldqc.buka.data.repository.exception.BoxFactoryException;
import xyz.ldqc.tightcall.util.StringUtil;

/**
 * @author Fetters
 */
public class BoxFactory {

  private static final ThreadLocal<BoxDefinition> DEFINITION_THREAD_LOCAL = new InheritableThreadLocal<>();

  private static final BoxFactory FACTORY_INSTANCE = new BoxFactory();

  private BoxFactory(){}

  public static BoxFactory get(){
    return BoxFactory.FACTORY_INSTANCE;
  }

  public BoxFactory lattice(String name, DataTypeEnum type){
    BoxDefinition boxDefinition = DEFINITION_THREAD_LOCAL.get();
    if (boxDefinition == null){
      boxDefinition = new BoxDefinition();
    }
    if (StringUtil.isBlank(name) || type == null){
      throw new BoxFactoryException("Name can not be empty and type can not be null");
    }
    boxDefinition.getLatticeName().add(name);
    boxDefinition.getLatticeType().add(type);
    DEFINITION_THREAD_LOCAL.set(boxDefinition);
    return this;
  }

  public Box create(){
    BoxDefinition boxDefinition = DEFINITION_THREAD_LOCAL.get();
    if (boxDefinition == null){
      return null;
    }
    Box box = new Box(boxDefinition.getLatticeName().toArray(new String[]{}),
        boxDefinition.latticeType.toArray(new DataTypeEnum[]{}));
    DEFINITION_THREAD_LOCAL.remove();
    return box;
  }

  public void clear(){
    DEFINITION_THREAD_LOCAL.remove();
  }




  private static class BoxDefinition{
    private final List<String> latticeName;

    private final List<DataTypeEnum> latticeType;

    public BoxDefinition(){
      this.latticeName = new ArrayList<>();
      this.latticeType = new ArrayList<>();
    }

    public List<String> getLatticeName() {
      return latticeName;
    }

    public List<DataTypeEnum> getLatticeType() {
      return latticeType;
    }
  }
}

package xyz.ldqc.buka.data.repository.core.engine.query.support;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import xyz.ldqc.buka.data.repository.core.engine.query.BoolTypeEnum;
import xyz.ldqc.buka.data.repository.core.engine.query.Conditional;
import xyz.ldqc.buka.data.repository.core.engine.query.MultipleConditional;

/**
 * @author Fetters
 */
public class OrConditional implements MultipleConditional {

  private final List<Conditional> conditionals;

  @SafeVarargs
  public OrConditional(Conditional... conditionals){
    this.conditionals = new LinkedList<>();
    this.conditionals.addAll(Arrays.asList(conditionals));
  }

  @Override
  public boolean judge(Object obj) {
    for (Conditional conditional : conditionals) {
      boolean judge = conditional.judge(obj);
      if (judge){
        return true;
      }
    }
    return false;
  }

  @Override
  public List<Conditional> getConditionals() {
    return this.conditionals;
  }

  @Override
  public void addConditional(Conditional conditional, BoolTypeEnum type) {
    if (conditional != null){
      this.conditionals.add(conditional);
    }
  }
}

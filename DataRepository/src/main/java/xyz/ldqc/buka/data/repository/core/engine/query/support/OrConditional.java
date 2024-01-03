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
public class OrConditional<T> implements MultipleConditional<T> {

  private final List<Conditional<T>> conditionals;

  @SafeVarargs
  public OrConditional(Conditional<T>... conditionals){
    this.conditionals = new LinkedList<>();
    this.conditionals.addAll(Arrays.asList(conditionals));
  }

  @Override
  public boolean judge(T obj) {
    for (Conditional<T> conditional : conditionals) {
      boolean judge = conditional.judge(obj);
      if (judge){
        return true;
      }
    }
    return false;
  }

  @Override
  public List<Conditional<T>> getConditionals() {
    return this.conditionals;
  }

  @Override
  public void addConditional(Conditional<T> conditional, BoolTypeEnum type) {
    if (conditional != null){
      this.conditionals.add(conditional);
    }
  }
}

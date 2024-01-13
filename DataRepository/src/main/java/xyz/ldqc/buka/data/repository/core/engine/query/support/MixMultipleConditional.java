package xyz.ldqc.buka.data.repository.core.engine.query.support;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import xyz.ldqc.buka.data.repository.core.engine.query.BoolTypeEnum;
import xyz.ldqc.buka.data.repository.core.engine.query.Conditional;
import xyz.ldqc.buka.data.repository.core.engine.query.MultipleConditional;

/**
 * @author Fetters 混合多条件
 */
public class MixMultipleConditional implements MultipleConditional {

  private final List<ConditionalBody> conditionalBodyList;

  public MixMultipleConditional() {
    this.conditionalBodyList = new LinkedList<>();
  }

  @Override
  public boolean judge(Object obj) {
    Deque<Boolean> boolStack = new LinkedList<>();
    Deque<BoolTypeEnum> opStack = new LinkedList<>();
    doJudge(obj, boolStack, opStack);
    // 且 的优先级大于 或 先处理 且 逻辑
    Deque<Boolean> tmpStack = handlerAndOp(boolStack, opStack);
    // 剩下的都是 或，直接对所有进行或操作
    while (!tmpStack.isEmpty()) {
      Boolean pop = tmpStack.pop();
      if (pop.equals(Boolean.TRUE)) {
        return true;
      }
    }
    return false;
  }

  private void doJudge(Object obj, Deque<Boolean> boolStack, Deque<BoolTypeEnum> opStack) {
    int i = 0;
    for (ConditionalBody conditionalBody : conditionalBodyList) {
      boolean b = conditionalBody.conditional.judge(obj);
      boolStack.push(b);
      if (i > 0) {
        opStack.push(conditionalBody.getType());
      }
      i++;
    }
  }

  private static Deque<Boolean> handlerAndOp(Deque<Boolean> boolStack,
      Deque<BoolTypeEnum> opStack) {
    Deque<Boolean> tStk = new LinkedList<>();
    while (!boolStack.isEmpty() && !opStack.isEmpty()) {
      boolean riBool = boolStack.pop();
      boolean leBool = boolStack.pop();
      BoolTypeEnum opType = opStack.pop();

      // 是否是 与 操作
      if (opType == BoolTypeEnum.AND) {
        // 进行 与 操作后放入栈中
        boolean and = leBool && riBool;
        tStk.push(and);
      } else {
        tStk.push(riBool);
        tStk.push(leBool);
      }
    }
    return tStk;
  }

  @Override
  public List<Conditional> getConditionals() {
    return conditionalBodyList.stream().map(ConditionalBody::getConditional)
        .collect(Collectors.toList());
  }

  @Override
  public void addConditional(Conditional conditional, BoolTypeEnum type) {
    if (conditional == null) {
      return;
    }
    ConditionalBody conditionalBody = new ConditionalBody(conditional, type);
    this.conditionalBodyList.add(conditionalBody);
  }

  private class ConditionalBody {

    private final Conditional conditional;

    private final BoolTypeEnum type;

    public ConditionalBody(Conditional conditional, BoolTypeEnum type) {
      this.conditional = conditional;
      this.type = type;
    }

    public Conditional getConditional() {
      return conditional;
    }

    public BoolTypeEnum getType() {
      return type;
    }
  }
}

package xyz.ldqc.buka.data.repository.core.engine.query;

import java.util.function.Consumer;
import xyz.ldqc.buka.data.repository.core.engine.query.support.AndConditional;
import xyz.ldqc.buka.data.repository.core.engine.query.support.EqualConditional;
import xyz.ldqc.buka.data.repository.core.engine.query.support.GreaterEqualThanConditional;
import xyz.ldqc.buka.data.repository.core.engine.query.support.GreaterThanConditional;
import xyz.ldqc.buka.data.repository.core.engine.query.support.LessEqualThanConditional;
import xyz.ldqc.buka.data.repository.core.engine.query.support.LessThanConditional;
import xyz.ldqc.buka.data.repository.core.engine.query.support.MixMultipleConditional;
import xyz.ldqc.buka.data.repository.core.engine.query.support.NotEqualConditional;
import xyz.ldqc.buka.data.repository.core.engine.query.support.TextLimitConditional;
import xyz.ldqc.buka.data.repository.core.engine.query.support.TextMatchConditional;

/**
 * @author Fetters
 */
public class ConditionalConstruct {

  Conditional rootConditional;

  public ConditionalConstruct() {
    // there anything need to construct
  }

  public ConditionalConstruct eq(Object val) {
    EqualConditional conditional = new EqualConditional(val);
    return addSimpleConditional(conditional);
  }

  public ConditionalConstruct ne(Object val) {
    NotEqualConditional conditional = new NotEqualConditional(val);
    return addSimpleConditional(conditional);
  }

  public ConditionalConstruct gt(Object val) {
    GreaterThanConditional conditional = new GreaterThanConditional(val);
    return addSimpleConditional(conditional);
  }

  public ConditionalConstruct lt(Object val) {
    LessThanConditional conditional = new LessThanConditional(val);
    return addSimpleConditional(conditional);
  }

  public ConditionalConstruct ge(Object val) {
    GreaterEqualThanConditional conditional = new GreaterEqualThanConditional(val);
    return addSimpleConditional(conditional);
  }

  public ConditionalConstruct le(Object val) {
    LessEqualThanConditional conditional = new LessEqualThanConditional(val);
    return addSimpleConditional(conditional);
  }

  public ConditionalConstruct and(Consumer<ConditionalConstruct> consumer) {
    ConditionalConstruct conditional = new ConditionalConstruct();
    consumer.accept(conditional);
    rootConditional = new AndConditional(rootConditional, conditional.getConditional());
    return this;
  }

  public ConditionalConstruct and(Conditional conditional){
    rootConditional = new AndConditional(rootConditional, conditional);
    return this;
  }

  public ConditionalConstruct or(Consumer<ConditionalConstruct> consumer){
    ConditionalConstruct conditional = new ConditionalConstruct();
    consumer.accept(conditional);
    MixMultipleConditional mix = new MixMultipleConditional();
    mix.addConditional(rootConditional, BoolTypeEnum.AND);
    mix.addConditional(conditional.getConditional(), BoolTypeEnum.OR);
    rootConditional = mix;
    return this;
  }

  public ConditionalConstruct textMatch(Object val){
    TextMatchConditional textMatchConditional = new TextMatchConditional(val);
    return addSimpleConditional(textMatchConditional);
  }

  public ConditionalConstruct textLimit(int minLen, int maxLen){
    TextLimitConditional textLimitConditional = new TextLimitConditional(minLen, maxLen);
    return addSimpleConditional(textLimitConditional);
  }

  private ConditionalConstruct addSimpleConditional(Conditional conditional) {
    if (rootConditional == null) {
      rootConditional = conditional;
      return this;
    }
    if (rootConditional instanceof MultipleConditional) {
      MultipleConditional multipleConditional = (MultipleConditional) rootConditional;
      multipleConditional.addConditional(conditional, BoolTypeEnum.AND);
      return this;
    }
    rootConditional = new AndConditional(rootConditional, conditional);
    return this;
  }

  public boolean judge(Object obj){
    return rootConditional.judge(obj);
  }

  public Conditional getConditional(){
    return rootConditional;
  }

}

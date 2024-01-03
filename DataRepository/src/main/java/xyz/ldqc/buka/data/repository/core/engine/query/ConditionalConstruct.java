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
public class ConditionalConstruct<T> {

  Conditional<T> rootConditional;

  public ConditionalConstruct() {

  }

  public ConditionalConstruct<T> eq(T val) {
    EqualConditional<T> conditional = new EqualConditional<>(val);
    return addSimpleConditional(conditional);
  }

  public ConditionalConstruct<T> ne(T val) {
    NotEqualConditional<T> conditional = new NotEqualConditional<>(val);
    return addSimpleConditional(conditional);
  }

  public ConditionalConstruct<T> gt(Comparable<T> val) {
    GreaterThanConditional<T> conditional = new GreaterThanConditional<>(val);
    return addSimpleConditional(conditional);
  }

  public ConditionalConstruct<T> lt(Comparable<T> val) {
    LessThanConditional<T> conditional = new LessThanConditional<>(val);
    return addSimpleConditional(conditional);
  }

  public ConditionalConstruct<T> ge(Comparable<T> val) {
    GreaterEqualThanConditional<T> conditional = new GreaterEqualThanConditional<>(val);
    return addSimpleConditional(conditional);
  }

  public ConditionalConstruct<T> le(Comparable<T> val) {
    LessEqualThanConditional<T> conditional = new LessEqualThanConditional<>(val);
    return addSimpleConditional(conditional);
  }

  public ConditionalConstruct<T> and(Consumer<ConditionalConstruct<T>> consumer) {
    ConditionalConstruct<T> conditional = new ConditionalConstruct<>();
    consumer.accept(conditional);
    rootConditional = new AndConditional<>(rootConditional, conditional.getConditional());
    return this;
  }

  public ConditionalConstruct<T> and(Conditional<T> conditional){
    rootConditional = new AndConditional<>(rootConditional, conditional);
    return this;
  }

  public ConditionalConstruct<T> or(Consumer<ConditionalConstruct<T>> consumer){
    ConditionalConstruct<T> conditional = new ConditionalConstruct<>();
    consumer.accept(conditional);
    MixMultipleConditional<T> mix = new MixMultipleConditional<>();
    mix.addConditional(rootConditional, BoolTypeEnum.AND);
    mix.addConditional(conditional.getConditional(), BoolTypeEnum.OR);
    rootConditional = mix;
    return this;
  }

  public ConditionalConstruct<T> textMatch(T val){
    TextMatchConditional<T> textMatchConditional = new TextMatchConditional<>(val);
    return addSimpleConditional(textMatchConditional);
  }

  public ConditionalConstruct<T> textLimit(int minLen, int maxLen){
    TextLimitConditional<T> textLimitConditional = new TextLimitConditional<>(minLen, maxLen);
    return addSimpleConditional(textLimitConditional);
  }

  private ConditionalConstruct<T> addSimpleConditional(Conditional<T> conditional) {
    if (rootConditional == null) {
      rootConditional = conditional;
      return this;
    }
    if (rootConditional instanceof MultipleConditional) {
      MultipleConditional<T> multipleConditional = (MultipleConditional<T>) rootConditional;
      multipleConditional.addConditional(conditional, BoolTypeEnum.AND);
      return this;
    }
    rootConditional = new AndConditional<>(rootConditional, conditional);
    return this;
  }

  public boolean judge(T obj){
    return rootConditional.judge(obj);
  }

  public Conditional<T> getConditional(){
    return rootConditional;
  }


}

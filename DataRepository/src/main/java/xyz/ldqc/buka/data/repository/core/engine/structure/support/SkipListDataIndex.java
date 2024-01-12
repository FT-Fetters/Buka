package xyz.ldqc.buka.data.repository.core.engine.structure.support;

import java.util.List;
import java.util.Random;
import xyz.ldqc.buka.data.repository.core.engine.structure.DataIndex;

/**
 * @author Fetters
 */
public class SkipListDataIndex<T> implements DataIndex<T> {

  private Node<T> headNode;

  int highLevel;

  Random random;

  final int MAX_LEVEL = 32;

  public SkipListDataIndex(){
    random = new Random(System.currentTimeMillis());
    highLevel = 0;
  }


  @Override
  public List<T> eq(Object obj) {
    return null;
  }

  @Override
  public List<T> ne(Object obj) {
    return null;
  }

  @Override
  public List<T> ge(Object obj) {
    return null;
  }

  @Override
  public List<T> le(Object obj) {
    return null;
  }

  @Override
  public List<T> gt(Object obj) {
    return null;
  }

  @Override
  public List<T> lt(Object obj) {
    return null;
  }

  private static class Node<T>{
    T value;
    Node<T> next, down;

    private Node(T value){
      this.value = value;
    }
  }
}

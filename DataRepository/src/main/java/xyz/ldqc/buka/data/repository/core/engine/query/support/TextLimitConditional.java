package xyz.ldqc.buka.data.repository.core.engine.query.support;

import xyz.ldqc.buka.data.repository.core.engine.query.Conditional;

/**
 * @author Fetters
 * 文本长度限制
 */
  public class TextLimitConditional implements Conditional {

  private final int minLen;
  private final int maxLen;

  public TextLimitConditional(int minLen, int maxLen) {
    this.minLen = minLen;
    this.maxLen = maxLen;
  }

  @Override
  public boolean judge(Object obj) {
    int len = obj.toString().length();
    if (minLen != -1 && len < minLen) {
      return false;
    }
    if (maxLen != -1 && len > maxLen) {
      return false;
    }
    return true;
  }
}

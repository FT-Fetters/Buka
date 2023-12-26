package xyz.ldqc.buka.data.repository.script.token;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Fetters
 */
public class TokenParser {

  private static final String[] KEYWORDS = {"CREATE", "REPO"};
  private static final String[] OPERATORS = {"+", "-", "*", "/", ">=", "<=", ">", "<", "=="};
  private static final char[] DELIMITERS = {'(', ')', '{', '}', ';'};

  private TokenParser() {
  }

  public static List<Token> parse(String script) {
    return doParse(script);
  }

  private static List<Token> doParse(String script) {
    List<Token> tokens = new LinkedList<>();
    StringBuilder currentToken = new StringBuilder();
    int len = script.length();
    int i = 0;
    while (i < len) {
      char ch = script.charAt(i);

      if (isWhitespace(ch)) {
        i = handleWhitespace(tokens, currentToken, i);
      } else if (isOperator(ch)) {
        i = handleOperator(tokens, currentToken, script, i);
      } else if (isDelimiter(ch)) {
        i = handleDelimiter(tokens, currentToken, ch, i);
      } else {
        currentToken.append(ch);
        i++;
      }
    }
    addToken(tokens, currentToken);
    return tokens;
  }

  private static int handleWhitespace(List<Token> tokens, StringBuilder currentToken, int index) {
    addToken(tokens, currentToken);
    return index + 1;
  }

  private static int handleOperator(List<Token> tokens, StringBuilder currentToken, String script,
      int index) {
    addToken(tokens, currentToken);
    if (index < script.length() - 1 && isTwoCharOperator(script, index)) {
      String twoCharOp = "" + script.charAt(index) + script.charAt(index + 1);
      tokens.add(new Token(TokenType.SYMBOL, twoCharOp));
      return index + 2;
    } else {
      tokens.add(new Token(TokenType.SYMBOL, String.valueOf(script.charAt(index))));
      return index + 1;
    }
  }

  private static int handleDelimiter(List<Token> tokens, StringBuilder currentToken, char ch,
      int index) {
    addToken(tokens, currentToken);
    tokens.add(new Token(TokenType.SYMBOL, String.valueOf(ch)));
    return index + 1;
  }


  private static void addToken(List<Token> tokens, StringBuilder currentToken) {
    if (currentToken.length() <= 0) {
      return;
    }
    String token = currentToken.toString();

    if (isKeyWord(token)) {
      tokens.add(new Token(TokenType.KEYWORD, token));
    } else {
      tokens.add(new Token(TokenType.IDENTIFIER, token));
    }
    currentToken.setLength(0);
  }

  private static boolean isKeyWord(String token) {
    return Arrays.asList(KEYWORDS).contains(token.toUpperCase());
  }

  private static boolean isWhitespace(char ch) {
    return Character.isWhitespace(ch);
  }

  private static boolean isOperator(String token) {
    return Arrays.asList(OPERATORS).contains(token);
  }

  private static boolean isOperator(char ch) {
    return Arrays.asList(OPERATORS).contains(String.valueOf(ch));
  }

  private static boolean isTwoCharOperator(String script, int i) {
    // 判断i+1个字符是否超出长度
    if (i >= script.length() - 1) {
      return false;
    }
    String twoCharOp = "" + script.charAt(i) + script.charAt(i + 1);
    return isOperator(twoCharOp);
  }

  private static boolean isDelimiter(char ch) {
    for (char delimiter : DELIMITERS) {
      if (delimiter == ch) {
        return true;
      }
    }
    return false;
  }

}

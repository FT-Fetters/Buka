package xyz.ldqc.buka.data.repository.script.syntax;

import java.util.List;
import xyz.ldqc.buka.data.repository.script.ScriptSupport;
import xyz.ldqc.buka.data.repository.script.token.Token;

/**
 * @author Fetters
 */
public class SyntaxAnalyzer {

  private SyntaxAnalyzer() {
  }

  public ScriptSupport parse(List<Token> tokens){
    if (tokens.isEmpty()){
      return null;
    }
    return null;
  }

}

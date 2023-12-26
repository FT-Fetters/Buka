package xyz.ldqc.buka.data.repository.script;

import java.util.ArrayList;
import java.util.List;
import xyz.ldqc.buka.data.repository.script.token.Token;
import xyz.ldqc.buka.data.repository.script.token.TokenParser;
import xyz.ldqc.tightcall.util.StringUtil;

/**
 * @author Fetters
 */
public class ScriptParser {

  private ScriptParser(){}

  public static List<ScriptSupport> parse(String script){
    return doParse(script);
  }

  private static List<ScriptSupport> doParse(String script){
    String[] scripts = script.split(";");
    List<ScriptSupport> scriptSupports = new ArrayList<>();
    for (String single : scripts) {
      if (StringUtil.isBlank(single)){
        continue;
      }
      scriptSupports.add(parseSingle(single));
    }
    return scriptSupports;
  }

  private static ScriptSupport parseSingle(String script){
    List<Token> tokens = TokenParser.parse(script);

    return null;
  }






}

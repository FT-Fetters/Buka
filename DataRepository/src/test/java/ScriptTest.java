import java.util.List;
import org.junit.Test;
import xyz.ldqc.buka.data.repository.script.ScriptParser;
import xyz.ldqc.buka.data.repository.script.ScriptSupport;
import xyz.ldqc.buka.data.repository.script.token.Token;
import xyz.ldqc.buka.data.repository.script.token.TokenParser;

public class ScriptTest {

  @Test
  public void testScriptParser(){
    String script = "create repo test;";
    List<ScriptSupport> scriptSupports = ScriptParser.parse(script);

  }

  @Test
  public void testTokenParser(){
    String script = "create repo test >= == + >abc";
    List<Token> tokens = TokenParser.parse(script);
  }

}

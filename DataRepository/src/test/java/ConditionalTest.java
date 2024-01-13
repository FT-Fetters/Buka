import java.util.function.Consumer;
import org.junit.Test;
import xyz.ldqc.buka.data.repository.core.engine.query.Conditional;
import xyz.ldqc.buka.data.repository.core.engine.query.ConditionalConstruct;
import xyz.ldqc.buka.data.repository.core.engine.query.support.AndConditional;
import xyz.ldqc.buka.data.repository.core.engine.query.support.EqualConditional;
import xyz.ldqc.buka.data.repository.core.engine.query.support.GreaterThanConditional;

public class ConditionalTest {

  @Test
  public void testConditional(){
    EqualConditional equalConditional = new EqualConditional(12);
    GreaterThanConditional greaterThanConditional = new GreaterThanConditional(12);

    AndConditional andConditional = new AndConditional(equalConditional, greaterThanConditional);

    System.out.println(andConditional.judge(12));
  }

  @Test
  public void testMixConditional(){
    String str = "abc";

    ConditionalConstruct construct = new ConditionalConstruct();
    construct.eq("ab").and(conditionalConstruct -> {
      conditionalConstruct.eq("bc");
    });

    System.out.println(construct.judge(str));


  }

}

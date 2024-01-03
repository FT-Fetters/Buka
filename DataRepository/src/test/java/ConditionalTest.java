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
    EqualConditional<Integer> equalConditional = new EqualConditional<>(12);
    GreaterThanConditional<Integer> greaterThanConditional = new GreaterThanConditional<>(12);

    AndConditional<Integer> andConditional = new AndConditional<>(equalConditional, greaterThanConditional);

    System.out.println(andConditional.judge(12));
  }

  @Test
  public void testMixConditional(){
    String str = "abc";

    ConditionalConstruct<String> construct = new ConditionalConstruct<>();
    construct.eq("ab").or(orCon -> orCon.textMatch("bc"));

    System.out.println(construct.judge(str));


  }

}

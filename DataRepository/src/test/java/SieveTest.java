import java.util.function.Function;
import org.junit.Test;
import xyz.ldqc.buka.data.repository.core.engine.query.Conditional;
import xyz.ldqc.buka.data.repository.core.engine.query.ConditionalConstruct;
import xyz.ldqc.buka.data.repository.core.engine.query.SieveBuilder;

public class SieveTest {

  @Test
  public void sieveBuilderTest() {
    SieveBuilder.get()
        .set("n", "n", conditionalConstruct -> conditionalConstruct.eq("123").getConditional());
  }

}

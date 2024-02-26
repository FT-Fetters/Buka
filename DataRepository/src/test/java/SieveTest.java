import com.alibaba.fastjson2.JSON;
import java.util.function.Function;
import org.junit.Test;
import xyz.ldqc.buka.data.repository.core.engine.query.Conditional;
import xyz.ldqc.buka.data.repository.core.engine.query.ConditionalConstruct;
import xyz.ldqc.buka.data.repository.core.engine.query.Sieve;
import xyz.ldqc.buka.data.repository.core.engine.query.SieveBuilder;
import xyz.ldqc.buka.data.repository.core.engine.query.SieveJsonParser;

public class SieveTest {

    @Test
    public void sieveBuilderTest() {
        SieveBuilder.get()
            .set("n", "n",
                (Function<ConditionalConstruct, Conditional>) construct -> construct.eq("n")
                    .getConditional());
    }

    @Test
    public void sieveJsonParserTest() {
        String jsonStr = "{\n"
            + "                \"type\": \"and\",\n"
            + "                \"value\": [\n"
            + "                    {\n"
            + "                        \"type\": \"equal\",\n"
            + "                        \"value\": \"b\"\n"
            + "                    },\n"
            + "                    {\n"
            + "                        \"type\": \"and\",\n"
            + "                        \"value\": [\n"
            + "                            {\n"
            + "                                \"type\": \"less\",\n"
            + "                                \"value\": \"a\"\n"
            + "                            },\n"
            + "                            {\n"
            + "                                \"type\": \"greater\",\n"
            + "                                \"value\": \"0\"\n"
            + "                            }\n"
            + "                        ]\n"
            + "                    }\n"
            + "                ]\n"
            + "            }";
        SieveBuilder sieveBuilder = SieveBuilder.get();
        SieveJsonParser.parse(sieveBuilder, JSON.parseObject(jsonStr),"name");
        Sieve sieve = sieveBuilder.build();

        System.out.println("11");
    }

}

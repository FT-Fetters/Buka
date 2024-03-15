import com.alibaba.fastjson2.JSONObject;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import org.junit.Test;
import xyz.ldqc.buka.data.repository.core.engine.buffer.BadBucket;
import xyz.ldqc.buka.data.repository.core.engine.buffer.Box;
import xyz.ldqc.buka.data.repository.core.engine.buffer.BoxFactory;
import xyz.ldqc.buka.data.repository.core.engine.query.Conditional;
import xyz.ldqc.buka.data.repository.core.engine.query.ConditionalConstruct;
import xyz.ldqc.buka.data.repository.core.engine.query.Sieve;
import xyz.ldqc.buka.data.repository.core.engine.query.SieveBuilder;
import xyz.ldqc.buka.data.repository.core.engine.structure.DataTypeEnum;

public class BucketTest {

  Random random = new Random(System.currentTimeMillis());

  @Test
  public void testBadTest() {
    BadBucket badBucket = new BadBucket("testBucket");
    //language=JSON
    String json = "{\n"
        + "  \"name\": \"tom\",\n"
        + "  \"age\": 12\n"
        + "}";
    badBucket.put(json);
    json = "{\n"
        + "  \"name\": \"mike\",\n"
        + "  \"age\": 11\n"
        + "}";
    badBucket.put(json);
    json = "{\n"
        + "  \"name\": \"mike\",\n"
        + "  \"age\": 12\n"
        + "}";
    badBucket.put(json);
    System.out.println();
  }

  @Test
  public void testBadBucketInsertSpeed() {
    int t = 10;
    String[] fieldName = {"name", "age", "sex", "company", "location"};
    BadBucket badBucket = new BadBucket("bucketTest");
    JSONObject[] array = new JSONObject[t];
    for (int i = 0; i < t; i++) {
      JSONObject json = new JSONObject();
      for (int j = 0; j < 3; j++) {
        json.put(fieldName[random.nextInt(fieldName.length)], randString(64));
      }
      array[i] = json;
    }
    System.out.println("json finished");
    long time = System.currentTimeMillis();
    for (int i = 0; i < array.length; i++) {
      JSONObject json = array[i];
      badBucket.put(json);
//      json.clear();
      array[i] = null;
    }
    long endTime = System.currentTimeMillis();
    System.out.println("spend time: " + (endTime - time));
    boolean storage = badBucket.storage("F:\\workspace\\buka", null);
    System.out.println(storage);
  }

  private String randString(int len) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < len; i++) {
      if (random.nextBoolean()) {
        sb.append((char) ('a' + random.nextInt(26)));
      } else {
        sb.append((char) ('0' + random.nextInt(10)));
      }
    }
    return sb.toString();
  }

  @Test
  public void testFind() {
    BadBucket badBucket = new BadBucket("findTest");
    int n = 10;
    for (int i = 0; i < n; i++) {
      JSONObject j = new JSONObject();
      j.put("age", random.nextInt(50));
      j.put("name", randString(random.nextInt(10)));
      badBucket.put(j.toString());
    }
    Sieve sieve = SieveBuilder.get()
        .set("name", "name",
            (Function<ConditionalConstruct, Conditional>) construct -> construct.textLimit(9, 10).getConditional())
        .set("age", "age",
            (Function<ConditionalConstruct, Conditional>) construct -> construct.gt(0).getConditional())
        .build();
    List<String> result = badBucket.find(sieve);
    Box box = BoxFactory.get()
        .lattice("name", DataTypeEnum.STRING)
        .lattice("age", DataTypeEnum.NUMBER)
        .create("");
    box.putAll(result,false);
    result.forEach(s -> System.out.println(s));
  }

  @Test
  public void testLoad(){
    String path = "F:\\迅雷下载";
    String name = "findTest";
    BadBucket badBucket = new BadBucket(name);
    badBucket.load(path, name, null);
  }

  @Test
  public void testStorageAndLoad(){
    BadBucket badBucket = new BadBucket("findTest");
    int n = 10;
    for (int i = 0; i < n; i++) {
      JSONObject j = new JSONObject();
      j.put("age", random.nextInt(50));
      j.put("name", randString(random.nextInt(10)));
      badBucket.put(j.toString());
    }
    Sieve sieve = SieveBuilder.get()
        .set("name", "name",
            (Function<ConditionalConstruct, Conditional>) construct -> construct.textLimit(9, 10).getConditional())
        .set("age", "age",
            (Function<ConditionalConstruct, Conditional>) construct -> construct.gt(0).getConditional())
        .build();
    List<String> result = badBucket.find(sieve);
    Box box = BoxFactory.get()
        .lattice("name", DataTypeEnum.STRING)
        .lattice("age", DataTypeEnum.NUMBER)
        .create("");
    box.putAll(result,false);
    result.forEach(s -> System.out.println(s));
    System.out.println("-------------------------");
    badBucket.storage("F:\\迅雷下载",  null);
    badBucket.load("F:\\迅雷下载","findTest", null);
    result.clear();
    result = badBucket.find(sieve);
    result.forEach(s -> System.out.println(s));

  }
}

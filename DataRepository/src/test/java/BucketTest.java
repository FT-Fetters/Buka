import com.alibaba.fastjson2.JSONObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import xyz.ldqc.buka.data.repository.core.engine.buffer.BadBucket;

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
  public void testBadBucketInsertSpeed() throws InterruptedException {
    int t = 10;
    String[] fieldName = {"name", "age", "sex","company", "location"};
    BadBucket badBucket = new BadBucket("speedTest");
    List<JSONObject> dataList = new LinkedList<>();
    for (int i = 0; i < t; i++) {
      JSONObject json = new JSONObject();
      for (int j = 0; j < 3; j++) {
        json.put(fieldName[random.nextInt(fieldName.length)],randString(64));
      }
      dataList.add(json);
    }
    long time = System.currentTimeMillis();
    for (JSONObject json : dataList) {
      badBucket.put(json.toJSONString());
    }
    long endTime = System.currentTimeMillis();
    System.out.println("spend time: " + (endTime - time));
    boolean storage = badBucket.storage("F:\\迅雷下载", "bk");
    System.out.println(storage);
  }

  private String randString(int len){
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < len; i++) {
      if (random.nextBoolean()) {
        sb.append((char)('a' + random.nextInt(26)));
      }else {
        sb.append((char)('0' + random.nextInt(10)));
      }
    }
    return sb.toString();
  }
}

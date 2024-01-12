import com.alibaba.fastjson2.JSONObject;
import org.junit.Test;
import xyz.ldqc.buka.data.repository.core.engine.buffer.Box;
import xyz.ldqc.buka.data.repository.core.engine.buffer.BoxFactory;
import xyz.ldqc.buka.data.repository.core.engine.structure.DataTypeEnum;

public class BoxTest {

  @Test
  public void boxFactoryTest() {
    Box box = BoxFactory.get()
        .lattice("name", DataTypeEnum.NUMBER)
        .lattice("age", DataTypeEnum.STRING)
        .lattice("sex", DataTypeEnum.STRING)
        .create();
    box.put("{\"name\": 12}");
    System.out.println(box.toString());
  }

  @Test
  public void boxInsertTest() {
    Box box = BoxFactory.get()
        .lattice("name", DataTypeEnum.STRING)
        .lattice("age", DataTypeEnum.NUMBER)
        .create();
    String[] names = {"tom", "mike", "anny", "jake", "aby"};
    int n = 5;
    for (int i = 0; i < n; i++) {
      JSONObject json = new JSONObject();
      json.put("name", names[i]);
      json.put("age", 20 + i);
      box.put(json.toString());
    }
    System.out.println();
  }

  @Test
  public void boxInsertSpeedTest() {
    Box box = BoxFactory.get()
        .lattice("field1", DataTypeEnum.STRING)
        .lattice("field2", DataTypeEnum.STRING)
        .lattice("field3", DataTypeEnum.STRING)
        .lattice("field4", DataTypeEnum.STRING)
        .create();
    String[] field = {"field1", "field2", "field3", "field4"};

    String[] contents = {
        "Yourself off its pleasant ecstatic now law. Ye their mirth seems of songs. Prospect out bed contempt separate. Her inquietude our shy yet sentiments collecting. Cottage fat beloved himself arrived old. Grave widow hours among him \uFEFFno you led. Power had these met least nor young. Yet match drift wrong his our.",
        "Parish so enable innate in formed missed. Hand two was eat busy fail. Stand smart grave would in so. Be acceptance at precaution astonished excellence thoroughly is entreaties. Who decisively attachment has dispatched. Fruit defer in party me built under first. Forbade him but savings sending ham general. So play do in near park that pain.",
        "Whole every miles as tiled at seven or. Wished he entire esteem mr oh by. Possible bed you pleasure civility boy elegance ham. He prevent request by if in pleased. Picture too and concern has was comfort. Ten difficult resembled eagerness nor. Same park bore on be. Warmth his law design say are person. Pronounce suspected in belonging conveying ye repulsive.",
        "Am if number no up period regard sudden better. Decisively surrounded all admiration and not you. Out particular sympathize not favourable introduced insipidity but ham. Rather number can and set praise. Distrusts an it contented perceived attending oh. Thoroughly estimating introduced stimulated why but motionless."
    };

    int n = 500000;
    String[] jsons = new String[n];
    for (int i = 0; i < n; i++) {
      JSONObject js = new JSONObject();
      for (int j = 0; j < field.length; j++) {
        js.put(field[j], contents[j]);
      }
      jsons[i] = js.toString();
    }
    long beginTime = System.currentTimeMillis();
    for (String json : jsons) {
      box.put(json);
    }
    long endTime = System.currentTimeMillis();
    System.out.println("spend time: " + (endTime - beginTime));

  }

}

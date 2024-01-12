import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import org.junit.Test;
import xyz.ldqc.buka.data.repository.core.engine.structure.DataLink;
import xyz.ldqc.buka.data.repository.core.engine.structure.support.SkipListDataLink;

public class DataLinkTest {

  @Test
  public void testSkipListDataLink() {
    SkipListDataLink<String> dataLink = new SkipListDataLink<>(String.class);
    dataLink.addDataSection(1, "test");
    dataLink.addDataSection(2, "test");
    dataLink.addDataSection(1, "test1");


  }

  @Test
  public void testSkipListDataLinkInsertSpeed() {
    SkipListDataLink<String> dataLink = new SkipListDataLink<>(String.class);

    String[] sectionNames = {"field1", "field2", "field3", "field4"};

    String[] contents = {
        "Yourself off its pleasant ecstatic now law. Ye their mirth seems of songs. Prospect out bed contempt separate. Her inquietude our shy yet sentiments collecting. Cottage fat beloved himself arrived old. Grave widow hours among him \uFEFFno you led. Power had these met least nor young. Yet match drift wrong his our.",
        "Parish so enable innate in formed missed. Hand two was eat busy fail. Stand smart grave would in so. Be acceptance at precaution astonished excellence thoroughly is entreaties. Who decisively attachment has dispatched. Fruit defer in party me built under first. Forbade him but savings sending ham general. So play do in near park that pain.",
        "Whole every miles as tiled at seven or. Wished he entire esteem mr oh by. Possible bed you pleasure civility boy elegance ham. He prevent request by if in pleased. Picture too and concern has was comfort. Ten difficult resembled eagerness nor. Same park bore on be. Warmth his law design say are person. Pronounce suspected in belonging conveying ye repulsive.",
        "Am if number no up period regard sudden better. Decisively surrounded all admiration and not you. Out particular sympathize not favourable introduced insipidity but ham. Rather number can and set praise. Distrusts an it contented perceived attending oh. Thoroughly estimating introduced stimulated why but motionless."
    };

    int n = 10000000;
    long current = System.currentTimeMillis();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < sectionNames.length; j++) {
        dataLink.addDataSection(i, sectionNames[j]);
      }
    }
    long finished = System.currentTimeMillis();
    System.out.println("spend time: " + (finished - current));
    List<String> field1SectionData = dataLink.getSectionData("field1");
    System.out.println(field1SectionData.size());
  }

  @Test
  public void superClassTest(){
    DataLink<String> dataLink = new SkipListDataLink<String>(null);
    Type superclass = dataLink.getClass().getGenericSuperclass();
    if (superclass instanceof Class) {
      throw new RuntimeException("Missing type parameter.");
    }
    Type type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
    System.out.println();
  }

}

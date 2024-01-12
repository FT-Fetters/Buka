import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import xyz.ldqc.buka.util.ClassUtil;

public class TestUtil {

  @Test
  public void testClassUtil() {
    List<String> list = new ArrayList<String>();
    Type superclass = list.getClass().getGenericSuperclass();
    if (superclass instanceof Class) {
      throw new RuntimeException("Missing type parameter.");
    }
    Type type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
    System.out.println();
  }

}

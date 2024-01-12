import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import xyz.ldqc.buka.data.repository.config.DataRepositoryConfig;

public class OtherTest {

  @Test
  public void testJarPath(){
    DataRepositoryConfig repositoryConfig = new DataRepositoryConfig();
    System.out.println(repositoryConfig.getDefaultStorageLocation());

  }

  @Test
  public void testGen(){
    List<?> list = new ArrayList<>();


  }

}

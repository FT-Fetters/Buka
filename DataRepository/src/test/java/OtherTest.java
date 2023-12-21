import org.junit.Test;
import xyz.ldqc.buka.data.repository.config.DataRepositoryConfig;

public class OtherTest {

  @Test
  public void testJarPath(){
    DataRepositoryConfig repositoryConfig = new DataRepositoryConfig();
    System.out.println(repositoryConfig.getDefaultStorageLocation());

  }

}

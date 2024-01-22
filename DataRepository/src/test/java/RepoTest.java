import java.util.List;
import org.junit.Test;
import xyz.ldqc.buka.data.repository.core.engine.buffer.RepositoryBuffer;

public class RepoTest {


  @Test
  public void repositoryEasyTest(){
    // test pass
    String repoPath = "F:\\workspace\\buka";
    RepositoryBuffer repositoryBuffer = new RepositoryBuffer(repoPath);
    List<String> list = repositoryBuffer.listBucketName();
    list.forEach(b -> System.out.println(b));
  }


}

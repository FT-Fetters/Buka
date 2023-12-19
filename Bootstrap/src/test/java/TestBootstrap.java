import java.util.concurrent.locks.LockSupport;
import org.junit.Test;
import xyz.ldqc.buka.BukaBootstrap;

public class TestBootstrap {

  @Test
  public void testBootstrap(){
    BukaBootstrap bukaBootstrap = BukaBootstrap.run();
    LockSupport.park();
  }

}

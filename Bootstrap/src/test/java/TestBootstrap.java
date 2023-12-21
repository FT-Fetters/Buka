import java.util.concurrent.locks.LockSupport;
import org.junit.Test;
import xyz.ldqc.buka.BukaApplication;
import xyz.ldqc.buka.BukaBootstrap;

public class TestBootstrap {

  @Test
  public void testBootstrap(){
    BukaApplication bukaApplication = BukaBootstrap.run();
    LockSupport.park();
  }

}

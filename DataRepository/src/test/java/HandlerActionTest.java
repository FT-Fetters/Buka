import org.junit.Test;
import xyz.ldqc.buka.data.repository.core.action.support.CreateRepoAction;
import xyz.ldqc.buka.data.repository.core.handler.HandlerFactory;

public class HandlerActionTest {

  @Test
  public void testHandlerFactory(){
    HandlerFactory handlerFactory = HandlerFactory.getInstance(null);
    CreateRepoAction createRepoAction = new CreateRepoAction("123");
    handlerFactory.handler(createRepoAction);
  }

}

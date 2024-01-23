package xyz.ldqc.buka.data.repository.exception;

/**
 * @author Fetters
 */
public class ActionHandlerNotFoundException extends RuntimeException{

  public ActionHandlerNotFoundException(String msg){
    super(msg);
  }

}

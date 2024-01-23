package xyz.ldqc.buka.data.repository.exception;

/**
 * @author LENOVO
 */
public class RepositoryNotFoundException extends RuntimeException {

  public RepositoryNotFoundException(String msg){
    super(msg);
  }

}

package xyz.ldqc.buka.receiver.entity;

/**
 * @author Fetters
 */
public class WebResponseEntity {

  private String status;

  private Object body;

  public WebResponseEntity(String status, Object body) {
    this.status = status;
    this.body = body;
  }

  public Object getBody() {
    return body;
  }

  public void setBody(Object body) {
    this.body = body;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public static WebResponseEntity fail(Object body){
    return new WebResponseEntity("fail", body);
  }

}

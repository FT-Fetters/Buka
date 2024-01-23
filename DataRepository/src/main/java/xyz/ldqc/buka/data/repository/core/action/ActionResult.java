package xyz.ldqc.buka.data.repository.core.action;

/**
 * @author Fetters
 */
public class ActionResult {

  private String msg;

  private Object out;

  public ActionResult(String msg){
    this.msg = msg;
  }

  public ActionResult(String msg, Object out) {
    this.msg = msg;
    this.out = out;
  }

  public String getMsg() {
    return msg;
  }

  public Object getOut() {
    return out;
  }
}

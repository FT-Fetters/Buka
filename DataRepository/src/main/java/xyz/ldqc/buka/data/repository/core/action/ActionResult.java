package xyz.ldqc.buka.data.repository.core.action;

/**
 * Action处理返回结果
 * @author Fetters
 */
public class ActionResult {

  /**
   * 结果消息
   */
  private final String msg;

  /**
   * 结果返回数据
   */
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

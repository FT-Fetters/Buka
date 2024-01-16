package xyz.ldqc.buka.data.repository.core.engine.buffer;

import java.util.List;
import java.util.Map;
import xyz.ldqc.buka.data.repository.core.engine.query.Sieve;
import xyz.ldqc.buka.data.repository.core.engine.structure.DataLink;

/**
 * @author Fetters
 */
public abstract class AbstractBucket {

  protected String name;

  protected Map<String, DataLink<?>> fieldMap;

  public String getName() {
    return name;
  }

  /**
   * 存储数据
   *
   * @param path 要存储的路径
   * @param pass 加密
   * @return boolean
   */
  public abstract boolean storage(String path, String pass);

  /**
   * 查找数据
   *
   * @param sieve 数据过滤器
   * @return 数据集
   */
  public abstract List<String> find(Sieve sieve);

  /**
   * 加载磁盘数据
   *
   * @param path 路径
   * @param pk   密钥
   * @return 加载成功数据的数据量
   */
  public abstract int load(String path, String name, String pk);
}

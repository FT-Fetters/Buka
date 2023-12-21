package xyz.ldqc.buka.receiver.aware;

import xyz.ldqc.buka.data.repository.DataRepositoryApplication;

/**
 * @author Fetters
 */
public interface DataRepositoryAware {

  /**
   * 设置数据仓库属性
   * @param dataRepositoryApplication 数据仓库
   */
  void setDataRepositoryApplication(DataRepositoryApplication dataRepositoryApplication);

}

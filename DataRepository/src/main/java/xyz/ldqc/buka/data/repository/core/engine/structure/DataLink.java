package xyz.ldqc.buka.data.repository.core.engine.structure;

import java.util.List;
import java.util.Map.Entry;
import xyz.ldqc.tightcall.serializer.support.KryoSerializer;

/**
 * @author Fetters
 */
public interface DataLink<T> {

  /**
   * 添加数据段
   * @param dataSourceId 源数据所在id
   * @param section 数据段内容
   * @return id
   */
  long addDataSection(long dataSourceId ,Object section);

  /**
   * 获取数据段
   * @param section 数据段名
   * @return 去重后的数据列表
   */
  List<Long> getSectionDataSourceIdList(T section);

  /**
   * 获取数据的类型
   * @return Class
   */
  Class<?> getDataType();

  /**
   * 获取所有数据
   * @return list
   */
  List<Entry<T, List<Long>>> sectionList();

  /**
   * 通过id获取数据
   * @param id id
   * @return T
   */
  T getSectionById(long id);

  /**
   * 转化为字节数据
   * @return byte[]
   */
  byte[] toLinkBytes();

  /**
   * 字节数据转对象
   *
   * @param bytes 字节数据
   * @return DataLink
   */
  static DataLink<?> bytes2DataLink(byte[] bytes) {
    Object obj = KryoSerializer.serializer().deserialize(bytes);
    return (DataLink<?>) obj;
  }

}

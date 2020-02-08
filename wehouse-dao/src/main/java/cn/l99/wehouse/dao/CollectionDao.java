package cn.l99.wehouse.dao;

import cn.l99.wehouse.pojo.Collection;
import org.springframework.stereotype.Repository;

/**
 * 房源收藏持久层
 *
 * @author L99
 */
@Repository
public interface CollectionDao {


    boolean insertCollection(Collection collection);

}

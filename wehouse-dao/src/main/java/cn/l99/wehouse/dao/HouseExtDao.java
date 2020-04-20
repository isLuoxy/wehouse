package cn.l99.wehouse.dao;

import cn.l99.wehouse.pojo.HouseExt;
import org.springframework.stereotype.Repository;

/**
 * 房屋扩展信息持久层
 *
 * @author L99
 */
@Repository
public interface HouseExtDao {

    boolean insertHouseExt(HouseExt houseExt);
}

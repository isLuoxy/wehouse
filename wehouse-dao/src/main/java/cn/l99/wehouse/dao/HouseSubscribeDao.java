package cn.l99.wehouse.dao;

import cn.l99.wehouse.pojo.HouseSubscribe;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 房源预定持久层
 *
 * @author L99
 */
@Repository
public interface HouseSubscribeDao {

    /**
     * 新增预定记录
     *
     * @param houseSubscribe
     */
    void insertHouseSubscribe(HouseSubscribe houseSubscribe);

    /**
     * 修改预定记录
     */
    boolean updateHouseSubscribe(HouseSubscribe houseSubscribe);

    /**
     * 查看预定记录
     *
     * @param userId
     * @return
     */
    List<HouseSubscribe> getHouseSubscribeByUserId(@Param("userId") String userId, @Param("pageStart") int pageStart, @Param("pageSize") int pageSize);

}

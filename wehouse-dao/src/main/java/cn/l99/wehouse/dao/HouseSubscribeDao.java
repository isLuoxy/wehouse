package cn.l99.wehouse.dao;

import cn.l99.wehouse.pojo.HouseAndSubscribeAndUser;
import cn.l99.wehouse.pojo.HouseSubscribe;
import cn.l99.wehouse.pojo.vo.HouseSubscribeVo;
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
    List<HouseAndSubscribeAndUser> getHouseAndSubscribeAndUserByUserId(@Param("userId") String userId, @Param("pageStart") int pageStart, @Param("pageSize") int pageSize);


    /**
     * 通过房东id查找预定的房源列表，按照预定时间倒序
     *
     * @return
     */
    List<HouseAndSubscribeAndUser> getHouseAndSubscribeAndUserByOwnerId(@Param("ownerId") String ownerId, @Param("pageStart") int pageStart, @Param("pageSize") int pageSize);

    /**
     * 根据条件查找预定房源，按照预定时间倒序
     *
     * @param houseSubscribeVo
     * @param pageStart
     * @param pageSize
     * @return
     */
    List<HouseAndSubscribeAndUser> searchHouseAndSubscribeAndUser(HouseSubscribeVo houseSubscribeVo, @Param("pageStart") int pageStart, @Param("pageSize") int pageSize);
}

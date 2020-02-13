package cn.l99.wehouse.dao;

import cn.l99.wehouse.pojo.AHouse;
import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.utils.condition.HouseCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 房屋信息持久层
 */
@Repository
public interface HouseDao {


    /**
     * 根据城市和房屋条件筛选房屋
     *
     * @param cityPyName
     * @param houseCondition
     * @return
     */
    List<House> getHouseByCityPyNameAndCondition(@Param("cityName") String cityPyName, @Param("houseCondition") HouseCondition houseCondition);

    /**
     * 获取房源详情信息
     *
     * @param houseId
     * @return
     */
    AHouse getAHouseByHouseId(String houseId);
}

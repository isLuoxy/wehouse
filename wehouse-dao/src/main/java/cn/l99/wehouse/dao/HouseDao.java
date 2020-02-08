package cn.l99.wehouse.dao;

import cn.l99.wehouse.pojo.AHouse;
import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.HouseExt;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 房屋信息持久层
 */
@Repository
public interface HouseDao {


    List<House> getHouseByCityPyName(String cityPyName);

    AHouse getAHouseByHouseId(String houseId);
}

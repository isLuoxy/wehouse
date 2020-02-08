package cn.l99.wehouse.test.dao;

import cn.l99.wehouse.dao.CityDao;
import cn.l99.wehouse.dao.HouseDao;
import cn.l99.wehouse.pojo.AHouse;
import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.dto.CityDto;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

@Slf4j
public class HouseDaoTest extends AbstractTest {
    @Test
    public void getAHouseByHouseId() {
        HouseDao houseDao = session.getMapper(HouseDao.class);
        AHouse aHouseByHouseId = houseDao.getAHouseByHouseId("198198");
        log.info("{}", JSONObject.toJSONString(aHouseByHouseId));
    }

    @Test
    public void getHouseDtoByCityName() {
        HouseDao houseDao = session.getMapper(HouseDao.class);
        List<House> sz = houseDao.getHouseByCityPyName("sz");
        log.info("{}", JSONObject.toJSONString(sz));

    }
}

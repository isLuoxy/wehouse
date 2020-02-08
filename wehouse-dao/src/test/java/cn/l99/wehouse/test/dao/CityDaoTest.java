package cn.l99.wehouse.test.dao;

import cn.l99.wehouse.dao.CityDao;
import cn.l99.wehouse.pojo.dto.CityDto;
import cn.l99.wehouse.pojo.dto.SubwayLineDto;
import cn.l99.wehouse.pojo.dto.SubwayStationDto;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

@Slf4j
public class CityDaoTest extends AbstractTest {

    @Test
    public void getAllCitys() {
        CityDao cityDao = session.getMapper(CityDao.class);
        List<CityDto> allCitys = cityDao.getAllCitys();
        log.info("{}", JSONObject.toJSONString(allCitys));
    }

    @Test
    public void getSubwayLinesByCityId(){
        CityDao cityDao = session.getMapper(CityDao.class);
        List<SubwayLineDto> subwayLinesByCityId = cityDao.getSubwayLinesByCityId("4");
        log.info("{}", JSONObject.toJSONString(subwayLinesByCityId));
    }

    @Test
    public void getSubwayStationByLineId(){
        CityDao cityDao = session.getMapper(CityDao.class);
        List<SubwayStationDto> subwayStationByLineId = cityDao.getSubwayStationByLineId("112");
        log.info("{}",JSONObject.toJSONString(subwayStationByLineId));
    }
}

package cn.l99.wehouse.dao;

import cn.l99.wehouse.pojo.dto.CityDto;
import cn.l99.wehouse.pojo.dto.SubwayLineDto;
import cn.l99.wehouse.pojo.dto.SubwayStationDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 城市相关持久层操作
 *
 * @author L99
 */
@Repository
public interface CityDao {


    List<CityDto> getAllCitys();

    List<SubwayLineDto> getSubwayLinesByCityId(String cityId);

    List<SubwayStationDto> getSubwayStationByLineId(String lineId);
}

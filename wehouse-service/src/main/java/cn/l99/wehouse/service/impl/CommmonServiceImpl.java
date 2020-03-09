package cn.l99.wehouse.service.impl;

import cn.l99.wehouse.dao.CityDao;
import cn.l99.wehouse.dao.HouseAreaDao;
import cn.l99.wehouse.map.utils.DistrictUtil;
import cn.l99.wehouse.pojo.HouseArea;
import cn.l99.wehouse.pojo.dto.CityDto;
import cn.l99.wehouse.pojo.dto.RegionDto;
import cn.l99.wehouse.pojo.dto.SubwayLineDto;
import cn.l99.wehouse.pojo.dto.SubwayStationDto;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.redis.RedisUtils;
import cn.l99.wehouse.service.ICommonService;
import cn.l99.wehouse.utils.SubwayUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 通用服务层接口实现类
 *
 * @author L99
 */
@Service(version = "${wehouse.service.version}")
@Component
public class CommmonServiceImpl implements ICommonService {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    CityDao cityDao;

    @Autowired
    HouseAreaDao houseAreaDao;

    @Override
    public CommonResult getCitys() {
        String citys = (String) redisUtils.get("Citys");
        List<CityDto> allCitys;
        if (StringUtils.isEmpty(citys)) {
            allCitys = cityDao.getAllCitys();
            // 放入 redis ,key 为城市拼音，value 为城市中文名称
            String citysJsonString = JSONArray.toJSONString(allCitys);
            redisUtils.set("Citys", citysJsonString);
            Map<String, Object> cityMapTemp = allCitys.stream().collect(Collectors.toMap(CityDto::getCityPyName, CityDto::getCityCnName));
            redisUtils.pipelineSetNX(cityMapTemp);
        } else {
            allCitys = JSON.parseArray(citys, CityDto.class);
        }
        return CommonResult.success(allCitys);
    }

    /**
     * 获取特定城市下的行政区域
     *
     * @param keyword 城市名
     * @return
     */
    @Override
    public CommonResult getResions(String keyword) {
        // 从缓存中获取
        Map<Object, Object> regionMap = redisUtils.hmget(keyword);
        if (regionMap.isEmpty()) {
            // 读取数据
            // TODO: 从数据库中读取
            // Map<String, Object> regionMapTmp = DistrictUtil.getRegionName(keyword);
            List<HouseArea> regionByCityName = houseAreaDao.getRegionByCityName(keyword);
            Map<String, Object> regionMapTmp = regionByCityName.stream().collect(Collectors.toMap(HouseArea::getAdcode, HouseArea::getName));
            // redis 中 hash 存储，外部 key 为城市 ，内部 key为区域 id，value 为 Map <String,String>
            redisUtils.hmset(keyword, regionMapTmp);
            redisUtils.pipelineSetNX(regionMapTmp);
            String jsonString = JSONObject.toJSONString(regionMapTmp);
            regionMap = JSONObject.parseObject(jsonString, Map.class);
        }
        List<RegionDto> result = regionMap.entrySet().stream().map(e -> {
            RegionDto regionDto = new RegionDto();
            regionDto.setRegionId((String) e.getKey());
            regionDto.setRegionCnName((String) e.getValue());
            return regionDto;
        }).collect(Collectors.toList());
        return CommonResult.success(result);
    }

    @Override
    public CommonResult getSubwayline(String cityId) {
        List<SubwayLineDto> subwayLinesByCityId = cityDao.getSubwayLinesByCityId(cityId);
        Set<SubwayLineDto> result = new HashSet<>();
        subwayLinesByCityId.forEach(subwayLineDto -> {
            String lineCnName = subwayLineDto.getLineCnName();
            subwayLineDto.setLineCnName(SubwayUtils.SubwayNameFormat(lineCnName));
            result.add(subwayLineDto);
        });

        return CommonResult.success(result);
    }

    @Override
    public CommonResult getSubwayStation(String subwaylineId) {
        List<SubwayStationDto> subwayStationByLineId = cityDao.getSubwayStationByLineId(subwaylineId);
        return CommonResult.success(subwayStationByLineId);
    }
}

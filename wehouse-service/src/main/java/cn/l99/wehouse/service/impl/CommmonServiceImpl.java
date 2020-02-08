package cn.l99.wehouse.service.impl;

import cn.l99.wehouse.dao.CityDao;
import cn.l99.wehouse.map.utils.DistrictUtil;
import cn.l99.wehouse.pojo.dto.CityDto;
import cn.l99.wehouse.pojo.dto.RegionDto;
import cn.l99.wehouse.pojo.dto.SubwayLineDto;
import cn.l99.wehouse.pojo.dto.SubwayStationDto;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.service.ICommonService;
import cn.l99.wehouse.utils.SubwayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 通用服务层接口实现类
 *
 * @author L99
 */
@Service(version = "1.0")
@Component
public class CommmonServiceImpl implements ICommonService {

    @Autowired
    CityDao cityDao;

    @Override
    public CommonResult getCitys() {
        List<CityDto> allCitys = cityDao.getAllCitys();
        return CommonResult.success(allCitys);
    }

    /**
     * 获取特定城市下的行政区域
     *
     * @param keyword 省份名、城市名、区域名
     * @return
     */
    @Override
    public CommonResult getResions(String keyword) {
        List<String> regionNameList = DistrictUtil.getRegionName(keyword);
        List<RegionDto> result = new ArrayList<>(regionNameList.size());
        regionNameList.forEach(regionCnName -> {
            RegionDto regionDto = new RegionDto();
            regionDto.setRegionCnName(regionCnName);
            result.add(regionDto);
        });
        return CommonResult.success(result);
    }

    @Override
    public CommonResult getSubwayline(String city) {
        List<SubwayLineDto> subwayLinesByCityId = cityDao.getSubwayLinesByCityId(city);
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

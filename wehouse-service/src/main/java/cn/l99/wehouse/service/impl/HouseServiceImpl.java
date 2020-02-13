package cn.l99.wehouse.service.impl;


import cn.l99.wehouse.dao.HouseDao;
import cn.l99.wehouse.pojo.AHouse;
import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.baseEnum.ErrorCode;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.redis.RedisUtils;
import cn.l99.wehouse.service.IHouseService;
import cn.l99.wehouse.utils.HouseUtils;
import cn.l99.wehouse.utils.condition.HouseCondition;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 房屋服务层接口实现类
 *
 * @author L99
 */
@Service(version = "${wehouse.service.version}")
@Component
@Slf4j
public class HouseServiceImpl implements IHouseService {

    @Autowired
    HouseDao houseDao;

    @Autowired
    RedisUtils redisUtils;

    @Override
    public CommonResult getHouseByCityName(String cityPyName, String condition) {
        HouseCondition houseCondition = HouseUtils.acqConditions(condition);
        if (houseCondition == null) {
            log.info("获取房源时没有条件");
        }
        // 如果处理条件中 regionName 不为空，那么此时的值为 regionID，需要改为 regionName
        if (!StringUtils.isEmpty(houseCondition.getRegionCnName())) {
            String regionCnName = (String) redisUtils.get(houseCondition.getRegionCnName());
            houseCondition.setRegionCnName(regionCnName);
        }
        List<House> houseByCityName = houseDao.getHouseByCityPyNameAndCondition(cityPyName, houseCondition);
        return CommonResult.success(houseByCityName);
    }

    @Override
    public CommonResult getAHouseByHouseId(String houseId) {
        AHouse aHouseByHouseId = houseDao.getAHouseByHouseId(houseId);
        if (aHouseByHouseId == null) {
            return CommonResult.failure(ErrorCode.HOUSE_NOT_EXIST);
        }
        return CommonResult.success(aHouseByHouseId);
    }
}

package cn.l99.wehouse.service.impl;


import cn.l99.wehouse.dao.HouseDao;
import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.service.IHouseService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 房屋服务层接口实现类
 *
 * @author L99
 */
@Service(version = "1.0")
@Component
public class HouseServiceImpl implements IHouseService {

    @Autowired
    HouseDao houseDao;

    @Override
    public CommonResult getHouseByCityName(String cityPyName) {
        List<House> houseByCityName = houseDao.getHouseByCityPyName(cityPyName);
        return CommonResult.success(houseByCityName);
    }
}

package cn.l99.wehouse.service.impl;

import cn.l99.wehouse.dao.HouseSubscribeExtDao;
import cn.l99.wehouse.pojo.HouseSubscribeExt;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.service.IHouseSubscribeExtService;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service(version = "${wehouse.service.version}")
@Slf4j
@Component
public class HouseSubscribeExtServiceImpl implements IHouseSubscribeExtService {

    @Autowired
    HouseSubscribeExtDao houseSubscribeExtDao;

    @Override
    public CommonResult addHouseSubscribeExt(HouseSubscribeExt houseSubscribeExt) {
        houseSubscribeExtDao.insertHouseSubscribeExt(houseSubscribeExt);
        return CommonResult.success();
    }
}

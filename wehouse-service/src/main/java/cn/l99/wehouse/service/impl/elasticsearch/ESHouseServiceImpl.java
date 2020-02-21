package cn.l99.wehouse.service.impl.elasticsearch;

import cn.l99.wehouse.elasticsearch.ESHouseRepository;
import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.service.elasticsearch.ESIHouseService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service(version = "${wehouse.service.version}")
public class ESHouseServiceImpl implements ESIHouseService {

    @Autowired
    private ESHouseRepository ESHouseRepository;

    @Override
    public CommonResult addHouse(House house) {
        ESHouseRepository.save(house);
        return CommonResult.success();
    }
}

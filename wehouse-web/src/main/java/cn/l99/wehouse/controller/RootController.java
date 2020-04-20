package cn.l99.wehouse.controller;

import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.pojo.vo.HouseAreaVo;
import cn.l99.wehouse.service.IHouseAreaService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员操作
 */
@RestController
public class RootController {

    @Reference(version = "${wehouse.service.version}")
    IHouseAreaService houseAreaService;

    /**
     * 新增房源区域
     *
     * @return
     */
    @PostMapping("/root/house/area")
    public Object addHouseArea(@RequestBody HouseAreaVo houseAreaVo) {
        CommonResult result = houseAreaService.insertCity(houseAreaVo.getName(), houseAreaVo.getEnName());
        return result;
    }
}

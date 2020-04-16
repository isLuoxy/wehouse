package cn.l99.wehouse.controller;

import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.pojo.vo.HouseVo;
import cn.l99.wehouse.service.IHouseService;
import cn.l99.wehouse.service.elasticsearch.ESIHouseService;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 房屋控制层
 *
 * @author L99
 */
@RestController
@Slf4j
public class HouseController {


    @Reference(version = "${wehouse.service.version}")
    IHouseService houseService;

    @Reference(version = "${wehouse.service.version}")
    ESIHouseService esHouseService;

    @GetMapping("/zufang/{city}/f/{id}")
    public Object getAHouse(@PathVariable("city") String cityPyName, @PathVariable("id") String houseId) {
        // 这里的城市拼音暂不使用

        CommonResult aHouseByHouseId = houseService.getAHouseByHouseId(houseId);
        return aHouseByHouseId;
    }

    @GetMapping({"/zufang/{city}/p/{param}/_search/{search}", "/zufang/{city}", "/zufang/{city}/_search/{search}", "/zufang/{city}/p/{param}"})
    public Object getHouseByParam(@PathVariable("city") String cityPyName,
                                  @PathVariable(value = "param", required = false) String param,
                                  @PathVariable(value = "search", required = false) String search) {
        CommonResult house;
        // 如果不为空，可知有关键词查询
        log.info("{}", search);
        house = esHouseService.findHouseByCondition(cityPyName, param, search);

        // TODO: 暂不使用数据库的索引
        //house = houseService.getHouseByCityName(cityPyName, param);
        return house;
    }

    /**
     * 新增房源
     *
     * @param houseVo 通用房源请求对象
     * @return
     */
    @PostMapping("/zufang/house")
    public Object addHouse(@RequestBody HouseVo houseVo) {
        houseService.addHouse(houseVo);
        return null;
    }
}

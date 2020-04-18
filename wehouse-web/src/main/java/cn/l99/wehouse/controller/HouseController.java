package cn.l99.wehouse.controller;

import cn.l99.wehouse.common.LoginUtils;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.pojo.vo.HouseVo;
import cn.l99.wehouse.service.IHouseService;
import cn.l99.wehouse.service.elasticsearch.ESIHouseService;
import cn.l99.wehouse.service.redis.IRedisService;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    IRedisService redisService;


    @GetMapping("/zufang/{city}/f/{id}")
    public Object getAHouse(@PathVariable("city") String cityPyName, @PathVariable("id") String houseId,HttpServletRequest request,HttpServletResponse response) {
        // 这里的城市拼音暂不使用
        String userId = LoginUtils.hasLoginAndReturnString(request, response, redisService);

        CommonResult aHouseByHouseId = houseService.getAHouseByHouseId(houseId);
        return aHouseByHouseId;
    }

    @GetMapping({"/zufang/{city}/p/{param}/_search/{search}", "/zufang/{city}", "/zufang/{city}/_search/{search}", "/zufang/{city}/p/{param}"})
    public Object getHouseByParam(@PathVariable("city") String cityPyName,
                                  @PathVariable(value = "param", required = false) String param,
                                  @PathVariable(value = "search", required = false) String search,
                                  HttpServletRequest request, HttpServletResponse response) {
        // 如果不为空，可知有关键词查询
        log.info("{}", search);
        String userId = LoginUtils.hasLoginAndReturnString(request, response, redisService);

        CommonResult house = houseService.findHouseByCondition(cityPyName, param, search, userId);

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

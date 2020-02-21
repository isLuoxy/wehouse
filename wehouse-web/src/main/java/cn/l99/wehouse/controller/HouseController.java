package cn.l99.wehouse.controller;

import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.service.IHouseService;
import cn.l99.wehouse.service.elasticsearch.ESIHouseService;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping({"/zufang/{city}/p/{param}", "/zufang/{city}", "/zufang/{city}/p"})
    public Object getHouseByParam(@PathVariable("city") String cityPyName, @PathVariable(value = "param", required = false) String param, HttpServletRequest request) {
        String search = request.getParameter("_search");
        if (StringUtils.isEmpty(search)) {
            // 如果搜索框有参数则使用 es 进行查询

        }
        CommonResult houseByCityName = houseService.getHouseByCityName(cityPyName, param);
        return houseByCityName;
    }
}

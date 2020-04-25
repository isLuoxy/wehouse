package cn.l99.wehouse.controller;

import cn.l99.wehouse.service.ICommonService;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通用类控制器
 *
 * @author L99
 */
@RestController
@Slf4j
public class CommonController {

    @Reference(version = "${wehouse.service.version}")
    ICommonService commonService;

    @GetMapping("/citys")
    public Object getCitys() {
        return commonService.getCitys();
    }

    @GetMapping("/region/{city}")
    public Object getRegions(@PathVariable String city) {
        log.info("{}", city);
//        try {
//            city = URLDecoder.decode(city, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            return CommonResult.failure(ErrorCode.CODING_ERROR);
//        }
        return commonService.getResions(city);
    }

    @GetMapping("/subwayline/{cityId}")
    public Object getCity(@PathVariable String cityId) {
        return commonService.getSubwayline(cityId);
    }

    @GetMapping("/subwaystation/{id}")
    public Object getSubwaystation(@PathVariable String id) {
        return commonService.getSubwayStation(id);
    }
}

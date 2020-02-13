package cn.l99.wehouse.controller;

import cn.l99.wehouse.pojo.baseEnum.ErrorCode;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.service.ICommonService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 通用类控制器
 *
 * @author L99
 */
@RestController
public class CommonController {

    @Reference(version = "${wehouse.service.version}")
    ICommonService commonService;

    @GetMapping("/citys")
    public Object getCitys() {
        return commonService.getCitys();
    }

    @GetMapping("/region/{city}")
    public Object getRegions(@PathVariable String city) {
        try {
            city = URLDecoder.decode(city, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return CommonResult.failure(ErrorCode.CODING_ERROR);
        }
        return commonService.getResions(city);
    }

    @GetMapping("/subwayline/{city}")
    public Object getCity(@PathVariable String city) {
        return commonService.getSubwayline(city);
    }

    @GetMapping("/subwaystation/{id}")
    public Object getSubwaystation(@PathVariable String id) {
        return commonService.getSubwayStation(id);
    }
}

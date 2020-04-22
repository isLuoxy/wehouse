package cn.l99.wehouse.controller;

import cn.l99.wehouse.common.LoginUtils;
import cn.l99.wehouse.common.LogoutUtils;
import cn.l99.wehouse.pojo.HouseSubscribeExt;
import cn.l99.wehouse.pojo.Page;
import cn.l99.wehouse.pojo.baseEnum.HouseSubscribeStatus;
import cn.l99.wehouse.pojo.baseEnum.OperatorType;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.pojo.vo.HouseSubscribeVo;
import cn.l99.wehouse.pojo.vo.OwnerVo;
import cn.l99.wehouse.service.IHouseSubscribeService;
import cn.l99.wehouse.service.IOwnerService;
import cn.l99.wehouse.service.redis.IRedisService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 房东控制层
 *
 * @author L99
 */
@RestController
public class OwnerController {


    @Reference(version = "${wehouse.service.version}", timeout = 50000)
    IOwnerService ownerService;

    @Reference(version = "${wehouse.service.version}")
    IRedisService redisService;

    @Reference(version = "${wehouse.service.version}")
    IHouseSubscribeService houseSubscribeService;

    @PostMapping("/owner/login")
    public Object login(@RequestBody OwnerVo ownerVo) {
        return ownerService.login(ownerVo);
    }

    @PostMapping("/owner/register")
    public Object register(@RequestBody OwnerVo ownerVo) {
        return ownerService.register(ownerVo);
    }

    @GetMapping("/owner/logout")
    public Object logout(HttpServletRequest request) {
        LogoutUtils.clearUserStatus(request, redisService);
        return CommonResult.success();
    }

    @PostMapping("/check/ownerphone")
    public Object checkOwnerPhone(@RequestBody OwnerVo ownerVo) {
        return ownerService.checkOwnerPhone(ownerVo.getOwnerPhone());
    }

    @PostMapping("/check/ownername")
    public Object checkOwnerName(@RequestBody OwnerVo ownerVo) {
        return ownerService.checkOwnerName(ownerVo.getOwnerName());
    }


    // === 房源预约模块 ===
    @PostMapping("/owner/house/subscribe/search")
    public Object searchHouseSubscribe(HttpServletRequest request, @RequestBody HouseSubscribeVo houseSubscribeVo) {
        //TODO
        return null;
    }

    @PutMapping("/owner/house/subscribe/success/{id}")
    public Object FinishHouseSubscribe(@PathVariable("id") String id) {
        HouseSubscribeVo houseSubscribeVo = new HouseSubscribeVo();
        houseSubscribeVo.setId(id);
        houseSubscribeVo.setStatus(HouseSubscribeStatus.F.getValue());
        return houseSubscribeService.updateHouseSubscribe(houseSubscribeVo);
    }

    @PutMapping("/owner/house/subscribe/{id}")
    public Object confirmHouseSubscribe(@PathVariable("id") String id) {
        HouseSubscribeVo houseSubscribeVo = new HouseSubscribeVo();
        houseSubscribeVo.setId(id);
        houseSubscribeVo.setStatus(HouseSubscribeStatus.V.getValue());
        return houseSubscribeService.updateHouseSubscribe(houseSubscribeVo);
    }

    @DeleteMapping("/owner/house/subscribe")
    public Object cancelHouseSubscribe(HttpServletRequest request, @RequestBody HouseSubscribeVo houseSubscribeVo) {
        String userId = LoginUtils.getUserId(request, redisService);
        houseSubscribeVo.setUserId(userId);
        houseSubscribeVo.setStatus(HouseSubscribeStatus.C.getValue());
        // 房源预定扩展，存储取消原因
        HouseSubscribeExt houseSubscribeExt = houseSubscribeVo.convert2HouseSubscribeExt();
        houseSubscribeExt.setOperatorId(Integer.valueOf(userId));
        houseSubscribeExt.setOperatorType(OperatorType.O);

        // 将房源预定状态改成 已取消
        houseSubscribeService.updateHouseSubscribe(houseSubscribeVo);
        return CommonResult.success();
    }


    @GetMapping("/owner/house/subscribe")
    public Object getHouseSubscribe(HttpServletRequest request, @RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber) {
        String userId = LoginUtils.getUserId(request, redisService);
        Page page = new Page(pageSize, pageNumber);
        CommonResult houseSubscribeByUserId = houseSubscribeService.getHouseSubscribeByUserId(userId, page);
        return houseSubscribeByUserId;
    }
}

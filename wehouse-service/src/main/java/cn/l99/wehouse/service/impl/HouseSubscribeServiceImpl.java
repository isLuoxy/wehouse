package cn.l99.wehouse.service.impl;

import cn.l99.wehouse.dao.HouseSubscribeDao;
import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.HouseSubscribe;
import cn.l99.wehouse.pojo.Page;
import cn.l99.wehouse.pojo.User;
import cn.l99.wehouse.pojo.baseEnum.HouseSubscribeStatus;
import cn.l99.wehouse.pojo.dto.HouseSubscribeDto;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.pojo.vo.HouseSubscribeVo;
import cn.l99.wehouse.service.IHouseSubscribeService;
import cn.l99.wehouse.utils.DateUtils;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service(version = "${wehouse.service.version}")
@Slf4j
@Component
public class HouseSubscribeServiceImpl implements IHouseSubscribeService {

    public final HouseSubscribeDao houseSubscribeDao;

    public final HouseServiceImpl houseService;

    public final UserServiceImpl userService;

    @Autowired
    public HouseSubscribeServiceImpl(HouseSubscribeDao houseSubscribeDao, HouseServiceImpl houseService, UserServiceImpl userService) {
        this.houseSubscribeDao = houseSubscribeDao;
        this.houseService = houseService;
        this.userService = userService;
    }

    @Override
    public CommonResult addHouseSubscribe(HouseSubscribeVo houseSubscribeVo) {
        HouseSubscribe houseSubscribe = houseSubscribeVo.convert2HouseSubscribe();
        houseSubscribeDao.insertHouseSubscribe(houseSubscribe);
        return CommonResult.success();
    }

    @Override
    public CommonResult updateHouseSubscribe(HouseSubscribeVo houseSubscribeVo) {
        HouseSubscribe houseSubscribe = houseSubscribeVo.convert2HouseSubscribe();
        boolean result = houseSubscribeDao.updateHouseSubscribe(houseSubscribe);
        if (result) {
            return CommonResult.success();
        }
        return CommonResult.failure(001, "更新预约信息失败");
    }

    //TODO：后续可优化成数据库层面多表获取数据，减少数据库表的查询
    @Override
    public CommonResult getHouseSubscribeByUserId(String userId, Page page) {
        // 获取预定信息
        List<HouseSubscribe> houseSubscribeByUserId = houseSubscribeDao.getHouseSubscribeByUserId(userId, page.getPageStart(), page.getPageSize());
        if (houseSubscribeByUserId == null || houseSubscribeByUserId.isEmpty()) {
            return CommonResult.success();
        }
        // 获取用户
        Map<Integer, User> userForHouseSubscribe = userService.getUserForHouseSubscribe(houseSubscribeByUserId.stream().map(HouseSubscribe::getUserId).collect(Collectors.toList()));
        Map<String, House> houseForHouseSubscribe = houseService.getHouseForHouseSubscribe(houseSubscribeByUserId.stream().map(HouseSubscribe::getHouseId).collect(Collectors.toList()));

        List<HouseSubscribeDto> result = new ArrayList(houseSubscribeByUserId.size());

        for (HouseSubscribe subscribe : houseSubscribeByUserId) {
            User user = userForHouseSubscribe.get(subscribe.getUserId());
            House house = houseForHouseSubscribe.get(subscribe.getHouseId());
            HouseSubscribeDto houseSubscribeDto = HouseSubscribeDto.convert2HouseSubScribeDtoByHouseSubscribe(subscribe, house, user);
            result.add(houseSubscribeDto);
        }
        return CommonResult.success(result);
    }

    public CommonResult getHouseSubscribeByOwnerId(){
        // TODO
        return null;
    }
}

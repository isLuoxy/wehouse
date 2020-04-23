package cn.l99.wehouse.service.impl;

import cn.l99.wehouse.dao.HouseSubscribeDao;
import cn.l99.wehouse.pojo.*;
import cn.l99.wehouse.pojo.baseEnum.HouseSubscribeStatus;
import cn.l99.wehouse.pojo.dto.HouseSubscribeDto;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.pojo.vo.HouseSubscribeVo;
import cn.l99.wehouse.service.IHouseSubscribeExtService;
import cn.l99.wehouse.service.IHouseSubscribeService;
import cn.l99.wehouse.utils.DateUtils;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
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

    public final IHouseSubscribeExtService houseSubscribeExtService;

    @Autowired
    public HouseSubscribeServiceImpl(HouseSubscribeDao houseSubscribeDao, HouseServiceImpl houseService, UserServiceImpl userService, IHouseSubscribeExtService houseSubscribeExtService) {
        this.houseSubscribeDao = houseSubscribeDao;
        this.houseService = houseService;
        this.userService = userService;
        this.houseSubscribeExtService = houseSubscribeExtService;
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

    /**
     * @param userId
     * @param page
     * @return
     */
    @Override
    public CommonResult getHouseSubscribeByUserId(String userId, Page page) {
        // 获取预定信息
        List<HouseAndSubscribeAndUser> houseSubscribeByUserId = houseSubscribeDao.getHouseAndSubscribeAndUserByUserId(userId, page.getPageStart(), page.getPageSize());
        if (houseSubscribeByUserId == null || houseSubscribeByUserId.isEmpty()) {
            return CommonResult.success();
        }
        return CommonResult.success(houseSubscribeByUserId.stream().map(HouseAndSubscribeAndUser::convert2HouseSubscribeDto).collect(Collectors.toList()));
    }

    /**
     * 获取预定房源列表
     *
     * @return
     */
    public CommonResult getHouseSubscribeByOwnerId(String ownerId, Page page) {
        // 通过数据库构造房源预定响应
        List<HouseAndSubscribeAndUser> houseAndSubscribeAndUserByOwnerId = houseSubscribeDao.getHouseAndSubscribeAndUserByOwnerId(ownerId, page.getPageStart(), page.getPageSize());
        if (houseAndSubscribeAndUserByOwnerId == null || houseAndSubscribeAndUserByOwnerId.isEmpty()) {
            return CommonResult.success();
        }
        return CommonResult.success(houseAndSubscribeAndUserByOwnerId.stream().map(HouseAndSubscribeAndUser::convert2HouseSubscribeDto).collect(Collectors.toList()));
    }

    @Override
    public CommonResult getHouseSubscribeByHouseSubscribeVo(HouseSubscribeVo houseSubscribeVo, Page page) {
        Date startTime = houseSubscribeVo.getStartTime();
        Date endTime = houseSubscribeVo.getEndTime();
        if (startTime != null && endTime != null) {
            log.info("开始时间：{} - 结束时间：{}", startTime, endTime);
            // 设置开始时间从零点开始，结束时间到24点结束
            houseSubscribeVo.setStartTime(DateUtils.get0Am(startTime));
            houseSubscribeVo.setEndTime(DateUtils.get0Am(DateUtils.plusDays(endTime, 1)));
        }
        if (houseSubscribeVo.getStatus() != null) {
            houseSubscribeVo.setStatus(HouseSubscribeStatus.get(houseSubscribeVo.getStatus()).name());
        }
        List<HouseAndSubscribeAndUser> result = houseSubscribeDao.searchHouseAndSubscribeAndUser(houseSubscribeVo, page.getPageStart(), page.getPageSize());

        return CommonResult.success(result.stream().map(HouseAndSubscribeAndUser::convert2HouseSubscribeDto).collect(Collectors.toList()));
    }
}

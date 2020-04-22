package cn.l99.wehouse.service;

import cn.l99.wehouse.pojo.HouseSubscribe;
import cn.l99.wehouse.pojo.Page;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.pojo.vo.HouseSubscribeVo;

/**
 * 房源预定服务层接口
 */
public interface IHouseSubscribeService {

    CommonResult addHouseSubscribe(HouseSubscribeVo houseSubscribeVo);

    CommonResult updateHouseSubscribe(HouseSubscribeVo houseSubscribeVo);

    CommonResult getHouseSubscribeByUserId(String userId, Page page);
}

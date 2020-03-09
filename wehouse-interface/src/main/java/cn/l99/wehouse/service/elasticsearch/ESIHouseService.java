package cn.l99.wehouse.service.elasticsearch;

import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.response.CommonResult;


/**
 * 用于es的房屋服务接口
 */
public interface ESIHouseService {

    CommonResult addHouse(House house);

    CommonResult findHouseByCondition(String cityPyName, String condition, String search);
}

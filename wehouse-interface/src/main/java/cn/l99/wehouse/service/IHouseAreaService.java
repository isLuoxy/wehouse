package cn.l99.wehouse.service;

import cn.l99.wehouse.pojo.response.CommonResult;

/**
 * 房源所属城市、区域服务类
 */
public interface IHouseAreaService {

    CommonResult insertCity(String cityName,String cityPyName);
}

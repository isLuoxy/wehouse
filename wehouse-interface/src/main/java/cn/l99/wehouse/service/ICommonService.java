package cn.l99.wehouse.service;

import cn.l99.wehouse.pojo.response.CommonResult;

/**
 * 通用服务层接口
 *
 * @author L99
 */
public interface ICommonService {

    // 获取房源的城市
    CommonResult getCitys();

    // 获取城市下的区域
    CommonResult getResions(String keyword);

    // 根据城市名获取地铁线路
    CommonResult getSubwayline(String city);

    // 根据地铁线路id获取具体站台
    CommonResult getSubwayStation(String subwaylineId);
}

package cn.l99.wehouse.service;

import cn.l99.wehouse.pojo.response.CommonResult;

/**
 * 房屋服务层接口
 *
 * @author L99
 */
public interface IHouseService {

    /**
     * 根据中文拼音获取房源
     *
     * @param cityPinyinName
     * @return
     */
    CommonResult getHouseByCityName(String cityPinyinName,String condition);

    /**
     * 获取某个房源的详情信息
     */
    CommonResult getAHouseByHouseId(String houseId);
}

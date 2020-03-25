package cn.l99.wehouse.service;

import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.pojo.vo.HouseVo;

/**
 * 房屋服务层接口
 *
 * @author L99
 */
public interface IHouseService {

    /**
     * 根据中文拼音获取房源
     * <b>数据库层面的搜索，暂不使用</b>
     *
     * @param cityPinyinName
     * @return
     */
    CommonResult getHouseByCityName(String cityPinyinName, String condition);

    /**
     * 获取某个房源的详情信息
     */
    CommonResult getAHouseByHouseId(String houseId);

    /**
     * 新增房源
     *
     * @param houseVo {@link HouseVo} 通用房源请求对象
     * @return
     */
    CommonResult addHouse(HouseVo houseVo);
}

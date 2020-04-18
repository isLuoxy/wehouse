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
    CommonResult getHouseByCondition(String cityPinyinName, String condition);

    /**
     * 获取某个房源的详情信息
     */
    CommonResult getAHouseByHouseId(String houseId,String userId);

    /**
     * 新增房源
     *
     * @param houseVo {@link HouseVo} 通用房源请求对象
     * @return
     */
    CommonResult addHouse(HouseVo houseVo);

    /**
     * 通过条件查找房源
     * 如果用户Id为空，说明当前用户并未登录，则无需对房源进行个性化排序和记录用户操作历史
     *
     * @param cityPyName
     * @param condition
     * @param searchWord 关键词
     * @param userId     用户Id
     * @return
     */
    CommonResult findHouseByCondition(String cityPyName, String condition, String searchWord, String userId);
}

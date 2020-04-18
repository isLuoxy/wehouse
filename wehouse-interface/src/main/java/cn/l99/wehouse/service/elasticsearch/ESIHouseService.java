package cn.l99.wehouse.service.elasticsearch;

import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.utils.condition.recommendation.HouseRecommendationCondition;


/**
 * 用于es的房屋服务接口
 */
public interface ESIHouseService {

    /**
     * 添加新房源
     *
     * @param house
     * @return
     */
    CommonResult addHouseToEs(House house);

    /**
     * 根据条件查找相似房源
     * 用于新增房源时使用
     *
     * @param houseRecommendationCondition
     * @return
     */
    CommonResult findSimilarHouseByCondition(HouseRecommendationCondition houseRecommendationCondition);

    /**
     * 根据条件查找房源，用于房源展示
     *
     * @param cityPyName
     * @param condition
     * @param search
     * @return
     */
    CommonResult findHouseByCondition(String cityPyName, String condition, String search);
}

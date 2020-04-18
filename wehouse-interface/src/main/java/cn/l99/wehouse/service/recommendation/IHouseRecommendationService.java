package cn.l99.wehouse.service.recommendation;

import java.util.List;

/**
 * 房源推荐服务接口
 *
 * @author L99
 */
public interface IHouseRecommendationService {

    /**
     * 通过中心房源的id获取特定数量的推荐房源
     *
     * @param HouseId 中心房源id
     * @param amount  推荐房源的数量
     * @return 返回推荐房源的房源id列表
     */
    List<String> getRecommendationByCenterHouse(String HouseId, int amount);

    /**
     * 添加新房源时设置词向量，取参照房源的平均房源
     * <p>
     * 用于新增房源时使用
     *
     * @param HouseId        添加房源
     * @param referenceHouse 参照房源
     */
    void addHouseVector(String HouseId, List<String> referenceHouse);

    /**
     * 获取参照房源的平均向量，计算跟候选房源的余弦距离进行排序
     * <p>
     * 用于房源搜索时使用
     *
     * @param reference 参照房源
     * @param candidate 候选房源
     * @return 排序后的候选房源
     */
    List<String> sortHouse(List<String> reference, List<String> candidate);
}

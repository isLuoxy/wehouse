package cn.l99.wehouse.service.impl;

import cn.l99.wehouse.dao.HouseDao;
import cn.l99.wehouse.dao.HouseExtDao;
import cn.l99.wehouse.pojo.AHouse;
import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.SearchHistory;
import cn.l99.wehouse.pojo.UserOperation;
import cn.l99.wehouse.pojo.baseEnum.ErrorCode;
import cn.l99.wehouse.pojo.baseEnum.OperationType;
import cn.l99.wehouse.pojo.dto.HouseDto;
import cn.l99.wehouse.pojo.dto.SimpleHouseDto;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.pojo.vo.HouseVo;
import cn.l99.wehouse.redis.RedisUtils;
import cn.l99.wehouse.service.IHouseService;
import cn.l99.wehouse.service.ISearchHistoryService;
import cn.l99.wehouse.service.IUserOperationService;
import cn.l99.wehouse.service.elasticsearch.ESIHouseService;
import cn.l99.wehouse.service.recommendation.IHouseRecommendationService;
import cn.l99.wehouse.utils.DateUtils;
import cn.l99.wehouse.utils.HouseUtils;
import cn.l99.wehouse.utils.condition.HouseCondition;
import cn.l99.wehouse.utils.condition.recommendation.HouseRecommendationCondition;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 房屋服务层接口实现类
 *
 * @author L99
 */
@Service(version = "${wehouse.service.version}")
@Component
@Slf4j
public class HouseServiceImpl implements IHouseService {

    @Autowired
    private HouseDao houseDao;

    @Autowired
    private HouseExtDao houseExtDao;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ESIHouseService esHouseService;

    @Autowired
    private IUserOperationService userOperationService;

    @Autowired
    private ISearchHistoryService searchHistoryService;


    @Reference(version = "${wehouse.service.version}")
    IHouseRecommendationService houseRecommendationService;

    @Override
    public CommonResult getHouseByCondition(String cityPyName, String condition) {
        HouseCondition houseCondition = HouseUtils.acqConditions(condition);
        if (houseCondition == null) {
            log.info("获取房源时没有条件");
        }
        // 如果处理条件中 regionName 不为空，那么此时的值为 regionID，需要改为 regionName
        if (houseCondition != null && !StringUtils.isEmpty(houseCondition.getRegionCnName())) {
            String regionCnName = (String) redisUtils.get(houseCondition.getRegionCnName());
            houseCondition.setRegionCnName(regionCnName);
        }
        List<House> houseByCityName = houseDao.getHouseByCityPyNameAndCondition(cityPyName, houseCondition);
        List<SimpleHouseDto> simpleHouseDtoList = new ArrayList<>(houseByCityName.size());
        houseByCityName.forEach(house -> {
            SimpleHouseDto simpleHouseDto = new SimpleHouseDto();
            simpleHouseDto.convertToSimpleHouseDtoFromHouse(house);
            simpleHouseDtoList.add(simpleHouseDto);
        });
        return CommonResult.success(simpleHouseDtoList);
    }

    @Override
    public CommonResult getAHouseByHouseId(String houseId, String userId) {
        AHouse aHouseByHouseId = houseDao.getAHouseByHouseId(houseId);
        log.info("Ahouse:{}", aHouseByHouseId);
        if (aHouseByHouseId == null) {
            return CommonResult.failure(ErrorCode.HOUSE_NOT_EXIST);
        }
        HouseDto houseDto = new HouseDto();
        houseDto.convertByAHouse(aHouseByHouseId);

        if (!StringUtils.isEmpty(userId)) {
            // 当用户登录后，需要进行页面埋点，记录用户在页面的停留时间
            UserOperation userOperation = constructUserOperation(userId, houseId, OperationType.C);
            userOperationService.addUserOperation(userOperation);
            houseDto.setUsId(userOperation.getId());
        }
        return CommonResult.success(houseDto);
    }


    @Override
    public CommonResult addHouse(HouseVo houseVo) {
        // 添加数据库 ->  添加 es 集群
        House house = houseVo.convertToHouse();

        houseDao.insertHouse(house);
        houseExtDao.insertHouseExt(house.getHouseExt());

        // == 异步为新房源添加房源向量 ==
        // 1、获取相似房源
        log.info("添加房源向量");
        HouseRecommendationCondition houseRecommendationCondition = acqHouseRecommendationCondition(house);
        CommonResult similarHouseByCondition = esHouseService.findSimilarHouseByCondition(houseRecommendationCondition);
        List<House> similarHouses = (List<House>) similarHouseByCondition.getData();
        // 2、新增房源向量
        houseRecommendationService.addHouseVector(house.getId(),
                similarHouses
                        .stream()
                        .map(House::getId)
                        .collect(Collectors.toList()));

        log.info("新增房源到es");
        // 添加房源到 es
        esHouseService.addHouseToEs(house);
        return CommonResult.success();
    }

    @Override
    public CommonResult findHouseByCondition(String cityPyName, String condition, String searchWords, String userId) {
        CommonResult result = esHouseService.findHouseByCondition(cityPyName, condition, searchWords);

        if (!StringUtils.isEmpty(userId)) {
            // 异步添加房源搜索记录
            searchHistoryService.addSearchHistory(constructSearchHistory(userId, condition, searchWords));

            // 对查找的房源进行个性化排序
            // 候选房源
            List<SimpleHouseDto> candidateList = (List<SimpleHouseDto>) result.getData();

            // 获取用户过去15天的操作记录
            Date start = DateUtils.minusDays(DateUtils.get0Am(), 15);
            // 参考房源
            List<UserOperation> referenceList = userOperationService.findUserOperationByUserIdAndGtOperationTime(userId, start);
            log.info("参考房源列表：{}", referenceList);
            log.info("搜索日志埋点：");
            // 对房源列表进行个性化排序
            List<String> referenceListTmp = referenceList.stream()
                    .map(HouseServiceImpl::extractHouseIdFromUserOperationAndConvertTypeToString)
                    .collect(Collectors.toList());
            log.info("参考房源id列表:{}", referenceListTmp);
            List<String> candidateListTmp = candidateList.stream()
                    .map(HouseServiceImpl::extractHouseIdFromSimpleHouseDtoAndConvertTypeToString)
                    .collect(Collectors.toList());
            log.info("候选房源id列表:{}", candidateListTmp);
            List<String> sortHouseList = houseRecommendationService.sortHouse(referenceListTmp, candidateListTmp);

            // 对原本的候选房源根据排序后的房源列表进行排序
            Map<String, SimpleHouseDto> tmpMap = candidateList.stream().collect(Collectors.toMap(HouseServiceImpl::extractHouseIdFromSimpleHouseDtoAndConvertTypeToString, Function.identity()));
            for (int i = 0; i < sortHouseList.size(); i++) {
                String houseId = sortHouseList.get(i);
                SimpleHouseDto simpleHouseDto = tmpMap.get(houseId);
                candidateList.set(i, simpleHouseDto);
            }
        }
        return result;
    }

    @Override
    public Map<String, House> getHouseForHouseSubscribe(List<String> houseId) {
        List<House> houseByHouseId = houseDao.getHouseByHouseId(houseId);
        return houseByHouseId == null ? null : houseByHouseId.stream().collect(Collectors.toMap(House::getId, Function.identity()));
    }

    private HouseRecommendationCondition acqHouseRecommendationCondition(House house) {
        HouseRecommendationCondition houseRecommendationCondition = new HouseRecommendationCondition();
        houseRecommendationCondition.setRentalType(house.getRentalType().name());
        houseRecommendationCondition.setHouseType(house.getHouseType());
        HouseUtils.Rent rent = HouseUtils.Rent.judgingRangeByRent(BigDecimal.valueOf(house.getPrice()));
        if (rent != null) {
            houseRecommendationCondition.setRentGreaterThanOrEqual(rent.getRentGreaterThanOrEqual().doubleValue());
            houseRecommendationCondition.setRentLessThan(rent.getRentLessThan().doubleValue());
        }
        houseRecommendationCondition.setAddress(house.getAddress());
        houseRecommendationCondition.setStart(0);
        houseRecommendationCondition.setSize(100);
        return houseRecommendationCondition;
    }

//    /**
//     * 用于进行列表转换时，获取房源id并进行格式化
//     *
//     * @param house
//     * @return
//     */
//    private static String extractHouseIdFromHouseAndConvertTypeToString(House house) {
//        return String.valueOf(house.getId());
//    }

    private static String extractHouseIdFromSimpleHouseDtoAndConvertTypeToString(SimpleHouseDto simpleHouseDto) {
        return String.valueOf(simpleHouseDto.getId());
    }

    private static String extractHouseIdFromUserOperationAndConvertTypeToString(UserOperation userOperation) {
        return String.valueOf(userOperation.getId());
    }

    private static UserOperation constructUserOperation(String userId, String houseId, OperationType operationType) {
        UserOperation userOperation = new UserOperation();
        userOperation.setUserId(Integer.valueOf(userId));
        userOperation.setHouseId(houseId);
        userOperation.setOperationType(operationType);
        return userOperation;
    }

    private static SearchHistory constructSearchHistory(String userId, String condition, String keyWords) {
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setUserId(Integer.valueOf(userId));
        searchHistory.setCondition(condition);
        searchHistory.setKeyWords(keyWords);
        searchHistory.setSearchTime(DateUtils.now());
        return searchHistory;
    }

}

package cn.l99.wehouse.service.impl.elasticsearch;

import cn.l99.wehouse.elasticsearch.ESHouseRepository;
import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.baseEnum.RentalType;
import cn.l99.wehouse.pojo.dto.SimpleHouseDto;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.redis.RedisUtils;
import cn.l99.wehouse.service.elasticsearch.ESIHouseService;
import cn.l99.wehouse.utils.HouseUtils;
import cn.l99.wehouse.utils.condition.HouseCondition;
import cn.l99.wehouse.utils.condition.recommendation.HouseRecommendationCondition;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(version = "${wehouse.service.version}")
@Slf4j
public class ESHouseServiceImpl implements ESIHouseService {

    @Autowired
    private ESHouseRepository ESHouseRepository;

    @Autowired
    RedisUtils redisUtils;


    @Override
    public CommonResult addHouseToEs(House house) {
        ESHouseRepository.save(house);
        return CommonResult.success();
    }

    @Override
    public CommonResult findSimilarHouseByCondition(HouseRecommendationCondition houseRecommendationCondition) {
        log.info("查找相似房源");
        Page<House> houseList = ESHouseRepository.search(constructSearchQuery(houseRecommendationCondition));
        List<House> content = houseList.getContent();
        log.info("相似房源:{}", JSONArray.toJSONString(content));
        return CommonResult.success(content);
    }

    @Override
    public CommonResult findHouseByCondition(String cityPyName, String condition, String search) {

        // 此时 cityPyName 为拼音，而在 es 中为全称，此时需要进行转换
        String cityCnName = (String) redisUtils.get(cityPyName);
        HouseCondition houseCondition = HouseUtils.acqConditions(condition);
        // 如果处理条件中 regionName 不为空，那么此时的值为 regionID，需要改为 regionName
        if (houseCondition != null && !StringUtils.isEmpty(houseCondition.getRegionCnName())) {
            String regionCnName = (String) redisUtils.get(houseCondition.getRegionCnName());
            houseCondition.setRegionCnName(regionCnName);
        }

        Page<House> houseList = ESHouseRepository.search(constructSearchQuery(houseCondition, cityCnName, search));
        List<House> content = houseList.getContent();

        List<SimpleHouseDto> simpleHouseDtoList = new ArrayList<>(content.size());
        content.stream().forEach(house -> {
            SimpleHouseDto simpleHouseDto = new SimpleHouseDto();
            simpleHouseDto.convertToSimpleHouseDtoFromHouse(house);
            simpleHouseDtoList.add(simpleHouseDto);
        });
        return CommonResult.success(simpleHouseDtoList);
    }

    /**
     * 组装 searchQuery
     *
     * @param houseCondition
     * @param cityCnName
     * @param search
     * @return
     */
    private SearchQuery constructSearchQuery(HouseCondition houseCondition, String cityCnName, String search) {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 组装 queryBuilder
        useCityCnNameConstrucQueryBuilder(boolQueryBuilder, cityCnName);
        UseConditionConstructQueryBuilder(boolQueryBuilder, houseCondition);
        UseSearchStringConstructQueryBuilder(boolQueryBuilder, search);

        // 设置分页
        if(houseCondition == null){
            houseCondition = new HouseCondition();
        }
        Pageable pageable = PageRequest.of(Integer.parseInt(houseCondition.getPageStart()), Integer.parseInt(houseCondition.getPageSize()));

        //构建查询
        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(pageable)
                .build();

        return query;
    }

    /**
     * 用于构造 {@link HouseRecommendationCondition} 的 es 条件
     *
     * @param houseRecommendationCondition {@link HouseRecommendationCondition}，新增房源时查找相似房源使用
     * @return {@link SearchQuery}
     */
    private SearchQuery constructSearchQuery(HouseRecommendationCondition houseRecommendationCondition) {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 组装 queryBuilder
        UseConditionConstructQueryBuilder(boolQueryBuilder, houseRecommendationCondition);

        // 查找前100条数据
        Pageable pageable = PageRequest.of(houseRecommendationCondition.getStart(), houseRecommendationCondition.getSize());

        //构建查询
        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(pageable)
                .build();

        return query;
    }

    /**
     * 通过城市组装 QueryBuilder
     *
     * @param boolQueryBuilder
     * @param cityCnName       城市名称
     */
    private void useCityCnNameConstrucQueryBuilder(BoolQueryBuilder boolQueryBuilder, String cityCnName) {
        boolQueryBuilder.must(QueryBuilders.matchQuery("cityCnName", cityCnName));
    }


    /**
     * 通过条件组装 QueryBuilder
     *
     * @param boolQueryBuilder
     * @param houseCondition   条件
     */
    private void UseConditionConstructQueryBuilder(BoolQueryBuilder boolQueryBuilder, HouseCondition houseCondition) {
        if (StringUtils.isEmpty(houseCondition)) {
            return;
        }

        if (houseCondition.getRegionCnName() != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("regionCnName", houseCondition.getRegionCnName()));
        }
        if (houseCondition.getSubwayLineId() != null) {
            boolQueryBuilder.must(QueryBuilders.termQuery("houseExt.subwayLineId", houseCondition.getSubwayLineId()));
        }
        if (houseCondition.getHouseType() != null) {
            boolQueryBuilder.must(QueryBuilders.termQuery("houseType", houseCondition.getHouseType()));
        }
        if (houseCondition.getOrientation() != null) {
            boolQueryBuilder.must(QueryBuilders.termQuery("orientation", houseCondition.getOrientation()));
        }
        // 价格区间
        if (houseCondition.getRentGreaterThanOrEqual() != null) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("price").gte(houseCondition.getRentGreaterThanOrEqual()));
        }
        if (houseCondition.getRentLessThan() != null) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("price").lt(houseCondition.getRentLessThan()));
        }
    }

    /**
     * 通过条件组装 QueryBuilder
     *
     * @param boolQueryBuilder
     * @param houseRecommendationCondition 条件
     */
    private void UseConditionConstructQueryBuilder(BoolQueryBuilder boolQueryBuilder, HouseRecommendationCondition houseRecommendationCondition) {
        if (StringUtils.isEmpty(houseRecommendationCondition)) {
            return;
        }

        // 出租方式
        if (houseRecommendationCondition.getRentalType() != null) {
            boolQueryBuilder.must(QueryBuilders.termQuery("rentalType", houseRecommendationCondition.getRentalType()));
            // 如果是整租的话，还要传 房源类型
            if (RentalType.Z.name().equals(houseRecommendationCondition.getRentalType()) && houseRecommendationCondition.getHouseType() != null) {
                boolQueryBuilder.must(QueryBuilders.termQuery("houseType", houseRecommendationCondition.getHouseType()));
            }
        }

        // 价格区间
        if (houseRecommendationCondition.getRentGreaterThanOrEqual() != null) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("price").gte(houseRecommendationCondition.getRentGreaterThanOrEqual()));
        }
        if (houseRecommendationCondition.getRentLessThan() != null) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("price").lt(houseRecommendationCondition.getRentLessThan()));
        }

        // 地区查找,如果有满足条件的一并查出
        if (houseRecommendationCondition.getAddress() != null) {
            // TODO
            boolQueryBuilder.must(QueryBuilders.matchQuery("address", houseRecommendationCondition.getAddress()));
        }
    }

    /**
     * 根据查询字符串构造 QueryBuilder
     *
     * @param boolQueryBuilder
     * @param search           查询字符串
     */
    private void UseSearchStringConstructQueryBuilder(BoolQueryBuilder boolQueryBuilder, String search) {
        if (search == null) {
            return;
        }
        Map field = new HashMap(4);
        field.put("name", 8.0f);
        field.put("place_cn_name", 5.0f);
        field.put("village", 4.0f);
        boolQueryBuilder.must(QueryBuilders.multiMatchQuery(search, "name").fields(field));
    }
}

package cn.l99.wehouse.service.impl.elasticsearch;

import cn.l99.wehouse.elasticsearch.ESHouseRepository;
import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.dto.SimpleHouseDto;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.redis.RedisUtils;
import cn.l99.wehouse.service.elasticsearch.ESIHouseService;
import cn.l99.wehouse.utils.HouseUtils;
import cn.l99.wehouse.utils.condition.HouseCondition;
import com.alibaba.dubbo.config.annotation.Service;
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
public class ESHouseServiceImpl implements ESIHouseService {

    @Autowired
    private ESHouseRepository ESHouseRepository;

    @Autowired
    RedisUtils redisUtils;

    @Override
    public CommonResult addHouse(House house) {
        ESHouseRepository.save(house);
        return CommonResult.success();
    }

    @Override
    public CommonResult findHouseByCondition(String cityPyName, String condition, String search) {

        // 此时 cityPyName 为拼音，而在 es 中为全称，此时需要进行转换
        String cityCnName = (String) redisUtils.get(cityPyName);
        HouseCondition houseCondition = HouseUtils.acqConditions(condition);
        // 如果处理条件中 regionName 不为空，那么此时的值为 regionID，需要改为 regionName
        if (!StringUtils.isEmpty(houseCondition.getRegionCnName())) {
            String regionCnName = (String) redisUtils.get(houseCondition.getRegionCnName());
            houseCondition.setRegionCnName(regionCnName);
        }

        Page<House> houseList = ESHouseRepository.search(constructSearchQuery(cityCnName, houseCondition, search));
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
     * @param cityCnName
     * @param houseCondition
     * @param search
     * @return
     */
    private SearchQuery constructSearchQuery(String cityCnName, HouseCondition houseCondition, String search) {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 组装 queryBuilder
        useCityCnNameConstrucQueryBuilder(boolQueryBuilder, cityCnName);
        UseConditionConstructQueryBuilder(boolQueryBuilder, houseCondition);
        UseSearchStringConstructQueryBuilder(boolQueryBuilder, search);

        // 设置分页
        Pageable pageable = PageRequest.of(Integer.parseInt(houseCondition.getPageStart()), Integer.parseInt(houseCondition.getPageSize()));

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

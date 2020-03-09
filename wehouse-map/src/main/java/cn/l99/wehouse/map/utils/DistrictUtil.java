package cn.l99.wehouse.map.utils;

import cn.l99.wehouse.map.config.LbsConfig;
import cn.l99.wehouse.map.lbs.District;
import cn.l99.wehouse.map.lbs.DistrictResult;
import cn.l99.wehouse.map.web.WebClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 高德地图行政区域查询
 *
 * @author L99
 */
public class DistrictUtil {

    /**
     * 通过关键词返回对应的下级区域名称
     *
     * @param keywords 省份名、城市名、区域名
     * @return
     */
    public static District getRegionName(String keywords) {
        return getRegionName(keywords, 1, LbsConfig.extensions.BASE.getValue());
    }

    /**
     * 获取城市和对应的区域 adcode 和 name
     *
     * @param keywords    规则：只支持单个关键词语搜索关键词支持：行政区名称、citycode、adcode
     *                    <p>
     *                    例如，在subdistrict=2，搜索省份（例如山东），能够显示市（例如济南），区（例如历下区）
     * @param subdistrict 规则：设置显示下级行政区级数（行政区级别包括：国家、省/直辖市、市、区/县4个级别）
     *                    <p>
     *                    可选值：0、1、2、3
     *                    <p>
     *                    0：不返回下级行政区；
     *                    <p>
     *                    1：返回下一级行政区；
     *                    <p>
     *                    2：返回下两级行政区；
     *                    <p>
     *                    3：返回下三级行政区；
     * @param extensions  此项控制行政区信息中返回行政区边界坐标点； 可选值：base、all;
     *                    <p>
     *                    base:不返回行政区边界坐标点；
     *                    <p>
     *                    all:只返回当前查询district的边界值，不返回子节点的边界值；
     * @return
     */
    public static District getRegionName(String keywords, int subdistrict, String extensions) {
        RestTemplate restTemplate = WebClient.getClient();
        String url = String.format(LbsConfig.url, keywords, subdistrict, LbsConfig.key, extensions);
        DistrictResult districtResult = JSONObject.parseObject(restTemplate.getForObject(url, String.class), DistrictResult.class);
        District district = districtResult.getDistricts().get(0);
//        Map<String, Object> result = new LinkedHashMap<>();
//        district.getDistricts().forEach(district1 -> {
//            // 区域编号为 key ，区域名称为 value
//            result.put(district1.getAdcode(), district1.getName());
//        });
        return district;
    }


//    public static void main(String[] args) {
//        Map<String, Object> temp = DistrictUtil.getRegionName("深圳市");
//        String jsonString = JSONObject.toJSONString(temp);
//        //Map<Object, Object> regionMap = JSONObject.parseObject(jsonString, Map.class);
//        Map<Object, Object> regionMap = (Map<Object, Object>) JSON.parse(jsonString);
//        List<String> result = regionMap.entrySet().stream().map(e -> {
//            String s = ((String) e.getKey() + (String) e.getValue());
//            return s;
//        }).collect(Collectors.toList());
//
//        System.out.println(JSONObject.toJSONString(result));
//    }

}

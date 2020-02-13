package cn.l99.wehouse.map.utils;

import cn.l99.wehouse.map.config.LbsConfig;
import cn.l99.wehouse.map.lbs.District;
import cn.l99.wehouse.map.lbs.DistrictResult;
import cn.l99.wehouse.map.web.WebClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public static Map<String, Object> getRegionName(String keywords) {
        return getRegionName(keywords, 1, LbsConfig.extensions.BASE.getValue());
    }

    public static Map<String, Object> getRegionName(String keywords, int subdistrict, String extensions) {
        RestTemplate restTemplate = WebClient.getClient();
        String url = String.format(LbsConfig.url, keywords, subdistrict, LbsConfig.key, extensions);
        DistrictResult districtResult = JSONObject.parseObject(restTemplate.getForObject(url, String.class), DistrictResult.class);
        District district = districtResult.getDistricts().get(0);
        Map<String, Object> result = new HashMap<>();
        district.getDistricts().forEach(district1 -> {
            // 区域编号为 key ，区域名称为 value
            result.put(district1.getAdcode(), district1.getName());
        });
        return result;
    }

    public static void main(String[] args) {
        Map<String, Object> temp = DistrictUtil.getRegionName("深圳市");
        String jsonString = JSONObject.toJSONString(temp);
        //Map<Object, Object> regionMap = JSONObject.parseObject(jsonString, Map.class);
        Map<Object, Object> regionMap = (Map<Object, Object> )JSON.parse(jsonString);
        List<String> result = regionMap.entrySet().stream().map(e -> {
            String s = ((String) e.getKey() + (String) e.getValue());
            return s;
        }).collect(Collectors.toList());

        System.out.println(JSONObject.toJSONString(result));
    }
}

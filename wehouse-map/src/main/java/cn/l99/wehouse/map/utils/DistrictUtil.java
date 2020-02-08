package cn.l99.wehouse.map.utils;

import cn.l99.wehouse.map.config.LbsConfig;
import cn.l99.wehouse.map.lbs.District;
import cn.l99.wehouse.map.lbs.DistrictResult;
import cn.l99.wehouse.map.web.WebClient;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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
    public static List<String> getRegionName(String keywords) {
        return getRegionName(keywords, 1, LbsConfig.extensions.BASE.getValue());
    }

    public static List<String> getRegionName(String keywords, int subdistrict, String extensions) {
        RestTemplate restTemplate = WebClient.getClient();
        String url = String.format(LbsConfig.url, keywords, subdistrict, LbsConfig.key, extensions);
        DistrictResult districtResult = JSONObject.parseObject(restTemplate.getForObject(url, String.class), DistrictResult.class);
        District district = districtResult.getDistricts().get(0);
        List<String> result = new ArrayList<>();
        district.getDistricts().forEach(district1 -> {
            result.add(district1.getName());
        });
        return result;
    }

    public static void main(String[] args) {
        List<String> list = DistrictUtil.getRegionName("龙岗区");
        list.forEach(System.out::println);
    }
}

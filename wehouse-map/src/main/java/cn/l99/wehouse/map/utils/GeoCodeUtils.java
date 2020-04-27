package cn.l99.wehouse.map.utils;

import cn.l99.wehouse.map.config.GeoCodeConfig;
import cn.l99.wehouse.map.entity.GeoCodeResult;
import cn.l99.wehouse.map.web.WebClient;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 地理编码类
 */
public class GeoCodeUtils {

    private final static Logger log = LoggerFactory.getLogger(GeoCodeUtils.class);

    public static String getGeoCode(String address, String city) {
        RestTemplate restTemplate = WebClient.getClient();
        String url = String.format(GeoCodeConfig.url, address, GeoCodeConfig.key, city);
        GeoCodeResult geoCodeResult = JSONObject.parseObject(restTemplate.getForObject(url, String.class), GeoCodeResult.class);
        String result = new String();
        if ("0".equals(geoCodeResult.getStatus()) || geoCodeResult.getGeocodes().isEmpty()) {
            // 请求失败
            log.error("地址: {} 编码失败", address);
            return result;
        }
        result = geoCodeResult.getGeocodes().get(0).getLocation();
        return result;
    }

    public static void main(String[] args) {
        String geoCode = GeoCodeUtils.getGeoCode("深业新岸线三期", "shenzhen");
        System.out.println(geoCode);
    }
}

package cn.l99.wehouse.map.utils;

import cn.l99.wehouse.map.config.AroundConfig;
import cn.l99.wehouse.map.config.GeoCodeConfig;
import cn.l99.wehouse.map.entity.Around;
import cn.l99.wehouse.map.entity.AroundResult;
import cn.l99.wehouse.map.entity.GeoCodeResult;
import cn.l99.wehouse.map.web.WebClient;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class AroundUtils {

    private final static Logger log = LoggerFactory.getLogger(GeoCodeUtils.class);

    public static String getDistance(String longitude, String latitude) {
        if (StringUtils.isEmpty(longitude) || StringUtils.isEmpty(latitude)) {
            return null;
        }
        RestTemplate restTemplate = WebClient.getClient();
        String url = String.format(AroundConfig.url, AroundConfig.key, longitude + "," + latitude, "地铁站");
        AroundResult aroundResult = JSONObject.parseObject(restTemplate.getForObject(url, String.class), AroundResult.class);
        List<Around> pois = aroundResult.getPois();
        if (pois.isEmpty()) {
            return null;
        }
        String name = pois.get(0).getName();
        String distance = pois.get(0).getDistance();
        return name + "约" + distance;
    }

    public static void main(String[] args) {
        String distance = AroundUtils.getDistance("113.889324", "22.558974");
        System.out.println(distance);
    }
}

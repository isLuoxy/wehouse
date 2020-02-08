package cn.l99.wehouse.map.config;

import cn.l99.wehouse.map.lbs.District;
import cn.l99.wehouse.map.lbs.DistrictResult;
import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

/**
 * 高德地图配置
 *
 * @author L99
 */
public class LbsConfig {

    // 后端 key
    public static final String key = "7668ac031f27c9e69b559940dcf0f96d";

    public static final String url = "https://restapi.amap.com/v3/config/district?keywords=%s&subdistrict=%d&key=%s&extensions=%s";

     public enum extensions {
        BASE("base"),
        ALL("all");
        private String value;

        extensions(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static void main(String[] args) {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.build();
        String url1 = String.format(url, "深圳市", key);
        String result = restTemplate.getForObject(url1, String.class);
//        代码解法
//        JSONObject jsonObject = JSONObject.parseObject(result);
//        String name = jsonObject.getString("districts");
//        System.out.println(name);
//        JSONArray innerJsonObjet = JSONArray.parseArray(name);
//        JSONObject o = (JSONObject) innerJsonObjet.get(0);
//        String districts = o.getString("districts");
//        JSONArray objects = JSONArray.parseArray(districts);
//        for (int i = 0; i < objects.size(); i++) {
//            JSONObject jsonObject1 = (JSONObject) objects.get(i);
//            String name1 = jsonObject1.getString("name");
//            System.out.println(name1);
//        }

        DistrictResult districtResult = JSONObject.parseObject(result, DistrictResult.class);
        District district = districtResult.getDistricts().get(0);
        district.getDistricts().forEach(district1 -> {
            System.out.println(district1.getName());
        });
    }
}

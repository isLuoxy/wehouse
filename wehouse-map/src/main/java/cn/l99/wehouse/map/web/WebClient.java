package cn.l99.wehouse.map.web;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

/**
 * web client 调用
 *
 * @author L99
 */
public class WebClient {

    public static RestTemplate getClient() {
        return new RestTemplateBuilder().build();
    }
}

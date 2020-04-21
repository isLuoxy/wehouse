package cn.l99.wehouse.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 当前环境
 */
@Component
@ConfigurationProperties(prefix = "spring.profiles")
public class EnvironmentProfiles {

    private String active;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}

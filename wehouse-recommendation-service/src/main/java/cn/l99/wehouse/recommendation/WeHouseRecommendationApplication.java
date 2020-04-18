package cn.l99.wehouse.recommendation;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;

@Slf4j
@SpringBootApplication
@EnableDubbo
@EnableAsync
public class WeHouseRecommendationApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeHouseRecommendationApplication.class, args);
        try {
            System.in.read();
        } catch (IOException e) {
            log.error("{}", e);
        }
    }
}

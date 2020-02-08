package cn.l99.wehouse.service;

import cn.l99.wehouse.redis.RedisUtils;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@ComponentScan("cn.l99.wehouse.redis")
@MapperScan("cn.l99.wehouse.dao")
@EnableDubbo
@Slf4j
public class WeHouseApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeHouseApplication.class, args);
        try {
            System.in.read();
        } catch (IOException e) {
            log.error("{}", e);
        }
    }
}

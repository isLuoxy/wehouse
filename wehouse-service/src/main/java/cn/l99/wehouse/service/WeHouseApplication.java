package cn.l99.wehouse.service;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;

@SpringBootApplication
@ComponentScan({"cn.l99.wehouse.redis","cn.l99.wehouse.mail","cn.l99.wehouse.utils","cn.l99.wehouse.service.impl"})
@MapperScan("cn.l99.wehouse.dao")
@EnableDubbo
@EnableElasticsearchRepositories(basePackages = "cn.l99.wehouse.elasticsearch")
@Slf4j
@EnableAsync
public class WeHouseApplication {
    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(WeHouseApplication.class, args);
        try {
            System.in.read();
        } catch (IOException e) {
            log.error("{}", e);
        }
    }
}

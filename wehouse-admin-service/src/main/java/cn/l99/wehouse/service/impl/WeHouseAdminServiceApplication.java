package cn.l99.wehouse.service.impl;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@ComponentScan({"cn.l99.wehouse.utils","cn.l99.wehouse.service.impl"})
@MapperScan("cn.l99.wehouse.dao")
@EnableDubbo
@Slf4j
public class WeHouseAdminServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeHouseAdminServiceApplication.class, args);
        try {
            System.in.read();
        } catch (IOException e) {
            log.error("{}", e);
        }
    }
}

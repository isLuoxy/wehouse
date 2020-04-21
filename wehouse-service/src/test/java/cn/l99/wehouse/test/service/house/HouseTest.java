package cn.l99.wehouse.test.service.house;

import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.service.IHouseService;
import cn.l99.wehouse.service.IUserService;
import cn.l99.wehouse.service.WeHouseApplication;
import cn.l99.wehouse.test.service.BashTest;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {WeHouseApplication.class, BashTest.class})
@RunWith(SpringRunner.class)
@Slf4j
@ComponentScan({"cn.l99.wehouse.redis","cn.l99.wehouse.mail","cn.l99.wehouse.utils","cn.l99.wehouse.service.impl"})
@MapperScan("cn.l99.wehouse.dao")
public class HouseTest {

    @Autowired
    IHouseService houseService;

    @Test
    public void findHouse() {
        CommonResult aHouseByHouseId = houseService.getAHouseByHouseId("2147483647", null);
        System.out.println(aHouseByHouseId);
    }
}

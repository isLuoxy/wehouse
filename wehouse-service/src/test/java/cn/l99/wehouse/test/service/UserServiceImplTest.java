package cn.l99.wehouse.test.service;

import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.pojo.vo.UserVo;
import cn.l99.wehouse.service.IUserService;
import cn.l99.wehouse.service.WeHouseApplication;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = WeHouseApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class UserServiceImplTest {

    @Autowired
    IUserService userService;

    @Test
    public void selectUserByUserName() {
        String userName = "root";
        CommonResult commonResult = userService.checkUserName(userName);
        log.info("{}", JSONObject.toJSONString(commonResult));
    }

    @Test
    public void selectUserByUserPhone() {
        String userPhone = "1234567891234";
        CommonResult commonResult = userService.checkUserPhone(userPhone);
        log.info("{}", JSONObject.toJSONString(commonResult));
    }

    @Test
    public void insertUser(){
        UserVo userVo = new UserVo();
        userVo.setUserName("l99");
        userVo.setUserPhone("13104884636");
        userVo.setUserPassword("root");
        CommonResult commonResult = userService.register(userVo);
        log.info("{}",JSONObject.toJSONString(commonResult));
    }
}

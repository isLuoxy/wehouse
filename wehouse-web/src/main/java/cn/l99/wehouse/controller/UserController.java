package cn.l99.wehouse.controller;

import cn.l99.wehouse.common.LoginUtils;
import cn.l99.wehouse.common.LogoutUtils;
import cn.l99.wehouse.pojo.UserCollection;
import cn.l99.wehouse.pojo.baseEnum.ErrorCode;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.pojo.vo.UserStudentAuthenticationVo;
import cn.l99.wehouse.pojo.vo.UserVo;
import cn.l99.wehouse.service.IUserService;
import cn.l99.wehouse.service.redis.IRedisService;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.Login;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    @Reference(version = "${wehouse.service.version}", timeout = 50000)
    IUserService userService;

    @Reference(version = "${wehouse.service.version}")
    IRedisService redisService;

    @PostMapping("/login")
    public Object login(@RequestBody UserVo userVo, HttpServletRequest request) {
        return userService.login(userVo);
    }

    @PostMapping("/register")
    public Object register(@RequestBody UserVo userVo) {
        return userService.register(userVo);
    }

    @GetMapping("/logout")
    public Object logout(HttpServletRequest request) {
        LogoutUtils.clearUserStatus(request, redisService);
        return CommonResult.success();
    }

    @PostMapping("/check/userphone")
    public Object checkUserPhone(@RequestBody UserVo userVo) {
        return userService.checkUserPhone(userVo.getUserPhone());
    }

    @PostMapping("/check/username")
    public Object checkUserName(@RequestBody UserVo userVo) {
        return userService.checkUserName(userVo.getUserName());
    }

    @GetMapping("/code/{phone}")
    public Object sendPhoneCode(@PathVariable("phone") String phone) {
        return userService.sendCode(phone);
    }

    @GetMapping("/i")
    public Object personalCenter(HttpServletRequest request) {
        String userId = LoginUtils.getUserId(request, redisService);
        log.info("用户{}查看个人中心", userId);
        return userService.getPersonalCenterByUserId(userId);
    }

    @GetMapping("/i/collection")
    public Object getPersonalCollection(HttpServletRequest request) {
        String userId = LoginUtils.getUserId(request, redisService);
        return userService.getPersonalCollectionByUserId(userId);
    }

    @PostMapping("/i/collection")
    public Object postPersonalCollection(HttpServletRequest request, @RequestBody UserCollection userCollection) {
        String userId = LoginUtils.getUserId(request, redisService);
        if (StringUtils.isEmpty(userId)) {
            return CommonResult.failure(ErrorCode.NOT_LOGIN);
        }
        userCollection.setUserId(Integer.valueOf(userId));
        return userService.postPersonalCollection(userCollection);
    }

    @PostMapping("/i/stuAuth/mail")
    public Object postSendStuAuthMail(HttpServletRequest request, @RequestBody UserStudentAuthenticationVo userStudentAuthenticationVo) {
        String userId = LoginUtils.getUserId(request, redisService);
        if (StringUtils.isEmpty(userId)) {
            return CommonResult.failure(ErrorCode.NOT_LOGIN);
        }
        CommonResult commonResult = userService.sendStuAuthEmail(userId, userStudentAuthenticationVo);
        return commonResult;
    }

    @GetMapping("/verifyEmailByUrl/uid/{uid}/email/{email}/token/{token}")
    public Object updateUserStudentAuthentication(@PathVariable String uid, @PathVariable String email, @PathVariable String token) {
        CommonResult commonResult = userService.updateUserStudentAuthentication(uid, email, token);
        return commonResult;
    }
}

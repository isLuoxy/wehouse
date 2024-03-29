package cn.l99.wehouse.controller;

import cn.l99.wehouse.pojo.Collection;
import cn.l99.wehouse.pojo.vo.UserVo;
import cn.l99.wehouse.service.IUserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Reference(version = "1.0")
    IUserService userService;

    @PostMapping("/login")
    public Object login(@RequestBody UserVo userVo) {
        return userService.login(userVo);
    }

    @PostMapping("/register")
    public Object register(@RequestBody UserVo userVo) {
        return userService.register(userVo);
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
        String userId = (String) request.getSession().getAttribute("userId");
        return userService.getPersonalCenterByUserId(userId);
    }

    @GetMapping("/i/collection")
    public Object getPersonalCollection(HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        return userService.getPersonalCollectionByUserId(userId);
    }

    @PostMapping("/i/collection")
    public Object postPersonalCollection(HttpServletRequest request, @RequestBody Collection collection) {
        String userId = (String) request.getSession().getAttribute("userId");
        collection.setUserId(Integer.valueOf(userId));
        return userService.postPersonalCollection(collection);
    }
}

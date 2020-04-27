package cn.l99.wehouse.admin.controller;

import cn.l99.wehouse.pojo.Page;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.pojo.vo.AdminVo;
import cn.l99.wehouse.pojo.vo.HouseMsgVo;
import cn.l99.wehouse.pojo.vo.UserMsgVo;
import cn.l99.wehouse.service.admin.IAdminService;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Slf4j
public class AdminController {

    @Reference(version = "${wehouse.service.version}")
    IAdminService adminService;


    @PostMapping("/login")
    public Object login(@RequestBody AdminVo adminVo, HttpServletRequest request) {
        CommonResult adminByAdminName = adminService.getAdminByAdminName(adminVo);
        if (CommonResult.success().getErrorCode() == adminByAdminName.getErrorCode()) {
            // 登录成功，本地会话记录管理员登录信息
            HttpSession session = request.getSession();
            // 这里为了简单直接将标识存储到会话中
            session.setAttribute("hasLogin", adminVo.getAdminName());
        }
        return adminByAdminName;
    }


    @PostMapping("/root/user")
    public Object updateUser(@RequestBody UserMsgVo userMsgVo) {
        log.info("管理员更新用户信息:{}", userMsgVo);
        CommonResult result = adminService.updateUser(userMsgVo);
        return result;
    }

    @GetMapping("/root/user")
    public Object getUser(@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber) {
        Page page = new Page(pageSize, pageNumber);
        CommonResult result = adminService.getAllUser(page);
        return result;
    }

    @DeleteMapping("/root/user")
    public Object deleteUser(@RequestBody UserMsgVo userMsgVo) {
        log.info("管理员删除用户：{},将用户状态改为禁用", userMsgVo.getUserId());
        return adminService.deleteUser(userMsgVo);
    }


    @GetMapping("/root/house")
    public Object getHouse(@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber) {
        Page page = new Page(pageSize, pageNumber);
        CommonResult result = adminService.getAllHouse(page);
        return result;
    }

    @PostMapping("/root/house")
    public Object updateHouse(@RequestBody HouseMsgVo houseMsgVo) {
        return adminService.updateHouse(houseMsgVo);
    }
//
//    @DeleteMapping("/root/user")
//    public Object deleteHouse(@RequestBody HouseMsgVo houseMsgVo) {
//        log.info("管理员删除房源：{},将用户状态改为逻辑删除", houseMsgVo.getHouseId());
//        return adminService.deleteHouse(houseMsgVo);
//    }
}

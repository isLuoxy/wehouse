package cn.l99.wehouse.common;

import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.service.redis.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 注销工具类
 */
@Slf4j
public class LogoutUtils {

    public static void clearUserStatus(HttpServletRequest request, IRedisService redisService) {
        String token = CookieUtils.getCookieValue(request, "token");
        if (StringUtils.isEmpty(token)) {
            // 用户没有 cookie，没有登录，返回值为null
            // 正常情况不进入该分支
            return;
        }
        redisService.delete(token);
        log.info("用户token:{} 注销", token);
    }
}

package cn.l99.wehouse.common;

import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.service.redis.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录工具类
 *
 * @author L99
 */
@Slf4j
public class LoginUtils {

    public static boolean hasLoginAndReturnBool(HttpServletRequest request, HttpServletResponse response, IRedisService redisService) {
        String s = hasLoginAndReturnString(request, response, redisService);
        if(StringUtils.isEmpty(s)){
            return false;
        }
        return true;
    }

    private static boolean correctLogin(HttpServletRequest request, String token, IRedisService redisService) {
        HttpSession session = request.getSession();
        // 会话中获取登录标识
        Object loginStatus = session.getAttribute(token);
        if (!StringUtils.isEmpty(loginStatus)) {
            return true;
        }
        CommonResult redisResult = redisService.getValueIfExist(token);
        if (redisResult != null) {
            // 说明此时 session 不存在 token，而 redis 存在 token，所以也是登录成功的，那么此时需要把 token放入 session 中 , 值为用户id
            // 第一次登录后会进入该分支
            session.setAttribute(token, redisResult.getData());
            return true;
        }
        return false;
    }

    private static void renew(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, IRedisService redisService) {
        Integer currAge = CookieUtils.getMaxAge(request, cookieName);
        if (currAge == null) {
            return;
        }
        // 小于10分钟时进行续期
        if (currAge.intValue() < 600) {
            // 进行续期
            log.info("对用户id{}进行续期", cookieValue);
            CookieUtils.setMaxAge(request, response, cookieName, cookieValue, 1800);
            redisService.expire(cookieValue, 1800);
        }
    }

    /**
     * 判断是否登录并返回用户标识，这里的用户标识为用户id
     * @param request
     * @param response
     * @param redisService
     * @return 如果已登录，则返回用户id；否则返回 null
     */
    public static String hasLoginAndReturnString(HttpServletRequest request, HttpServletResponse response, IRedisService redisService) {
        String token = CookieUtils.getCookieValue(request, "token");
        if (StringUtils.isEmpty(token)) {
            // 用户没有 cookie，没有登录，返回值为null
            return null;
        }
        // 判断登录状态是否正确
        boolean correctLogin = correctLogin(request, token, redisService);
        if (correctLogin) {
            // 判断是否需要续期并自动续期
            renew(request, response, "token", token, redisService);
            return token;
        }
        return null;
    }
}

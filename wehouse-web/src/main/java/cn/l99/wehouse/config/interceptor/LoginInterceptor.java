package cn.l99.wehouse.config.interceptor;

import cn.l99.wehouse.pojo.baseEnum.ErrorCode;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.service.IUserService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * 拦截器配置
 *
 * @author L99
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Reference(version = "1.0")
    IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("进去拦截器，拦截路径:{}",request.getRequestURI());
        // 检查登录状态
        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                // 先通过 session 查找是否存在对应的登录标识
                String token = cookie.getValue();
                Object loginStatus = session.getAttribute(token);
                if (loginStatus != null) {
                    // 客户端存在token，并且session也存在，此时通过，避免查询 redis
                    return true;
                }

                // 此时 session 中不存在 token，进一步从 redis 中查找是否存在该 token
                CommonResult redisResult = userService.getValueIfExist(token);
                if (redisResult != null) {
                    // 说明此时 session 不存在 token，而 redis 存在 token，所以也是登录成功的，那么此时需要把 token放入 session 中 , 值为用户id
                    session.setAttribute(token, redisResult.getData());
                    session.setAttribute("userId", redisResult.getData());
                    return true;
                }
            }
        }

        /* 两种情况代码运行到这里
         * 1、客户端token存在，但是session和redis都不存在 token，这里以防token重放要重新登录
         * 2、客户端token不存在，此时没有查询session和redis，此时需要重新登录，那么清空对应session和 redis的 token值
         */
        // 由于重新登录是新的会话了，所以直接注销session,而 redis 中有过期机制，会自动过期
        session.invalidate();
        useJsonResponse(response, CommonResult.failure(ErrorCode.NOT_LOGIN));
        return false;
    }


    /**
     * 返回请求拦截后的响应
     *
     * @param response
     * @param object   响应信息
     */
    private void useJsonResponse(HttpServletResponse response, Object object) {
        String content = JSONObject.toJSONString(object);
        try {
            response.reset();
            response.setContentType("application/json");
            response.setHeader("Cache-Control", "no-store");
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write(content);
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

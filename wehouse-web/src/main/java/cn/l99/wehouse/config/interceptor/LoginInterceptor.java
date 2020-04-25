package cn.l99.wehouse.config.interceptor;

import cn.l99.wehouse.common.LoginUtils;
import cn.l99.wehouse.pojo.baseEnum.ErrorCode;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.service.redis.IRedisService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 拦截器配置
 *
 * @author L99
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Reference(version = "${wehouse.service.version}")
    IRedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("进去拦截器，拦截路径:{}", request.getRequestURI());

        // 检查登录状态
        boolean hasLogin = LoginUtils.hasLoginAndReturnBool(request, response, redisService);

        if (hasLogin) {
            return true;
        }

        /* 两种情况代码运行到这里
         * 1、客户端token存在，但是session和redis都不存在 token，这里以防token重放要重新登录
         * 2、客户端token不存在，此时没有查询session和redis，此时需要重新登录，那么清空对应session和 redis的 token值
         */
        // 由于重新登录是新的会话了，所以直接注销session,而 redis 中有过期机制，会自动过期
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

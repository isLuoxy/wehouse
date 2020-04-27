package cn.l99.wehouse.admin.config.interceptor;

import cn.l99.wehouse.pojo.baseEnum.ErrorCode;
import cn.l99.wehouse.pojo.response.CommonResult;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("进去拦截器，拦截路径:{}", request.getRequestURI());

        // 检查登录状态
        HttpSession session = request.getSession();
        if (!StringUtils.isEmpty(session.getAttribute("hasLogin"))) {
            return true;
        }

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

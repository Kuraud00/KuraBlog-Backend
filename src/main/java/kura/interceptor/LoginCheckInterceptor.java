package kura.interceptor;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kura.pojo.Result;
import kura.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(request.getRequestURL().toString() + "调用拦截器");

        // 如果是OPTIONS请求，则放行
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            log.info("OPTIONS请求，直接放行");
            return true;
        }

        String JwtToken = request.getHeader("Authorization");

        if(!StringUtils.hasLength(JwtToken)){
            log.info("请求头中不存在Authorization，属于未登录");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(JSONObject.toJSONString(Result.error("NOT_LOGIN")));
            return false;
        }

        try{
            jwtUtils.parseJWT(JwtToken);
        }catch (Exception e){
            e.printStackTrace();
            log.info("解析失败，令牌无效");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(JSONObject.toJSONString(Result.error("EXPIRED_TOKEN")));
            return false;
        }

        log.info("令牌有效");
        return true;
    }
}

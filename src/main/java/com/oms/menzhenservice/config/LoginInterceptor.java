package com.oms.menzhenservice.config;

import com.oms.menzhenservice.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行 OPTIONS 请求 (跨域预检)
        if ("OPTIONS".equals(request.getMethod())) return true;

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = jwtUtil.parseToken(token);
                // 将用户信息存入 request，方便 Controller 获取
                request.setAttribute("currUser", claims.getSubject());
                request.setAttribute("currRole", claims.get("role"));
                request.setAttribute("currId", claims.get("userId"));
                return true;
            } catch (Exception e) {
                // Token 无效
            }
        }
        response.setStatus(401);
        response.getWriter().write("Unauthorized");
        return false;
    }
}
package com.campus.activity.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    public LoginInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            response.setStatus(401);
            response.getWriter().write("{\"code\":401,\"message\":\"未登录\",\"data\":null}");
            return false;
        }

        try {
            Claims claims = jwtUtil.parseToken(auth.substring(7));
            request.setAttribute("userId", claims.get("userId", Long.class));
            request.setAttribute("studentId", claims.getSubject());
            request.setAttribute("role", claims.get("role", String.class));
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            response.getWriter().write("{\"code\":401,\"message\":\"token无效\",\"data\":null}");
            return false;
        }
    }
}

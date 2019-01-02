package com.ushakov.movieland.web.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Order
@Service
public class CorsInterceptor extends HandlerInterceptorAdapter {
    @Value("${cors.origin:http://localhost:3000}")
    private String origin;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Request-Headers", "content-type,uuid");
        response.setHeader("Access-Control-Allow-Headers", "content-type,uuid");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        return true;
    }
}
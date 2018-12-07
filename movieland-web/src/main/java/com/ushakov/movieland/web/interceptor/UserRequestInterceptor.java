package com.ushakov.movieland.web.interceptor;

import com.ushakov.movieland.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class UserRequestInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private SecurityService securityService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        MDC.put("requestId", UUID.randomUUID().toString());

        String uri = request.getRequestURI();
        if (uri.equalsIgnoreCase("/v1/login")) {
            return true;
        }

        String uuid = request.getHeader("uuid");

        String email = securityService.getEmail(uuid);

        if (uuid == null || email == null) {
            logger.warn("The user is not logged on!");
            HttpStatus forbidden = HttpStatus.FORBIDDEN;
            response.setStatus(forbidden.value());
            return false;
        }

        MDC.put("user", email);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) {
        MDC.put("user", "");
        MDC.put("requestId", "");
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}

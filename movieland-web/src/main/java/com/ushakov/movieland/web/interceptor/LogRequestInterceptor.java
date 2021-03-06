package com.ushakov.movieland.web.interceptor;

import com.ushakov.movieland.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class LogRequestInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private SecurityService securityService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        MDC.put("requestId", UUID.randomUUID().toString());

        String uri = request.getRequestURI();
        if (!uri.equalsIgnoreCase("/v1/login")) {


            String uuid = request.getHeader("uuid");

            if (uuid == null) {
                uuid = "NULL";
            }

            logger.debug("uuid: {}", uuid);

            String email = securityService.getEmail(uuid);

            if (email == null) {
                email = "READONLY";
            }

            logger.debug("email: {}", email);

            MDC.put("user", email);
        }

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

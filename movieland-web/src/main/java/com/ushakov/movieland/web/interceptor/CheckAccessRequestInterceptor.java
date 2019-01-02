package com.ushakov.movieland.web.interceptor;

import com.ushakov.movieland.common.UserRole;
import com.ushakov.movieland.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Service
public class CheckAccessRequestInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private SecurityService securityService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uri = request.getRequestURI();
        if (uri.equalsIgnoreCase("/v1/login")) {
            return true;
        }

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (method.isAnnotationPresent(ProtectedBy.class)) {
                String uuid = request.getHeader("uuid");

                if (uuid != null) {
                    UserRole userRole = securityService.getUserRole(uuid);

                    logger.debug("User role: {}", userRole);

                    ProtectedBy protectedBy = method.getAnnotation(ProtectedBy.class);

                    UserRole[] userRoles = protectedBy.value();

                    for (UserRole role : userRoles) {
                        if (userRole == role) {
                            UserHandler.setCurrentUser(securityService.getUser(uuid));
                            return true;
                        }
                    }
                }

                logger.warn("The user does not have permissions to invoke {}!", method.getName());
                HttpStatus forbidden = HttpStatus.FORBIDDEN;
                response.setStatus(forbidden.value());
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) {
        UserHandler.setCurrentUser(null);
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}

package com.ushakov.movieland.web.configuration;

import com.ushakov.movieland.web.interceptor.UserRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    UserRequestInterceptor userRequestInterceptor() {
        return new UserRequestInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userRequestInterceptor());
    }

}

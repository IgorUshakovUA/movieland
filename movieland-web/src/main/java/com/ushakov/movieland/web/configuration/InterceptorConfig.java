package com.ushakov.movieland.web.configuration;

import com.ushakov.movieland.web.interceptor.CheckAccessRequestInterceptor;
import com.ushakov.movieland.web.interceptor.LogRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    LogRequestInterceptor userRequestInterceptor() {
        return new LogRequestInterceptor();
    }

    @Bean
    CheckAccessRequestInterceptor checkAccessRequestInterceptor() { return new CheckAccessRequestInterceptor(); }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userRequestInterceptor());
        registry.addInterceptor(checkAccessRequestInterceptor());
    }

}

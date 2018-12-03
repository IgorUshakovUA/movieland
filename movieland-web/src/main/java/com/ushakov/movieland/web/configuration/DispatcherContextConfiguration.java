package com.ushakov.movieland.web.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.ushakov.movieland.web.controller"})
public class DispatcherContextConfiguration {
}

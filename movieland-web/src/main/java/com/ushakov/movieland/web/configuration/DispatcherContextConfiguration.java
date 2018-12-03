package com.ushakov.movieland.web.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.ushakov.movieland.web.controller"})
public class DispatcherContextConfiguration {
}

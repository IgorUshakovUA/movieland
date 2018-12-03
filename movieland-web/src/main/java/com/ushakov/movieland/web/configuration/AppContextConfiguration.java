package com.ushakov.movieland.web.configuration;

import com.ushakov.movieland.common.*;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.ushakov.movieland.service", "com.ushakov.movieland.dao"})
@EnableAsync
@EnableScheduling
public class AppContextConfiguration {
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        PropertySourcesPlaceholderConfigurer bean = new PropertySourcesPlaceholderConfigurer();

        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));

        bean.setProperties(yaml.getObject());

        return bean;
    }

    @Bean
    public AppProperties appProperties() {
        return new AppProperties();
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("org.postgresql.Driver");
        basicDataSource.setUrl("jdbc:postgresql://127.0.0.1:5432/movieland");
        basicDataSource.setUsername("app_owner");
        basicDataSource.setPassword("app_owner");

        return basicDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CustomEditorConfigurer customEditorConfigurer() {
        CustomEditorConfigurer bean = new CustomEditorConfigurer();

        Map<Class<?>, Class<? extends PropertyEditor>> customEditors = new HashMap<>();
        customEditors.put(SortField.class, SortFieldConverter.class);
        customEditors.put(SortType.class, SortTypeConverter.class);
        customEditors.put(Currency.class, CurrencyConverter.class);

        bean.setCustomEditors(customEditors);

        return bean;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor bean = new ThreadPoolTaskExecutor();

        AppProperties appProperties = appProperties();

        bean.setCorePoolSize(appProperties.getExecutorPoolSize());
        bean.setMaxPoolSize(appProperties.getExecutorPoolSize() * 2);
        bean.setQueueCapacity(appProperties.getExecutorQueueCapacity());

        return bean;
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler bean = new ThreadPoolTaskScheduler();

        AppProperties appProperties = appProperties();

        bean.setPoolSize(appProperties.getSchedulerPoolSize());

        return bean;
    }
}

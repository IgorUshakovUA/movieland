package com.ushakov.movieland.web.configuration;

import com.ushakov.movieland.common.*;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
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

import javax.sql.DataSource;
import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
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
    public DataSource dataSource(@Value("${jdbc.driver}") String jdbcDriver,
                                 @Value("${jdbc.url}") String jdbcUrl,
                                 @Value("${jdbc.username}") String jdbcUsername,
                                 @Value("${jdbc.password}") String jdbcPassword
    ) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(jdbcDriver);
        basicDataSource.setUrl(jdbcUrl);
        basicDataSource.setUsername(jdbcUsername);
        basicDataSource.setPassword(jdbcPassword);

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
    public TaskExecutor taskExecutor(@Value("${my.job.executor.queue.capacity:10}") int executorQueueCapacity,
                                     @Value("${my.job.executor.pool.size:5}") int executorPoolSize) {

        ThreadPoolTaskExecutor bean = new ThreadPoolTaskExecutor();

        bean.setCorePoolSize(executorPoolSize);
        bean.setMaxPoolSize(executorPoolSize * 2);
        bean.setQueueCapacity(executorQueueCapacity);

        return bean;
    }

    @Bean
    public TaskScheduler taskScheduler(@Value("${my.job.scheduler.pool.size:5}") int schedulerPoolSize) {
        ThreadPoolTaskScheduler bean = new ThreadPoolTaskScheduler();

        bean.setPoolSize(schedulerPoolSize);

        return bean;
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newCachedThreadPool();
    }
}

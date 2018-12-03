package com.ushakov.movieland.web.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class TestConfiguration {
    @Value("classpath:db/schema.sql")
    private Resource schemaScript;

    @Value("classpath:db/test-data.sql")
    private Resource dataScript;


    @Bean
    public DataSource dataSource(){
        DataSource dataSource = createDataSource();
        DatabasePopulatorUtils.execute(databasePopulator(), dataSource);
        return dataSource;
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(schemaScript);
        populator.addScript(dataScript);
        return populator;
    }

    private SimpleDriverDataSource createDataSource() {
        SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
        simpleDriverDataSource.setDriverClass(org.h2.Driver.class);
        simpleDriverDataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
        simpleDriverDataSource.setUsername("");
        simpleDriverDataSource.setPassword("");
        return simpleDriverDataSource;
    }
}

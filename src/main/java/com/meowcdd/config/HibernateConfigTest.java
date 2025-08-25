package com.meowcdd.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@Slf4j
@Profile("!test") // Exclude from test profile
public class HibernateConfigTest {

    @Bean
    public CommandLineRunner testDatabaseConnection(@Autowired DataSource dataSource) {
        return args -> {
            log.info("Testing database connection and Hibernate configuration...");
            
            try (Connection connection = dataSource.getConnection()) {
                log.info("Database connection successful!");
                log.info("Database: {}", connection.getMetaData().getDatabaseProductName());
                log.info("Driver: {}", connection.getMetaData().getDriverName());
                log.info("URL: {}", connection.getMetaData().getURL());
            } catch (SQLException e) {
                log.error("Database connection failed: {}", e.getMessage(), e);
                throw new RuntimeException("Database connection test failed", e);
            }
            
            log.info("Hibernate configuration test completed successfully!");
        };
    }
}

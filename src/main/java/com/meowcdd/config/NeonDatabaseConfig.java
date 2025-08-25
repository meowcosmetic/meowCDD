package com.meowcdd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing
@EnableJpaRepositories(
    basePackages = "com.meowcdd.repository.neon"
)
public class NeonDatabaseConfig {
    // Let Spring Boot handle all JPA configuration automatically
    // This minimal configuration should avoid transaction issues
}

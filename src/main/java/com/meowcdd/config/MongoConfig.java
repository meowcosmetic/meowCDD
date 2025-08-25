package com.meowcdd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@Profile("!docker") // Only enable MongoDB auditing when NOT using Docker profile
@EnableMongoAuditing
public class MongoConfig {
    // Configuration để enable MongoDB auditing
    // Cho phép @CreatedDate và @LastModifiedDate hoạt động tự động
    // Disabled for Docker profile to avoid MongoDB dependencies
}

package com.meowcdd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class MongoConfig {
    // Configuration để enable MongoDB auditing
    // Cho phép @CreatedDate và @LastModifiedDate hoạt động tự động
}

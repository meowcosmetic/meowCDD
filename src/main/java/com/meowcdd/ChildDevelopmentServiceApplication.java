package com.meowcdd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.meowcdd.repository.mongo")
public class ChildDevelopmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChildDevelopmentServiceApplication.class, args);
    }
}

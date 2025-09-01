package com.meowcdd.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
@Slf4j
public class DatabaseMigrationConfig {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    public CommandLineRunner runMigrations() {
        return args -> {
            log.info("Running database migrations...");
            
            try {
                // Run the migration script
                runMigrationScript();
                log.info("Database migrations completed successfully");
            } catch (Exception e) {
                log.error("Error running database migrations: ", e);
                throw e;
            }
        };
    }

    private void runMigrationScript() {
        try {
            // Read the migration script
            String migrationScript = readMigrationScript();
            
            // Execute the migration
            jdbcTemplate.execute(migrationScript);
            
        } catch (Exception e) {
            log.error("Error executing migration script: ", e);
            throw new RuntimeException("Failed to run database migration", e);
        }
    }

    private String readMigrationScript() throws IOException {
        // Read the migration script from the file
        return new String(Files.readAllBytes(Paths.get("check_and_create_table.sql")), StandardCharsets.UTF_8);
    }
}

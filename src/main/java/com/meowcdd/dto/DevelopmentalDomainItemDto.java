package com.meowcdd.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DevelopmentalDomainItemDto {
    private Long id;
    private UUID domainId;
    private String domainName;
    private Map<String, Object> title; // JSON object
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}



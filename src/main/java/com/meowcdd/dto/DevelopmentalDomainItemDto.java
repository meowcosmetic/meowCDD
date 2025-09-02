package com.meowcdd.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DevelopmentalDomainItemDto {
    private Long id;
    private UUID domainId;
    private String domainName;
    private String code;
    private String title; // JSON string
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}



package com.meowcdd.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DevelopmentalItemCriteriaDto {
    private Long id;
    // Accept both numeric and UUID strings from FE in this field
    private String itemId;
    private UUID itemPublicId; // FE sends this as UUID string
    private Map<String, Object> description; // JSON object
    private Integer minAgeMonths;
    private Integer maxAgeMonths;
    private Integer level;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}



package com.meowcdd.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterventionMethodDto {

    private Long id;
    private String code;
    
    private JsonNode displayedName;
    
    private JsonNode description;
    
    private Long groupId; // ID của InterventionMethodGroup
    private Integer minAgeMonths;
    private Integer maxAgeMonths;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


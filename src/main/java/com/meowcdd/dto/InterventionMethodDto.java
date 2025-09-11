package com.meowcdd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterventionMethodDto {

    private Long id;
    private String code;
    private String displayedName;
    private String description;
    private Integer minAgeMonths;
    private Integer maxAgeMonths;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


package com.meowcdd.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DevelopmentalAssessmentResultDto {
    private Long id;
    private Long childId;
    private Long criteriaId;
    private Long programId;
    private String status; // ACHIEVED | NOT_ACHIEVED | IN_PROGRESS
    private String notes;
    private LocalDate assessedAt;
    private String assessor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}



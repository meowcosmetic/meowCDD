package com.meowcdd.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChildTestRecordNeonDto {
    
    private Long id;
    
    @NotNull(message = "Child ID is required")
    private String childId;
    
    @NotNull(message = "Test ID is required")
    private String testId;
    
    private LocalDateTime testDate;
    
    private String testResult;
    
    private Double score;
    
    private String interpretation;
    
    private String recommendations;
    
    private String notes;
    
    private String status;
    
    private String administeredBy;
    
    private String location;
    
    private Integer durationMinutes;
    
    private String environmentNotes;
    
    private String childBehaviorNotes;
    
    private String parentFeedback;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}

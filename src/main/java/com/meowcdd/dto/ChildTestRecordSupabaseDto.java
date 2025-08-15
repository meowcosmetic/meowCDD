package com.meowcdd.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChildTestRecordSupabaseDto {
    
    private Long id;
    
    @NotBlank(message = "External ID is required")
    private String externalId;
    
    @NotNull(message = "Child ID is required")
    private Long childId;
    
    @NotNull(message = "Test ID is required")
    private Long testId;
    
    @NotNull(message = "Test type is required")
    private String testType;
    
    private LocalDateTime testDate;
    
    private LocalDateTime startTime;
    
    private LocalDateTime endTime;
    
    private String status;
    
    private Double totalScore;
    
    private Double maxScore;
    
    private Double percentageScore;
    
    private String resultLevel;
    
    private String interpretation;
    
    private String questionAnswers;
    
    private Integer correctAnswers;
    
    private Integer totalQuestions;
    
    private Integer skippedQuestions;
    
    private String notes;
    
    private String environment;
    
    private String assessor;
    
    private Boolean parentPresent;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}

package com.meowcdd.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChildTestRecordWithCategoryDto {
    
    private Long id;
    private String childId;
    private String testId;
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
    
    // Thông tin từ bảng cdd_tests
    private String testCategory;
    private String testName;
    private String testVersion;
    private String testDescription;
    private Integer minAgeMonths;
    private Integer maxAgeMonths;
    private Integer estimatedDuration;
}

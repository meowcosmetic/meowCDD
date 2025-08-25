package com.meowcdd.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DevelopmentDisorderDto {
    
    private String id;
    
    @NotBlank(message = "Child ID is required")
    private String childId;
    
    @NotBlank(message = "Disorder type is required")
    private String disorderType;
    
    @NotBlank(message = "Severity is required")
    private String severity;
    
    private LocalDateTime diagnosisDate;
    
    private String diagnosedBy;
    
    private String diagnosisNotes;
    
    private List<SymptomDto> symptoms;
    
    private List<AssessmentDto> assessments;
    
    private List<TreatmentDto> treatments;
    
    private String currentStatus;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SymptomDto {
        private String name;
        private String description;
        private String severity;
        private LocalDateTime firstObserved;
        private String notes;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AssessmentDto {
        private String type;
        private LocalDateTime assessmentDate;
        private String assessor;
        private Double score;
        private String result;
        private String notes;
        private List<String> recommendations;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TreatmentDto {
        private String type;
        private String description;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private String provider;
        private String status;
        private String effectiveness;
        private String notes;
    }
}

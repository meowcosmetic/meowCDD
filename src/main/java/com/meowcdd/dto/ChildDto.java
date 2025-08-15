package com.meowcdd.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChildDto {
    
    private Long id;
    
    @NotBlank(message = "External ID is required")
    private String externalId;
    
    @NotBlank(message = "Full name is required")
    private String fullName;
    
    @NotNull(message = "Gender is required")
    private String gender;
    
    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;
    
    private Integer currentAgeMonths;
    
    private Boolean isPremature;
    
    private Integer gestationalWeek;
    
    private Integer birthWeightGrams;
    
    private String specialMedicalConditions;
    
    private String developmentalDisorderDiagnosis;
    
    private Boolean hasEarlyIntervention;
    
    private String earlyInterventionDetails;
    
    private String primaryLanguage;
    
    private String familyDevelopmentalIssues;
    
    private Double height;
    
    private Double weight;
    
    private String bloodType;
    
    private String allergies;
    
    private String medicalHistory;
    
    private LocalDateTime registrationDate;
    
    private String status;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}

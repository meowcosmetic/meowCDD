package com.meowcdd.dto;

import com.meowcdd.document.AssessmentTest;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentTestDto {

    private String id;

    @NotEmpty(message = "Test names in at least one language are required")
    private Map<String, String> names; // Language code -> Test name

    @NotEmpty(message = "Test descriptions in at least one language are required")
    private Map<String, String> descriptions; // Language code -> Test description

    private Map<String, String> instructions; // Language code -> Test instructions

    @NotBlank(message = "Test code is required")
    private String testCode; // Unique test identifier

    @NotBlank(message = "Test category is required")
    private String category; // Category of test

    @NotBlank(message = "Target age group is required")
    private String targetAgeGroup; // Target age group

    @NotNull(message = "Estimated duration is required")
    @Min(value = 1, message = "Estimated duration must be at least 1 minute")
    @Max(value = 480, message = "Estimated duration cannot exceed 8 hours")
    private Integer estimatedDuration; // Estimated duration in minutes

    @NotBlank(message = "Difficulty level is required")
    private String difficultyLevel; // EASY, MODERATE, DIFFICULT

    @NotEmpty(message = "Test sections are required")
    private List<AssessmentTest.TestSection> sections; // Test sections/questions

    private Map<String, Object> scoringCriteria; // Scoring criteria and interpretation
    private Map<String, Object> cutoffScores; // Cutoff scores for different severity levels

    @NotBlank(message = "Administration type is required")
    private String administrationType; // INDIVIDUAL, GROUP, PARENT_REPORT, OBSERVATION

    private String requiredQualifications; // Required qualifications for administrators
    private List<String> requiredMaterials; // List of required materials
    private Map<String, String> administrationNotes; // Language code -> Administration notes

    private String validity; // Information about test validity
    private String reliability; // Information about test reliability
    private String normativeData; // Information about normative data

    @NotBlank(message = "Test status is required")
    private String status; // ACTIVE, INACTIVE, DRAFT, DEPRECATED

    @NotBlank(message = "Test version is required")
    private String version; // Test version

    private String lastUpdatedBy; // Who last updated the test
}


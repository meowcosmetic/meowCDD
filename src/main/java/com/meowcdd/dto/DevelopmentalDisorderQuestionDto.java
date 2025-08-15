package com.meowcdd.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DevelopmentalDisorderQuestionDto {

    private String id;

    @NotEmpty(message = "Names in at least one language are required")
    private Map<String, String> names; // Language code -> Name

    @NotEmpty(message = "Main symptoms in at least one language are required")
    private Map<String, String> mainSymptoms; // Language code -> Main symptoms

    @Min(value = 0, message = "Minimum detection age in months must be 0 or greater")
    @Max(value = 60, message = "Maximum detection age in months must be 60 or less")
    private Integer detectionAgeMinMonths;

    @Min(value = 0, message = "Maximum detection age in months must be 0 or greater")
    @Max(value = 60, message = "Maximum detection age in months must be 60 or less")
    private Integer detectionAgeMaxMonths;

    @Min(value = 0, message = "Minimum detection age in years must be 0 or greater")
    @Max(value = 18, message = "Maximum detection age in years must be 18 or less")
    private Integer detectionAgeMinYears;

    @Min(value = 0, message = "Maximum detection age in years must be 0 or greater")
    @Max(value = 18, message = "Maximum detection age in years must be 18 or less")
    private Integer detectionAgeMaxYears;

    @NotEmpty(message = "Screening questions in at least one language are required")
    private Map<String, String> screeningQuestions; // Language code -> Screening questions
}

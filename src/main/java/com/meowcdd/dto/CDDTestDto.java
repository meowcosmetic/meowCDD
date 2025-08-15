package com.meowcdd.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CDDTestDto {
    private String id;
    private String assessmentCode;
    private Map<String, String> names;
    private Map<String, String> descriptions;
    private Map<String, String> instructions;
    private String category;
    private String targetAgeGroup;
    private Integer minAgeMonths;
    private Integer maxAgeMonths;
    private String status;
    private String version;
    private Integer estimatedDuration;
    private List<YesNoQuestionDto> questions;
    private ScoringCriteriaDto scoringCriteria;
    private String administrationType;
    private String requiredQualifications;
    private List<String> requiredMaterials;
    private Map<String, String> notes;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class YesNoQuestionDto {
        private String questionId;
        private Integer questionNumber;
        private Map<String, String> questionTexts;
        private String category;
        private Integer weight;
        private Boolean required;
        private Map<String, String> hints;
        private Map<String, String> explanations;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ScoringCriteriaDto {
        private Integer totalQuestions;
        private Integer yesScore;
        private Integer noScore;
        private Map<String, ScoreRangeDto> scoreRanges;
        private String interpretation;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ScoreRangeDto {
        private Integer minScore;
        private Integer maxScore;
        private String level;
        private Map<String, String> descriptions;
        private String recommendation;
    }
}

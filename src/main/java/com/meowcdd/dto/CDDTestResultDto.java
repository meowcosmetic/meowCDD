package com.meowcdd.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CDDTestResultDto {
    private String id;
    private String childId;
    private String assessmentId;
    private String assessmentCode;
    private String administratorId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer durationMinutes;
    private List<QuestionAnswerDto> answers;
    private Integer totalScore;
    private String riskLevel;
    private String interpretation;
    private String notes;
    private String parentComments;
    private String status;
    private Map<String, Object> additionalData;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class QuestionAnswerDto {
        private String questionId;
        private Integer questionNumber;
        private String answer; // YES, NO, SKIPPED
        private Integer score;
        private String notes;
        private LocalDateTime answeredAt;
    }
}

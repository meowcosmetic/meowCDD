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
public class TrackingQuestionNeonDto {
    
    private Long id;
    private String category; // JSON string
    private String domain; // Ví dụ: "communication", "social_interaction", "behavior_emotion", "cognition", "independence"
    private String ageRange; // Độ tuổi áp dụng, ví dụ "2-6"
    private String frequency; // Tần suất, ví dụ "daily"
    private String context; // JSON string
    private String question; // JSON string
    private String options; // JSON string
    private String note; // Ghi chú phụ huynh/giáo viên
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

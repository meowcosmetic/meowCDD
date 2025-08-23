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
public class TrackingQuestionDto {
    
    private Long id;
    private Map<String, String> category; // { "vi": "Giao tiếp", "en": "Communication" }
    private String domain; // Ví dụ: "communication", "social_interaction", "behavior_emotion", "cognition", "independence"
    private String ageRange; // Độ tuổi áp dụng, ví dụ "2-6"
    private String frequency; // Tần suất, ví dụ "daily"
    private List<String> context; // ["home", "school"]
    private Map<String, String> question; // { "vi": "...", "en": "..." }
    private List<QuestionOptionDto> options; // Danh sách lựa chọn (song ngữ + điểm số)
    private String note; // Ghi chú phụ huynh/giáo viên
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class QuestionOptionDto {
        private Map<String, String> text; // { "vi": "...", "en": "..." }
        private Integer score; // Điểm số
    }
}

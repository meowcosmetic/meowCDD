package com.meowcdd.document;

import com.meowcdd.document.base.BaseDocument;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Document(collection = "progress_reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProgressReport extends BaseDocument {
    
    @Id
    private String id;
    
    @Indexed
    private String childExternalId; // Liên kết với Child entity
    
    private LocalDateTime reportDate;
    
    private String reporter; // Người báo cáo
    
    private String reportType; // MONTHLY, QUARTERLY, ANNUAL, SPECIAL
    
    private Map<String, Object> physicalDevelopment; // Phát triển thể chất
    
    private Map<String, Object> cognitiveDevelopment; // Phát triển nhận thức
    
    private Map<String, Object> socialDevelopment; // Phát triển xã hội
    
    private Map<String, Object> emotionalDevelopment; // Phát triển cảm xúc
    
    private Map<String, Object> languageDevelopment; // Phát triển ngôn ngữ
    
    private List<Milestone> milestones; // Các cột mốc đạt được
    
    private List<Challenge> challenges; // Các thách thức gặp phải
    
    private List<Goal> goals; // Mục tiêu tiếp theo
    
    private String overallProgress; // Tiến độ tổng thể
    
    private String recommendations; // Khuyến nghị
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Milestone {
        private String category; // PHYSICAL, COGNITIVE, SOCIAL, EMOTIONAL, LANGUAGE
        private String description;
        private LocalDateTime achievedDate;
        private String notes;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Challenge {
        private String category;
        private String description;
        private String severity; // MILD, MODERATE, SEVERE
        private String currentApproach;
        private String notes;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Goal {
        private String category;
        private String description;
        private LocalDateTime targetDate;
        private String priority; // HIGH, MEDIUM, LOW
        private String status; // PENDING, IN_PROGRESS, ACHIEVED
    }
}

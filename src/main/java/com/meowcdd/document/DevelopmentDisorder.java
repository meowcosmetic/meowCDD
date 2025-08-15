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

@Document(collection = "development_disorders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class DevelopmentDisorder extends BaseDocument {
    
    @Id
    private String id;
    
    @Indexed
    private String childExternalId; // Liên kết với Child entity
    
    private String disorderType; // Loại rối loạn: AUTISM, ADHD, DOWN_SYNDROME, etc.
    
    private String severity; // Mức độ: MILD, MODERATE, SEVERE
    
    private LocalDateTime diagnosisDate;
    
    private String diagnosedBy; // Bác sĩ chẩn đoán
    
    private String diagnosisNotes; // Ghi chú chẩn đoán
    
    private List<Symptom> symptoms; // Danh sách triệu chứng
    
    private List<Assessment> assessments; // Các đánh giá
    
    private List<Treatment> treatments; // Các phương pháp điều trị
    
    private String currentStatus; // Trạng thái hiện tại
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Symptom {
        private String name;
        private String description;
        private String severity; // MILD, MODERATE, SEVERE
        private LocalDateTime firstObserved;
        private String notes;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Assessment {
        private String type; // Loại đánh giá
        private LocalDateTime assessmentDate;
        private String assessor; // Người đánh giá
        private Double score; // Điểm số
        private String result; // Kết quả
        private String notes;
        private List<String> recommendations;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Treatment {
        private String type; // Loại điều trị
        private String description;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private String provider; // Người cung cấp điều trị
        private String status; // ONGOING, COMPLETED, DISCONTINUED
        private String effectiveness; // EFFECTIVE, PARTIALLY_EFFECTIVE, NOT_EFFECTIVE
        private String notes;
    }
}

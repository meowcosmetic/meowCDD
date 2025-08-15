package com.meowcdd.document;

import com.meowcdd.document.base.BaseDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "cdd_tests")
public class CDDTest extends BaseDocument {

    @Id
    private String id;

    // Thông tin cơ bản của bài kiểm tra
    private String assessmentCode; // Mã bài kiểm tra (ví dụ: "M-CHAT-R", "ASQ-3")
    private Map<String, String> names; // Tên bài kiểm tra đa ngôn ngữ
    private Map<String, String> descriptions; // Mô tả bài kiểm tra đa ngôn ngữ
    private Map<String, String> instructions; // Hướng dẫn đa ngôn ngữ
    
    // Thông tin phân loại
    private String category; // Loại bài kiểm tra (ví dụ: "AUTISM_SCREENING", "LANGUAGE_ASSESSMENT")
    private String targetAgeGroup; // Nhóm tuổi mục tiêu (ví dụ: "18-36_MONTHS", "3-7_YEARS")
    private Integer minAgeMonths; // Tuổi tối thiểu (tháng)
    private Integer maxAgeMonths; // Tuổi tối đa (tháng)
    
    // Thông tin quản lý
    private String status; // ACTIVE, INACTIVE, DRAFT
    private String version; // Phiên bản bài kiểm tra
    private Integer estimatedDuration; // Thời gian ước tính (phút)
    
    // Danh sách câu hỏi
    private List<YesNoQuestion> questions;
    
    // Tiêu chí đánh giá
    private ScoringCriteria scoringCriteria;
    
    // Thông tin bổ sung
    private String administrationType; // INDIVIDUAL, PARENT_REPORT, OBSERVATION
    private String requiredQualifications; // Yêu cầu về trình độ người thực hiện
    private List<String> requiredMaterials; // Vật liệu cần thiết
    private Map<String, String> notes; // Ghi chú đa ngôn ngữ
    
    // Timestamps được tự động quản lý bởi @CreatedDate và @LastModifiedDate trong BaseDocument
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class YesNoQuestion {
        private String questionId;
        private Integer questionNumber; // Số thứ tự câu hỏi
        private Map<String, String> questionTexts; // Nội dung câu hỏi đa ngôn ngữ
        private String category; // Danh mục câu hỏi (ví dụ: "COMMUNICATION", "SOCIAL_INTERACTION")
        private Integer weight; // Trọng số câu hỏi
        private Boolean required; // Câu hỏi bắt buộc hay không
        private Map<String, String> hints; // Gợi ý cho người thực hiện
        private Map<String, String> explanations; // Giải thích câu hỏi
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ScoringCriteria {
        private Integer totalQuestions; // Tổng số câu hỏi
        private Integer yesScore; // Điểm cho câu trả lời "Có"
        private Integer noScore; // Điểm cho câu trả lời "Không"
        private Map<String, ScoreRange> scoreRanges; // Các khoảng điểm và ý nghĩa
        private String interpretation; // Cách diễn giải kết quả
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ScoreRange {
        private Integer minScore;
        private Integer maxScore;
        private String level; // Mức độ (ví dụ: "LOW_RISK", "MEDIUM_RISK", "HIGH_RISK")
        private Map<String, String> descriptions; // Mô tả mức độ đa ngôn ngữ
        private String recommendation; // Khuyến nghị
    }
}

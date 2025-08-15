package com.meowcdd.entity.supabase;

import com.meowcdd.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@Table(name = "child_test_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ChildTestRecordSupabase extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "external_id", unique = true, nullable = false)
    private String externalId; // ID từ service khác
    
    @Column(name = "child_id", nullable = false)
    private Long childId; // ID của trẻ thực hiện bài test
    
    @Column(name = "test_id", nullable = false)
    private Long testId; // ID của bài test (CDDTest hoặc AssessmentTest)
    
    @Column(name = "test_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TestType testType; // Loại bài test
    
    @Column(name = "test_date", nullable = false)
    private LocalDateTime testDate; // Ngày thực hiện bài test
    
    @Column(name = "start_time")
    private LocalDateTime startTime; // Thời gian bắt đầu
    
    @Column(name = "end_time")
    private LocalDateTime endTime; // Thời gian kết thúc
    
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status; // Trạng thái
    
    @Column(name = "total_score")
    private Double totalScore; // Tổng điểm
    
    @Column(name = "max_score")
    private Double maxScore; // Điểm tối đa có thể đạt
    
    @Column(name = "percentage_score")
    private Double percentageScore; // Phần trăm điểm đạt được
    
    @Column(name = "result_level")
    @Enumerated(EnumType.STRING)
    private ResultLevel resultLevel; // Mức độ kết quả
    
    @Column(name = "interpretation", columnDefinition = "TEXT")
    private String interpretation; // Diễn giải kết quả
    
    @Column(name = "question_answers", columnDefinition = "TEXT")
    private String questionAnswers; // Chi tiết câu trả lời của từng câu hỏi (JSON)
    
    @Column(name = "correct_answers")
    private Integer correctAnswers; // Số câu trả lời đúng
    
    @Column(name = "total_questions")
    private Integer totalQuestions; // Tổng số câu hỏi
    
    @Column(name = "skipped_questions")
    private Integer skippedQuestions; // Số câu bỏ qua
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes; // Ghi chú của người thực hiện
    
    @Column(name = "environment")
    private String environment; // Môi trường thực hiện (HOME, CLINIC, SCHOOL)
    
    @Column(name = "assessor")
    private String assessor; // Người đánh giá
    
    @Column(name = "parent_present")
    private Boolean parentPresent; // Có phụ huynh tham gia không
    
    @PrePersist
    @Override
    protected void onCreate() {
        super.onCreate();
        if (testDate == null) {
            testDate = LocalDateTime.now();
        }
        if (status == null) {
            status = Status.IN_PROGRESS;
        }
        calculatePercentageScore();
        determineResultLevel();
    }
    
    @PreUpdate
    protected void onUpdate() {
        super.onUpdate();
        calculatePercentageScore();
        determineResultLevel();
    }
    
    /**
     * Tính phần trăm điểm đạt được
     */
    private void calculatePercentageScore() {
        if (totalScore != null && maxScore != null && maxScore > 0) {
            this.percentageScore = (totalScore / maxScore) * 100;
        }
    }
    
    /**
     * Xác định mức độ kết quả dựa trên phần trăm điểm
     */
    private void determineResultLevel() {
        if (percentageScore == null) {
            this.resultLevel = null;
            return;
        }
        
        if (percentageScore >= 90) {
            this.resultLevel = ResultLevel.EXCELLENT;
        } else if (percentageScore >= 80) {
            this.resultLevel = ResultLevel.GOOD;
        } else if (percentageScore >= 70) {
            this.resultLevel = ResultLevel.AVERAGE;
        } else if (percentageScore >= 60) {
            this.resultLevel = ResultLevel.BELOW_AVERAGE;
        } else {
            this.resultLevel = ResultLevel.POOR;
        }
    }
    
    /**
     * Kiểm tra đã hoàn thành chưa
     */
    public boolean isCompleted() {
        return Status.COMPLETED.equals(this.status);
    }
    
    /**
     * Tính thời gian làm bài (phút)
     */
    public Integer getDuration() {
        if (startTime != null && endTime != null) {
            long durationInSeconds = java.time.Duration.between(startTime, endTime).getSeconds();
            return (int) (durationInSeconds / 60);
        }
        return null;
    }
    
    public enum TestType {
        CDD_TEST("CDD Test"),
        ASSESSMENT_TEST("Assessment Test");
        
        private final String displayName;
        
        TestType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum Status {
        IN_PROGRESS("Đang thực hiện"),
        COMPLETED("Hoàn thành"),
        ABANDONED("Bỏ dở"),
        INVALID("Không hợp lệ"),
        REVIEWED("Đã xem xét");
        
        private final String displayName;
        
        Status(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum ResultLevel {
        EXCELLENT("Xuất sắc"),
        GOOD("Tốt"),
        AVERAGE("Trung bình"),
        BELOW_AVERAGE("Dưới trung bình"),
        POOR("Kém");
        
        private final String displayName;
        
        ResultLevel(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
}

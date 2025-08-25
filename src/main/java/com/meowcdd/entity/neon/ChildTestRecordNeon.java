package com.meowcdd.entity.neon;

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
public class ChildTestRecordNeon extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "child_id", nullable = false)
    private String childId; // ID của trẻ thực hiện bài test
    
    @Column(name = "test_id", nullable = false)
    private String testId; // ID của bài test (CDDTest hoặc AssessmentTest)
    
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
    
    public enum Status {
        SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED, RESCHEDULED
    }
    
    public enum TestType {
        CDD_TEST, ASSESSMENT_TEST, DEVELOPMENTAL_SCREENING, DIAGNOSTIC_TEST
    }
    
    public enum ResultLevel {
        EXCELLENT, GOOD, AVERAGE, BELOW_AVERAGE, POOR, NEEDS_INTERVENTION
    }
}

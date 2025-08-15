package com.meowcdd.document;

import com.meowcdd.document.base.BaseDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "cdd_test_results")
public class CDDTestResult extends BaseDocument {

    @Id
    private String id;

    // Thông tin liên kết
    private String childId; // ID của trẻ
    private String assessmentId; // ID của bài kiểm tra
    private String assessmentCode; // Mã bài kiểm tra
    private String administratorId; // ID người thực hiện
    
    // Thông tin thời gian
    private LocalDateTime startTime; // Thời gian bắt đầu
    private LocalDateTime endTime; // Thời gian kết thúc
    private Integer durationMinutes; // Thời gian thực hiện (phút)
    
    // Kết quả chi tiết
    private List<QuestionAnswer> answers; // Danh sách câu trả lời
    private Integer totalScore; // Tổng điểm
    private String riskLevel; // Mức độ rủi ro (LOW_RISK, MEDIUM_RISK, HIGH_RISK)
    private String interpretation; // Diễn giải kết quả
    
    // Thông tin bổ sung
    private String notes; // Ghi chú của người thực hiện
    private String parentComments; // Nhận xét của phụ huynh
    private String status; // COMPLETED, INCOMPLETE, CANCELLED
    private Map<String, Object> additionalData; // Dữ liệu bổ sung
    
    // Timestamps được tự động quản lý bởi @CreatedDate và @LastModifiedDate trong BaseDocument
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class QuestionAnswer {
        private String questionId;
        private Integer questionNumber;
        private String answer; // YES, NO, SKIPPED
        private Integer score; // Điểm cho câu trả lời này
        private String notes; // Ghi chú cho câu hỏi này
        private LocalDateTime answeredAt; // Thời gian trả lời
    }
}

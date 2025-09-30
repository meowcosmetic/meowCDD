package com.meowcdd.dto;

import com.meowcdd.entity.neon.InterventionPost;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterventionPostDto {
    private Long id;
    private String title;
    private Map<String, Object> content; // JSON object chứa nội dung bài post
    private InterventionPost.PostType postType;
    private Integer difficultyLevel; // 1-5 (dễ đến khó)
    private Integer targetAgeMinMonths;
    private Integer targetAgeMaxMonths;
    private Integer estimatedDurationMinutes; // Thời gian ước tính thực hiện
    private String tags; // Các tag phân cách bằng dấu phẩy
    private Boolean isPublished;
    private String author;
    private String version; // Phiên bản của bài post
    
    // Liên kết với mục tiêu can thiệp
    private Long criteriaId;
    private String criteriaDescription; // Mô tả của criteria để hiển thị
    
    // Liên kết với chương trình can thiệp
    private Long programId;
    private String programName; // Tên chương trình để hiển thị
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

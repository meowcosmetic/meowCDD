package com.meowcdd.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO cho entity BookRating
 * Sử dụng để truyền dữ liệu giữa các layer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRatingDto {
    
    private Long id;
    
    // === QUAN HỆ VỚI BOOK VÀ USER ===
    private Long bookId; // ID sách được đánh giá
    private String bookTitle; // Tiêu đề sách
    private String parentId; // ID phụ huynh đánh giá
    
    // === THÔNG TIN ĐÁNH GIÁ ===
    private Integer rating; // Điểm đánh giá (1-5)
    private String review; // Nội dung đánh giá
    private String reviewTitle; // Tiêu đề đánh giá
    
    // === THÔNG TIN BỔ SUNG ===
    private Integer helpfulCount; // Số người thấy hữu ích
    private Integer notHelpfulCount; // Số người thấy không hữu ích
    private LocalDateTime ratingDate; // Ngày đánh giá
    private Boolean isEdited; // Có chỉnh sửa không
    private LocalDateTime editDate; // Ngày chỉnh sửa
    
    // === ENUM ===
    private String ratingType; // Loại đánh giá: PARENT_RATING, EDUCATOR_RATING, EXPERT_RATING, CHILD_FEEDBACK
    
    // === THÔNG TIN TIMESTAMP ===
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

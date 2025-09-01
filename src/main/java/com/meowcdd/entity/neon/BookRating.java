package com.meowcdd.entity.neon;

import com.meowcdd.entity.base.BaseEntity;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity quản lý đánh giá sách
 * Lưu trữ thông tin chi tiết về đánh giá của người dùng cho từng sách
 */
@Entity
@Table(name = "book_ratings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class BookRating extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // === QUAN HỆ VỚI BOOK VÀ USER ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book; // Sách được đánh giá
    
    @Column(name = "parent_id", nullable = false)
    private String parentId; // ID phụ huynh đánh giá
    
    // === THÔNG TIN ĐÁNH GIÁ ===
    @Column(name = "rating", nullable = false)
    private Integer rating; // Điểm đánh giá (1-5)
    
    @Column(name = "review", columnDefinition = "TEXT")
    private String review; // Nội dung đánh giá
    
    @Column(name = "review_title")
    private String reviewTitle; // Tiêu đề đánh giá
    
    // === THÔNG TIN BỔ SUNG ===
    @Column(name = "is_helpful")
    private Integer helpfulCount = 0; // Số người thấy hữu ích
    
    @Column(name = "is_not_helpful")
    private Integer notHelpfulCount = 0; // Số người thấy không hữu ích
    
    @Column(name = "rating_date")
    private LocalDateTime ratingDate; // Ngày đánh giá
    
    @Column(name = "is_edited")
    private Boolean isEdited = false; // Có chỉnh sửa không
    
    @Column(name = "edit_date")
    private LocalDateTime editDate; // Ngày chỉnh sửa
    
    // === ENUM ===
    public enum RatingType {
        PARENT_RATING,    // Đánh giá của phụ huynh
        EDUCATOR_RATING,  // Đánh giá của giáo viên
        EXPERT_RATING,    // Đánh giá của chuyên gia
        CHILD_FEEDBACK    // Phản hồi của trẻ (nếu có)
    }
    
    @Column(name = "rating_type")
    @Enumerated(EnumType.STRING)
    private RatingType ratingType = RatingType.PARENT_RATING; // Loại đánh giá
}

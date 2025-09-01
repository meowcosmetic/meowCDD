package com.meowcdd.entity.neon;

import com.meowcdd.entity.base.BaseEntity;
import com.meowcdd.entity.neon.DevelopmentalDomain;
import com.meowcdd.entity.neon.SupportedFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Entity quản lý thông tin sách trong hệ thống
 * Lưu trữ thông tin cơ bản, mối quan hệ với định dạng file và lĩnh vực phát triển
 */
@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Book extends BaseEntity {
    
    // === THÔNG TIN CƠ BẢN ===
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "title", nullable = false)
    private String title; // Tiêu đề sách
    
    @Column(name = "author")
    private String author; // Tác giả
    
    @Column(name = "publisher")
    private String publisher; // Nhà xuất bản
    
    @Column(name = "isbn")
    private String isbn; // Mã ISBN (nếu có)
    
    @Column(name = "publication_year")
    private Integer publicationYear; // Năm xuất bản
    
    // === QUAN HỆ VỚI CÁC ENTITY KHÁC ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supported_format_id", nullable = false)
    private SupportedFormat supportedFormat; // Định dạng file
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "book_developmental_domains",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "developmental_domain_id")
    )
    private Set<DevelopmentalDomain> developmentalDomains; // Lĩnh vực phát triển
    
    // === THÔNG TIN ĐỘ TUỔI ===
    @Column(name = "min_age")
    private Integer minAge; // Độ tuổi tối thiểu (tháng)
    
    @Column(name = "max_age")
    private Integer maxAge; // Độ tuổi tối đa (tháng)
    
    @Column(name = "age_group")
    private String ageGroup; // Nhóm tuổi: "INFANT", "TODDLER", "PRESCHOOL", "SCHOOL_AGE"
    
    // === THÔNG TIN NỘI DUNG ===
    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // Mô tả nội dung
    
    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary; // Tóm tắt
    
    @Column(name = "language")
    private String language; // Ngôn ngữ (VI, EN, v.v.)
    
    // === THÔNG TIN FILE ===
    @Column(name = "file_size")
    private Long fileSize; // Kích thước file (bytes)
    
    @Column(name = "page_count")
    private Integer pageCount; // Số trang (nếu là sách)
    
    // === THÔNG TIN ĐÁNH GIÁ (TÍNH TOÁN TỪ BOOK_RATINGS) ===
    @Column(name = "average_rating")
    private Double averageRating; // Điểm đánh giá trung bình (1-5)
    
    @Column(name = "total_ratings")
    private Integer totalRatings; // Tổng số lượt đánh giá
    
    @Column(name = "total_views")
    private Long totalViews; // Tổng lượt xem
    
    // === THÔNG TIN TRẠNG THÁI ===
    @Column(name = "is_active")
    @lombok.Builder.Default
    private Boolean isActive = true; // Có đang hoạt động không
    
    @Column(name = "is_featured")
    @lombok.Builder.Default
    private Boolean isFeatured = false; // Có phải sách nổi bật không
    
    // === THÔNG TIN BỔ SUNG ===
    @Column(name = "keywords")
    private String keywords; // Từ khóa tìm kiếm (phân cách bằng dấu phẩy)
    
    @Column(name = "tags")
    private String tags; // Tags (JSON array)
    
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata; // Metadata bổ sung (JSON)
    
    // === FILE NỘI DUNG SÁCH (LƯU TRỰC TIẾP TRONG DB) ===
    @Column(name = "content_file", columnDefinition = "BYTEA")
    private byte[] contentFile; // File nội dung sách (PDF, EPUB, etc.) lưu dưới dạng binary
    
    @Column(name = "content_file_name")
    private String contentFileName; // Tên file gốc
    
    @Column(name = "content_file_type")
    private String contentFileType; // Loại file: "PDF", "EPUB", "DOCX", "TXT", etc.
    
    @Column(name = "content_file_size")
    private Long contentFileSize; // Kích thước file (bytes)
    
    @Column(name = "content_mime_type")
    private String contentMimeType; // MIME type của file (application/pdf, application/epub+zip, etc.)
    
    @Column(name = "content_uploaded_at")
    private java.time.LocalDateTime contentUploadedAt; // Thời gian upload file
    
    @Column(name = "content_uploaded_by")
    private String contentUploadedBy; // Người upload file
    
    @Column(name = "content_is_verified")
    @lombok.Builder.Default
    private Boolean contentIsVerified = false; // File đã được verify chưa
    
    @Column(name = "content_verification_date")
    private java.time.LocalDateTime contentVerificationDate; // Ngày verify file
}

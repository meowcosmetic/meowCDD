package com.meowcdd.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * DTO cho entity Book
 * Sử dụng để truyền dữ liệu giữa các layer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    
    // === THÔNG TIN CƠ BẢN ===
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private Integer publicationYear;
    
    // === QUAN HỆ VỚI CÁC ENTITY KHÁC ===
    private Long supportedFormatId; // ID của định dạng file
    private String supportedFormatName; // Tên định dạng file
    private Set<UUID> developmentalDomainIds; // Set ID của các lĩnh vực phát triển
    private Set<String> developmentalDomainNames; // Set tên của các lĩnh vực phát triển
    
    // === THÔNG TIN ĐỘ TUỔI ===
    private Integer minAge;
    private Integer maxAge;
    private String ageGroup;
    
    // === THÔNG TIN NỘI DUNG ===
    private String description;
    private String summary;
    private String language;
    
    // === THÔNG TIN FILE ===
    private Long fileSize;
    private Integer pageCount;
    
    // === THÔNG TIN ĐÁNH GIÁ ===
    private Double averageRating;
    private Integer totalRatings;
    private Long totalViews;
    
    // === THÔNG TIN TRẠNG THÁI ===
    private Boolean isActive;
    private Boolean isFeatured;
    
    // === THÔNG TIN BỔ SUNG ===
    private String coverImageUrl;
    private String previewUrl;
    private String keywords;
    private String tags;
    private String metadata;
    
    // === THÔNG TIN TIMESTAMP ===
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

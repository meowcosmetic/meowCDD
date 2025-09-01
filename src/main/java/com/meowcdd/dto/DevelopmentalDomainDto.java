package com.meowcdd.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * DTO cho entity DevelopmentalDomain
 * Dùng để truyền dữ liệu giữa Controller và Service
 * Chứa thông tin về các lĩnh vực phát triển của trẻ em
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DevelopmentalDomainDto {
    
    /**
     * ID duy nhất của lĩnh vực phát triển (UUID)
     */
    private UUID id;
    
    /**
     * Tên lĩnh vực phát triển (tên kỹ thuật)
     * VD: "physical_development", "cognitive_development", "language_development"
     */
    private String name;
    
    /**
     * Tên hiển thị của lĩnh vực phát triển (đa ngôn ngữ)
     * Có thể là Map<String, String> hoặc String
     * VD: {"vi": "Phát triển thể chất", "en": "Physical Development"}
     */
    private Object displayedName;
    
    /**
     * Mô tả chi tiết về lĩnh vực phát triển (song ngữ)
     * Có thể là Map<String, String> hoặc String
     * VD: {"vi": "Mô tả bằng tiếng Việt", "en": "Description in English"}
     */
    private Object description;
    
    /**
     * Phân loại lĩnh vực phát triển (CORE, SECONDARY, SPECIALIZED, INTEGRATED)
     */
    private String category;
    
    /**
     * Thời gian tạo
     */
    private LocalDateTime createdAt;
    
    /**
     * Thời gian cập nhật cuối
     */
    private LocalDateTime updatedAt;
}

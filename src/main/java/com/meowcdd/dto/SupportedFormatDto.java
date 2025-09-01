package com.meowcdd.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO cho entity SupportedFormat
 * Dùng để truyền dữ liệu giữa Controller và Service
 * Không chứa logic nghiệp vụ, chỉ là data transfer object
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupportedFormatDto {
    
    /**
     * ID duy nhất của định dạng
     */
    private Long id;
    
    /**
     * Tên định dạng file (VD: "PDF Document", "JPEG Image")
     */
    private String formatName;
    
    /**
     * Phần mở rộng file (VD: .pdf, .jpg, .mp4)
     */
    private String fileExtension;
    
    /**
     * MIME type chuẩn (VD: application/pdf, image/jpeg)
     */
    private String mimeType;
    
    /**
     * Phân loại định dạng (DOCUMENT, IMAGE, AUDIO, etc.)
     */
    private String category;
    
    /**
     * Trạng thái hoạt động (true = đang hỗ trợ, false = không hỗ trợ)
     */
    private Boolean isActive;
    
    /**
     * Kích thước file tối đa được phép upload (bytes)
     */
    private Long maxFileSize;
    
    /**
     * Mô tả chi tiết về định dạng
     */
    private String description;
    
    /**
     * Thông tin phiên bản được hỗ trợ
     */
    private String versionInfo;
    
    /**
     * Độ ưu tiên xử lý (1-10)
     */
    private Integer processingPriority;
    
    /**
     * URL icon đại diện cho định dạng
     */
    private String iconUrl;
    
    /**
     * Thời gian tạo
     */
    private LocalDateTime createdAt;
    
    /**
     * Thời gian cập nhật cuối
     */
    private LocalDateTime updatedAt;
}

package com.meowcdd.entity.neon;

import com.meowcdd.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

/**
 * Entity quản lý các định dạng file được hỗ trợ trong hệ thống
 * Sử dụng để validate file upload, hiển thị danh sách định dạng hỗ trợ
 * và quản lý quy trình xử lý file theo từng loại định dạng
 */
@Entity
@Table(name = "supported_formats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SupportedFormat extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Tên định dạng file (VD: "PDF Document", "JPEG Image", "MP4 Video")
     * Dùng để hiển thị cho người dùng, phải unique
     */
    @Column(name = "format_name", nullable = false, unique = true)
    private String formatName;
    
    /**
     * Phần mở rộng file (VD: .pdf, .jpg, .mp4)
     * Dùng để validate file upload và mapping
     */
    @Column(name = "file_extension", nullable = false)
    private String fileExtension;
    
    /**
     * MIME type chuẩn (VD: application/pdf, image/jpeg, video/mp4)
     * Dùng để xác định loại file khi upload
     */
    @Column(name = "mime_type")
    private String mimeType;
    
    /**
     * Phân loại định dạng theo nhóm chức năng
     * Giúp tổ chức và filter các định dạng theo category
     */
    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private FormatCategory category;
    
    /**
     * Trạng thái hoạt động của định dạng
     * false = không còn hỗ trợ, true = đang hỗ trợ
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    /**
     * Kích thước file tối đa được phép upload (tính bằng bytes)
     * null = không giới hạn kích thước
     */
    @Column(name = "max_file_size")
    private Long maxFileSize;
    
    /**
     * Mô tả chi tiết về định dạng file
     * Thông tin về cách sử dụng, ưu nhược điểm
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    /**
     * Thông tin về phiên bản được hỗ trợ
     * VD: "PDF 1.4+", "MP4 H.264", "JPEG Baseline"
     */
    @Column(name = "version_info")
    private String versionInfo;
    
    /**
     * Độ ưu tiên khi xử lý file (1-10)
     * Số càng cao càng ưu tiên xử lý trước
     */
    @Column(name = "processing_priority")
    private Integer processingPriority;
    
    /**
     * URL icon đại diện cho định dạng
     * Dùng để hiển thị trong UI
     */
    @Column(name = "icon_url")
    private String iconUrl;
    
    /**
     * Enum định nghĩa các loại định dạng chính
     * Giúp phân loại và tổ chức các định dạng file
     */
    public enum FormatCategory {
        /**
         * Tài liệu văn bản: PDF, DOCX, TXT, RTF
         */
        DOCUMENT,
        
        /**
         * Hình ảnh: JPG, PNG, GIF, BMP, SVG
         */
        IMAGE,
        
        /**
         * Âm thanh: MP3, WAV, AAC, OGG
         */
        AUDIO,
        
        /**
         * Video: MP4, AVI, MOV, WMV
         */
        VIDEO,
        
        /**
         * File nén: ZIP, RAR, 7Z, TAR
         */
        ARCHIVE,
        
        /**
         * Dữ liệu: JSON, XML, CSV, XLSX
         */
        DATA,
        
        /**
         * Mã nguồn: JAVA, JS, HTML, CSS, PY
         */
        CODE,
        
        /**
         * Các loại khác không thuộc nhóm trên
         */
        OTHER
    }
}

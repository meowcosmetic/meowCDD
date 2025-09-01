package com.meowcdd.entity.neon;

import com.meowcdd.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.UUID;

/**
 * Entity quản lý các lĩnh vực phát triển của trẻ em
 * Sử dụng để phân loại và tổ chức các khía cạnh phát triển khác nhau
 * như phát triển thể chất, nhận thức, ngôn ngữ, xã hội-cảm xúc
 */
@Entity
@Table(name = "developmental_domains")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class DevelopmentalDomain extends BaseEntity {
    
    /**
     * ID duy nhất của lĩnh vực phát triển (UUID)
     * Sử dụng UUID để đảm bảo tính duy nhất toàn cục
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;
    
    /**
     * Tên lĩnh vực phát triển (tên kỹ thuật)
     * VD: "physical_development", "cognitive_development", "language_development"
     * Không được null và phải unique
     * Dùng cho việc lập trình và database operations
     */
    @Column(name = "name", nullable = false, unique = true, length = 255)
    private String name;
    
    /**
     * Tên hiển thị của lĩnh vực phát triển (đa ngôn ngữ)
     * Cấu trúc JSON để hỗ trợ đa ngôn ngữ:
     * {
     *   "vi": "Phát triển thể chất",
     *   "en": "Physical Development"
     * }
     * Hoặc có thể lưu dạng text thường nếu chỉ cần 1 ngôn ngữ
     */
    @Column(name = "displayed_name", nullable = false, columnDefinition = "TEXT")
    private String displayedName;
    
    /**
     * Mô tả chi tiết về lĩnh vực phát triển (song ngữ)
     * Cấu trúc JSON để hỗ trợ song ngữ:
     * {
     *   "vi": "Mô tả bằng tiếng Việt",
     *   "en": "Description in English"
     * }
     * Hoặc có thể lưu dạng text thường nếu chỉ cần 1 ngôn ngữ
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    /**
     * Phân loại lĩnh vực phát triển (dạng String để linh hoạt thêm mới)
     * Dùng để nhóm các lĩnh vực có tính chất tương tự
     * VD: "CORE", "SECONDARY", "SPECIALIZED", "INTEGRATED", v.v.
     * Có thể thêm các category mới mà không cần thay đổi code
     */
    @Column(name = "category", length = 100)
    private String category;
}

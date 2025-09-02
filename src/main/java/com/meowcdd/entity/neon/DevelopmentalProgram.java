package com.meowcdd.entity.neon;

import com.meowcdd.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entity quản lý chương trình can thiệp/phát triển (Developmental Program)
 * Yêu cầu theo bảng (PostgreSQL):
 * - id: BIGSERIAL (IDENTITY)
 * - code: VARCHAR(50) UNIQUE NOT NULL (mã ngắn gọn, ví dụ: "ESDM", "VBMAPP")
 * - name: JSON (lưu dưới dạng chuỗi JSON)
 * - description: JSON (lưu dưới dạng chuỗi JSON)
 *
 * Lưu ý: Các trường JSON được ánh xạ sang TEXT để lưu string JSON (tương tự cách xử lý đa ngôn ngữ trước đó).
 */
@Entity
@Table(name = "developmental_programs",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_developmental_programs_code", columnNames = {"code"})
       })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class DevelopmentalProgram extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Mã ngắn gọn của chương trình (ví dụ: "ESDM", "VBMAPP")
     */
    @Column(name = "code", length = 50, nullable = false, unique = true)
    private String code;

    /**
     * Tên đa ngôn ngữ - lưu chuỗi JSON (ví dụ: {"vi":"...","en":"..."})
     */
    @Column(name = "name", columnDefinition = "TEXT", nullable = false)
    private String name;

    /**
     * Mô tả đa ngôn ngữ - lưu chuỗi JSON (tùy chọn)
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}



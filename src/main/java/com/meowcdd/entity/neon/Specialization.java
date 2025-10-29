package com.meowcdd.entity.neon;

import com.meowcdd.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

/**
 * Entity quản lý danh sách các chuyên môn có sẵn trong hệ thống
 * Đây là bảng master data cho các specialization
 */
@Entity
@Table(name = "specializations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Specialization extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "specialization_id", columnDefinition = "UUID")
    private UUID specializationId;

    @Column(name = "specialization_name", nullable = false, unique = true, length = 255)
    private String specializationName; // Ví dụ: "Speech Therapy", "Occupational Therapy"

    @Column(name = "specialization_type", nullable = false, length = 100)
    private String specializationType; // Ví dụ: "THERAPY", "ASSESSMENT", "EDUCATION"

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "category", length = 100)
    private String category; // Ví dụ: "DEVELOPMENTAL", "BEHAVIORAL", "ACADEMIC"

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "required_certifications", columnDefinition = "jsonb")
    private Map<String, Object> requiredCertifications; // Chứng chỉ yêu cầu

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "typical_skills", columnDefinition = "jsonb")
    private Map<String, Object> typicalSkills; // Kỹ năng điển hình

    @Column(name = "min_experience_years")
    private Integer minExperienceYears; // Số năm kinh nghiệm tối thiểu

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private Status status = Status.ACTIVE;

    public enum Status {
        ACTIVE, INACTIVE, DEPRECATED
    }

    @PrePersist
    private void ensureId() {
        if (specializationId == null) {
            specializationId = UUID.randomUUID();
        }
    }
}

package com.meowcdd.entity.neon;

import com.meowcdd.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

/**
 * Entity quản lý chi tiết yêu cầu cho từng vai trò
 * Chứa thông tin về học vấn, kỹ năng, chứng chỉ cần thiết
 */
@Entity
@Table(name = "role_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class RoleDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "detail_id", columnDefinition = "UUID")
    private UUID detailId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false, columnDefinition = "UUID")
    private Role role;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "education_requirement", columnDefinition = "jsonb")
    private Map<String, Object> educationRequirement;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "main_tasks", columnDefinition = "jsonb")
    private Map<String, Object> mainTasks;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "required_skills", columnDefinition = "jsonb")
    private Map<String, Object> requiredSkills;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "certifications_required", columnDefinition = "jsonb")
    private Map<String, Object> certificationsRequired;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "experience_requirement", columnDefinition = "jsonb")
    private Map<String, Object> experienceRequirement;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "notes", columnDefinition = "jsonb")
    private Map<String, Object> notes;

    @PrePersist
    private void ensureId() {
        if (detailId == null) {
            detailId = UUID.randomUUID();
        }
    }
}

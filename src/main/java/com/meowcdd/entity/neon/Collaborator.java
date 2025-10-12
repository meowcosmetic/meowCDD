package com.meowcdd.entity.neon;

import com.meowcdd.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

/**
 * Entity quản lý thông tin cộng tác viên
 * Liên kết user với role và chứa thông tin chuyên môn
 */
@Entity
@Table(name = "collaborators")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Collaborator extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "collaborator_id", columnDefinition = "UUID")
    private UUID collaboratorId;

    @Column(name = "user_id", nullable = false, columnDefinition = "UUID")
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false, columnDefinition = "UUID")
    private Role role;

    @Column(name = "specialization", length = 255)
    private String specialization;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "certification", columnDefinition = "jsonb")
    private Map<String, Object> certification;

    @Column(name = "organization", length = 255)
    private String organization;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "availability", columnDefinition = "jsonb")
    private Map<String, Object> availability;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private Status status = Status.PENDING;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "notes", columnDefinition = "jsonb")
    private Map<String, Object> notes;

    public enum Status {
        ACTIVE, INACTIVE, PENDING
    }

    @PrePersist
    private void ensureId() {
        if (collaboratorId == null) {
            collaboratorId = UUID.randomUUID();
        }
    }
}

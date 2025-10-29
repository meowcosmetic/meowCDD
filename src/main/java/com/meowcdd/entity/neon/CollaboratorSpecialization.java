package com.meowcdd.entity.neon;

import com.meowcdd.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

/**
 * Entity liên kết giữa Collaborator và Specialization
 * Cho phép một collaborator có nhiều specialization và một specialization có nhiều collaborator
 */
@Entity
@Table(name = "collaborator_specializations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CollaboratorSpecialization extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "collaborator_specialization_id", columnDefinition = "UUID")
    private UUID collaboratorSpecializationId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "collaborator_id", nullable = false, columnDefinition = "UUID")
    private Collaborator collaborator;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "specialization_id", nullable = false, columnDefinition = "UUID")
    private Specialization specialization;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "certifications", columnDefinition = "jsonb")
    private Map<String, Object> certifications; // Chứng chỉ cụ thể của collaborator cho specialization này

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "skills", columnDefinition = "jsonb")
    private Map<String, Object> skills; // Kỹ năng cụ thể của collaborator

    @Column(name = "is_primary", nullable = false)
    @Builder.Default
    private Boolean isPrimary = false; // Chuyên môn chính hay không

    @Enumerated(EnumType.STRING)
    @Column(name = "proficiency_level", nullable = false)
    @Builder.Default
    private ProficiencyLevel proficiencyLevel = ProficiencyLevel.INTERMEDIATE;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes; // Ghi chú về specialization này của collaborator

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private Status status = Status.ACTIVE;

    public enum ProficiencyLevel {
        BEGINNER,      // Mới bắt đầu
        INTERMEDIATE,   // Trung bình
        ADVANCED,      // Nâng cao
        EXPERT         // Chuyên gia
    }

    public enum Status {
        ACTIVE, INACTIVE, SUSPENDED
    }

    @PrePersist
    private void ensureId() {
        if (collaboratorSpecializationId == null) {
            collaboratorSpecializationId = UUID.randomUUID();
        }
    }
}

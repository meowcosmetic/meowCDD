package com.meowcdd.dto;

import com.meowcdd.entity.neon.CollaboratorSpecialization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollaboratorSpecializationDto {
    
    private UUID collaboratorSpecializationId;
    private UUID collaboratorId;
    private UUID specializationId;
    private String collaboratorName; // Tên collaborator để hiển thị
    private String specializationName; // Tên specialization để hiển thị
    private String specializationType; // Loại specialization để hiển thị
    private Integer yearsOfExperience;
    private Map<String, Object> certifications;
    private Map<String, Object> skills;
    private Boolean isPrimary;
    private CollaboratorSpecialization.ProficiencyLevel proficiencyLevel;
    private String notes;
    private CollaboratorSpecialization.Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Helper methods để convert từ Entity
    public static CollaboratorSpecializationDto fromEntity(CollaboratorSpecialization entity) {
        return CollaboratorSpecializationDto.builder()
                .collaboratorSpecializationId(entity.getCollaboratorSpecializationId())
                .collaboratorId(entity.getCollaborator().getCollaboratorId())
                .specializationId(entity.getSpecialization().getSpecializationId())
                .specializationName(entity.getSpecialization().getSpecializationName())
                .specializationType(entity.getSpecialization().getSpecializationType())
                .yearsOfExperience(entity.getYearsOfExperience())
                .certifications(entity.getCertifications())
                .skills(entity.getSkills())
                .isPrimary(entity.getIsPrimary())
                .proficiencyLevel(entity.getProficiencyLevel())
                .notes(entity.getNotes())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
    
    public CollaboratorSpecialization toEntity() {
        return CollaboratorSpecialization.builder()
                .collaboratorSpecializationId(this.collaboratorSpecializationId)
                .yearsOfExperience(this.yearsOfExperience)
                .certifications(this.certifications)
                .skills(this.skills)
                .isPrimary(this.isPrimary)
                .proficiencyLevel(this.proficiencyLevel)
                .notes(this.notes)
                .status(this.status)
                .build();
    }
}

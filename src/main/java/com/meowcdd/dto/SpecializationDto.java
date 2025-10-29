package com.meowcdd.dto;

import com.meowcdd.entity.neon.Specialization;
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
public class SpecializationDto {
    
    private UUID specializationId;
    private String specializationName;
    private String specializationType;
    private String description;
    private String category;
    private Map<String, Object> requiredCertifications;
    private Map<String, Object> typicalSkills;
    private Integer minExperienceYears;
    private Boolean isActive;
    private Specialization.Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Helper methods để convert từ Entity
    public static SpecializationDto fromEntity(Specialization entity) {
        return SpecializationDto.builder()
                .specializationId(entity.getSpecializationId())
                .specializationName(entity.getSpecializationName())
                .specializationType(entity.getSpecializationType())
                .description(entity.getDescription())
                .category(entity.getCategory())
                .requiredCertifications(entity.getRequiredCertifications())
                .typicalSkills(entity.getTypicalSkills())
                .minExperienceYears(entity.getMinExperienceYears())
                .isActive(entity.getIsActive())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
    
    public Specialization toEntity() {
        return Specialization.builder()
                .specializationId(this.specializationId)
                .specializationName(this.specializationName)
                .specializationType(this.specializationType)
                .description(this.description)
                .category(this.category)
                .requiredCertifications(this.requiredCertifications)
                .typicalSkills(this.typicalSkills)
                .minExperienceYears(this.minExperienceYears)
                .isActive(this.isActive)
                .status(this.status)
                .build();
    }
}

package com.meowcdd.entity.neon;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.meowcdd.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@Table(name = "cdd_tests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.ALWAYS)
public class CDDTestNeon extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "assessment_code", unique = true, nullable = false)
    private String assessmentCode;
    
    @Column(name = "category")
    private String category;
    
    @Column(name = "version")
    private String version;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "administration_type")
    private AdministrationType administrationType;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "required_qualifications")
    private RequiredQualifications requiredQualifications;
    
    @Column(name = "min_age_months")
    private Integer minAgeMonths;
    
    @Column(name = "max_age_months")
    private Integer maxAgeMonths;
    
    @Column(name = "estimated_duration")
    private Integer estimatedDuration;
    
    @Column(name = "namesjson", columnDefinition = "TEXT")
    private String namesJson;
    
    @Column(name = "descriptionsjson", columnDefinition = "TEXT")
    private String descriptionsJson;
    
    @Column(name = "instructionsjson", columnDefinition = "TEXT")
    private String instructionsJson;
    
    @Column(name = "questionsjson", columnDefinition = "TEXT")
    @JsonProperty("questionsJson")
    private String questionsJson;
    
    @Column(name = "scoringcriteriajson", columnDefinition = "TEXT")
    private String scoringCriteriaJson;
    
    @Column(name = "requiredmaterialsjson", columnDefinition = "TEXT")
    private String requiredMaterialsJson;
    
    @Column(name = "notesjson", columnDefinition = "TEXT")
    private String notesJson;
    
    public enum Status {
        DRAFT, ACTIVE, INACTIVE, ARCHIVED
    }
    
    public enum AdministrationType {
        PARENT_REPORT, PROFESSIONAL_OBSERVATION, DIRECT_ASSESSMENT, SELF_REPORT
    }
    
    public enum RequiredQualifications {
        NO_QUALIFICATION_REQUIRED, PSYCHOLOGIST_REQUIRED, PEDIATRICIAN_REQUIRED, 
        DEVELOPMENTAL_SPECIALIST_REQUIRED, THERAPIST_REQUIRED, NURSE_REQUIRED, TEACHER_REQUIRED
    }
}

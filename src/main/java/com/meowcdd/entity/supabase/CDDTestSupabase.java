package com.meowcdd.entity.supabase;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cdd_tests")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CDDTestSupabase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "assessment_code", unique = true, nullable = false)
    private String assessmentCode;

    // Names as JSON string (multilingual support)
    @Column(columnDefinition = "TEXT")
    private String namesJson;

    // Descriptions as JSON string (multilingual support)
    @Column(columnDefinition = "TEXT")
    private String descriptionsJson;

    // Instructions as JSON string (multilingual support)
    @Column(columnDefinition = "TEXT")
    private String instructionsJson;

    @Column(nullable = false)
    private String category;

    @Column(name = "min_age_months")
    private Integer minAgeMonths;

    @Column(name = "max_age_months")
    private Integer maxAgeMonths;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private String version;

    @Column(name = "estimated_duration")
    private Integer estimatedDuration;

    @Enumerated(EnumType.STRING)
    @Column(name = "administration_type")
    private AdministrationType administrationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "required_qualifications")
    private RequiredQualifications requiredQualifications;

    // Required materials as JSON array string
    @Column(columnDefinition = "TEXT")
    private String requiredMaterialsJson;

    // Notes as JSON string (multilingual support)
    @Column(columnDefinition = "TEXT")
    private String notesJson;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Questions as JSON string (PostgreSQL can store JSON)
    @Column(columnDefinition = "TEXT")
    private String questionsJson;

    // Scoring criteria as JSON string
    @Column(columnDefinition = "TEXT")
    private String scoringCriteriaJson;

    public enum Status {
        DRAFT("Bản nháp"),
        ACTIVE("Hoạt động"),
        INACTIVE("Không hoạt động"),
        ARCHIVED("Đã lưu trữ");

        private final String displayName;

        Status(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum AdministrationType {
        PARENT_REPORT("Báo cáo phụ huynh"),
        PROFESSIONAL_OBSERVATION("Quan sát chuyên môn"),
        DIRECT_ASSESSMENT("Đánh giá trực tiếp"),
        SELF_REPORT("Tự báo cáo");

        private final String displayName;

        AdministrationType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}

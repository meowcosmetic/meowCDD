package com.meowcdd.entity.supabase;

import com.meowcdd.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Entity
@Table(name = "children")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ChildSupabase extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "parent_id", nullable = false)
    private String parentId; // ID của phụ huynh (String format)
    
    @Column(name = "full_name", nullable = false)
    private String fullName; // Họ và tên trẻ
    
    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender; // Giới tính
    
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth; // Ngày tháng năm sinh
    
    @Column(name = "current_age_months")
    private Integer currentAgeMonths; // Tuổi hiện tại (theo tháng)
    
    @Column(name = "is_premature")
    private Boolean isPremature; // Trẻ có phải sinh non không?
    
    @Column(name = "gestational_week")
    private Integer gestationalWeek; // Tuần thai thứ (nếu sinh non)
    
    @Column(name = "birth_weight_grams")
    private Integer birthWeightGrams; // Cân nặng khi sinh (gram)
    
    @Column(name = "special_medical_conditions", columnDefinition = "TEXT")
    private String specialMedicalConditions; // Tình trạng y tế đặc biệt
    
    @Column(name = "developmental_disorder_diagnosis")
    @Enumerated(EnumType.STRING)
    private DevelopmentalDisorderStatus developmentalDisorderDiagnosis; // Đã từng được chẩn đoán rối loạn phát triển
    
    @Column(name = "early_intervention_history")
    private Boolean hasEarlyIntervention; // Có từng được can thiệp sớm/chăm sóc đặc biệt
    
    @Column(name = "early_intervention_details", columnDefinition = "TEXT")
    private String earlyInterventionDetails; // Chi tiết can thiệp sớm
    
    @Column(name = "primary_language")
    private String primaryLanguage; // Ngôn ngữ chủ yếu sử dụng trong gia đình
    
    @Column(name = "family_developmental_issues", columnDefinition = "TEXT")
    private String familyDevelopmentalIssues; // Có ai khác trong gia đình từng có vấn đề phát triển/ngôn ngữ
    
    // Các trường thông tin cơ bản khác
    @Column(name = "height")
    private Double height; // cm
    
    @Column(name = "weight")
    private Double weight; // kg
    
    @Column(name = "blood_type")
    private String bloodType;
    
    @Column(name = "allergies", columnDefinition = "TEXT")
    private String allergies;
    
    @Column(name = "medical_history", columnDefinition = "TEXT")
    private String medicalHistory;
    
    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;
    
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @PrePersist
    @Override
    protected void onCreate() {
        super.onCreate();
        if (registrationDate == null) {
            registrationDate = LocalDateTime.now();
        }
        if (status == null) {
            status = Status.ACTIVE;
        }
        calculateCurrentAge();
    }
    
    @PreUpdate
    protected void onUpdate() {
        super.onUpdate();
        calculateCurrentAge();
    }
    
    /**
     * Tính tuổi hiện tại theo tháng
     */
    private void calculateCurrentAge() {
        if (dateOfBirth != null) {
            LocalDate now = LocalDate.now();
            Period period = Period.between(dateOfBirth, now);
            this.currentAgeMonths = period.getYears() * 12 + period.getMonths();
        }
    }
    
    public enum Gender {
        MALE("Nam"),
        FEMALE("Nữ"),
        OTHER("Khác");
        
        private final String displayName;
        
        Gender(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum Status {
        ACTIVE("Hoạt động"),
        INACTIVE("Không hoạt động"),
        SUSPENDED("Tạm ngưng");
        
        private final String displayName;
        
        Status(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum DevelopmentalDisorderStatus {
        YES("Có"),
        NO("Không"),
        NOT_EVALUATED("Chưa đánh giá"),
        UNDER_INVESTIGATION("Đang điều tra");
        
        private final String displayName;
        
        DevelopmentalDisorderStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    

}

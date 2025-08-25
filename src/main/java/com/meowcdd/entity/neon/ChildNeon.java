package com.meowcdd.entity.neon;

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
public class ChildNeon extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
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
    
    @Column(name = "special_medical_conditions", columnDefinition = "text")
    private String specialMedicalConditions; // Tình trạng y tế đặc biệt
    
    @Column(name = "developmental_disorder_diagnosis")
    @Enumerated(EnumType.STRING)
    private DevelopmentalDisorderStatus developmentalDisorderDiagnosis; // Đã từng được chẩn đoán rối loạn phát triển
    
    @Column(name = "early_intervention_history")
    private Boolean hasEarlyIntervention; // Có từng được can thiệp sớm/chăm sóc đặc biệt
    
    @Column(name = "early_intervention_details")
    private String earlyInterventionDetails; // Chi tiết can thiệp sớm
    
    @Column(name = "primary_language")
    private String primaryLanguage; // Ngôn ngữ chủ yếu sử dụng trong gia đình
    
    @Column(name = "family_developmental_issues")
    @Enumerated(EnumType.STRING)
    private FamilyDevelopmentalIssues familyDevelopmentalIssues; // Có ai khác trong gia đình từng có vấn đề phát triển/ngôn ngữ
    
    // Các trường thông tin cơ bản khác
    @Column(name = "height")
    private Double height; // cm
    
    @Column(name = "weight")
    private Double weight; // kg
    
    @Column(name = "blood_type")
    private String bloodType; // Nhóm máu
    
    @Column(name = "allergies")
    private String allergies; // Dị ứng
    
    @Column(name = "medical_history", columnDefinition = "text")
    private String medicalHistory; // Tiền sử bệnh
    
    @Column(name = "parent_id")
    private String parentId; // ID phụ huynh
    
    @Column(name = "registration_date")
    private LocalDateTime registrationDate; // Ngày đăng ký
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status; // Trạng thái
    
    public enum Gender {
        MALE, FEMALE, OTHER
    }
    
    public enum DevelopmentalDisorderStatus {
        YES, NO, NOT_EVALUATED, UNDER_INVESTIGATION
    }
    
    public enum FamilyDevelopmentalIssues {
        NONE, PARENT, SIBLING, GRANDPARENT, OTHER
    }
    
    public enum Status {
        ACTIVE, INACTIVE, SUSPENDED
    }
    
    // Method để tính tuổi hiện tại
    public void calculateCurrentAge() {
        if (dateOfBirth != null) {
            Period period = Period.between(dateOfBirth, LocalDate.now());
            this.currentAgeMonths = period.getYears() * 12 + period.getMonths();
        }
    }
}

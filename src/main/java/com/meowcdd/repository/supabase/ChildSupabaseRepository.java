package com.meowcdd.repository.supabase;

import com.meowcdd.entity.supabase.ChildSupabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChildSupabaseRepository extends JpaRepository<ChildSupabase, Long> {
    
    /**
     * Tìm trẻ theo parent ID
     */
    List<ChildSupabase> findByParentId(String parentId);
    
    /**
     * Tìm trẻ theo tên (tìm kiếm mờ)
     */
    List<ChildSupabase> findByFullNameContainingIgnoreCase(String fullName);
    
    /**
     * Tìm trẻ theo giới tính
     */
    List<ChildSupabase> findByGender(ChildSupabase.Gender gender);
    
    /**
     * Tìm trẻ theo trạng thái
     */
    List<ChildSupabase> findByStatus(ChildSupabase.Status status);
    
    /**
     * Tìm trẻ theo độ tuổi (tháng)
     */
    @Query("SELECT c FROM ChildSupabase c WHERE c.currentAgeMonths BETWEEN :minAge AND :maxAge")
    List<ChildSupabase> findByAgeRange(@Param("minAge") Integer minAge, @Param("maxAge") Integer maxAge);
    
    /**
     * Tìm trẻ sinh non
     */
    List<ChildSupabase> findByIsPrematureTrue();
    
    /**
     * Tìm trẻ có rối loạn phát triển
     */
    List<ChildSupabase> findByDevelopmentalDisorderDiagnosis(ChildSupabase.DevelopmentalDisorderStatus status);
    
    /**
     * Tìm trẻ có can thiệp sớm
     */
    List<ChildSupabase> findByHasEarlyInterventionTrue();
    
    /**
     * Tìm trẻ theo ngôn ngữ chính
     */
    List<ChildSupabase> findByPrimaryLanguage(String primaryLanguage);
    
    /**
     * Đếm số trẻ theo giới tính
     */
    long countByGender(ChildSupabase.Gender gender);
    
    /**
     * Đếm số trẻ theo trạng thái
     */
    long countByStatus(ChildSupabase.Status status);
    
    /**
     * Tìm trẻ theo khoảng ngày sinh
     */
    @Query("SELECT c FROM ChildSupabase c WHERE c.dateOfBirth BETWEEN :startDate AND :endDate")
    List<ChildSupabase> findByDateOfBirthBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    /**
     * Tìm trẻ có cân nặng khi sinh trong khoảng
     */
    @Query("SELECT c FROM ChildSupabase c WHERE c.birthWeightGrams BETWEEN :minWeight AND :maxWeight")
    List<ChildSupabase> findByBirthWeightBetween(@Param("minWeight") Integer minWeight, @Param("maxWeight") Integer maxWeight);
}

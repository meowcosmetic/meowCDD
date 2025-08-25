package com.meowcdd.repository.neon;

import com.meowcdd.entity.neon.CDDTestNeon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CDDTestNeonRepository extends JpaRepository<CDDTestNeon, Long> {
    
    Optional<CDDTestNeon> findByAssessmentCode(String assessmentCode);
    
    List<CDDTestNeon> findByStatus(CDDTestNeon.Status status);
    
    List<CDDTestNeon> findByCategory(String category);
    
    List<CDDTestNeon> findByMinAgeMonthsLessThanEqualAndMaxAgeMonthsGreaterThanEqual(
        Integer currentAgeMonths, Integer currentAgeMonths2);
    
    @Query("SELECT c FROM CDDTestNeon c WHERE c.minAgeMonths <= :ageMonths AND c.maxAgeMonths >= :ageMonths")
    List<CDDTestNeon> findTestsForAge(@Param("ageMonths") Integer ageMonths);
    
    @Query("SELECT c FROM CDDTestNeon c WHERE c.minAgeMonths <= :ageMonths AND c.maxAgeMonths >= :ageMonths AND c.status = :status")
    Page<CDDTestNeon> findTestsForAgeAndStatusWithPagination(@Param("ageMonths") Integer ageMonths, @Param("status") CDDTestNeon.Status status, Pageable pageable);
    
    @Query("SELECT c FROM CDDTestNeon c WHERE c.minAgeMonths <= :ageMonths AND c.maxAgeMonths >= :ageMonths AND c.category = :category AND c.status = :status")
    Page<CDDTestNeon> findTestsForAgeCategoryAndStatusWithPagination(@Param("ageMonths") Integer ageMonths, @Param("category") String category, @Param("status") CDDTestNeon.Status status, Pageable pageable);
    
    @Query("SELECT c FROM CDDTestNeon c WHERE c.status = :status AND c.category = :category")
    List<CDDTestNeon> findByStatusAndCategory(@Param("status") CDDTestNeon.Status status, 
                                             @Param("category") String category);
    
    @Query("SELECT c FROM CDDTestNeon c WHERE c.administrationType = :adminType")
    List<CDDTestNeon> findByAdministrationType(@Param("adminType") CDDTestNeon.AdministrationType adminType);
    
    @Query("SELECT c FROM CDDTestNeon c WHERE c.requiredQualifications = :qualification")
    List<CDDTestNeon> findByRequiredQualifications(@Param("qualification") CDDTestNeon.RequiredQualifications qualification);
    
    @Query("SELECT c FROM CDDTestNeon c WHERE c.estimatedDuration <= :maxDuration")
    List<CDDTestNeon> findByMaxDuration(@Param("maxDuration") Integer maxDuration);
    
    @Query("SELECT c FROM CDDTestNeon c WHERE c.status = :status")
    Page<CDDTestNeon> findByStatusWithPagination(@Param("status") CDDTestNeon.Status status, Pageable pageable);
    
    @Query("SELECT c FROM CDDTestNeon c WHERE c.category = :category")
    Page<CDDTestNeon> findByCategoryWithPagination(@Param("category") String category, Pageable pageable);
    
    @Query("SELECT c FROM CDDTestNeon c WHERE c.assessmentCode LIKE %:keyword% OR c.category LIKE %:keyword%")
    Page<CDDTestNeon> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
    
    boolean existsByAssessmentCode(String assessmentCode);
    
    @Query("SELECT COUNT(c) FROM CDDTestNeon c WHERE c.status = :status")
    long countByStatus(@Param("status") CDDTestNeon.Status status);
    
    @Query("SELECT c.category, COUNT(c) FROM CDDTestNeon c GROUP BY c.category")
    List<Object[]> countByCategory();
    
    // Test method to get raw data
    @Query(value = "SELECT c.id, c.assessment_code, c.questions_json FROM cdd_tests c WHERE c.id = :id", nativeQuery = true)
    Object[] getRawTestData(@Param("id") Long id);
}


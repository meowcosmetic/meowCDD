package com.meowcdd.repository.neon;

import com.meowcdd.entity.neon.ChildNeon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChildNeonRepository extends JpaRepository<ChildNeon, Long> {
    

    
    List<ChildNeon> findByParentId(String parentId);
    
    List<ChildNeon> findByStatus(ChildNeon.Status status);
    
    List<ChildNeon> findByGender(ChildNeon.Gender gender);
    
    List<ChildNeon> findByIsPremature(Boolean isPremature);
    
    List<ChildNeon> findByDevelopmentalDisorderDiagnosis(ChildNeon.DevelopmentalDisorderStatus diagnosis);
    
    List<ChildNeon> findByHasEarlyIntervention(Boolean hasEarlyIntervention);
    
    @Query("SELECT c FROM ChildNeon c WHERE c.dateOfBirth BETWEEN :startDate AND :endDate")
    List<ChildNeon> findByDateOfBirthBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT c FROM ChildNeon c WHERE c.currentAgeMonths BETWEEN :minAge AND :maxAge")
    List<ChildNeon> findByAgeRange(@Param("minAge") Integer minAge, @Param("maxAge") Integer maxAge);
    
    @Query("SELECT c FROM ChildNeon c WHERE c.primaryLanguage = :language")
    List<ChildNeon> findByPrimaryLanguage(@Param("language") String language);
    
    @Query("SELECT c FROM ChildNeon c WHERE c.familyDevelopmentalIssues = :issue")
    List<ChildNeon> findByFamilyDevelopmentalIssues(@Param("issue") ChildNeon.FamilyDevelopmentalIssues issue);
    
    @Query("SELECT c FROM ChildNeon c WHERE c.fullName LIKE %:keyword%")
    Page<ChildNeon> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT c FROM ChildNeon c WHERE c.status = :status")
    Page<ChildNeon> findByStatusWithPagination(@Param("status") ChildNeon.Status status, Pageable pageable);
    
    @Query("SELECT c FROM ChildNeon c WHERE c.parentId = :parentId")
    Page<ChildNeon> findByParentIdWithPagination(@Param("parentId") String parentId, Pageable pageable);
    
    @Query("SELECT c FROM ChildNeon c WHERE c.gender = :gender")
    Page<ChildNeon> findByGenderWithPagination(@Param("gender") ChildNeon.Gender gender, Pageable pageable);
    
    @Query("SELECT c FROM ChildNeon c WHERE c.isPremature = :isPremature")
    Page<ChildNeon> findByIsPrematureWithPagination(@Param("isPremature") Boolean isPremature, Pageable pageable);
    
    @Query("SELECT c FROM ChildNeon c WHERE c.developmentalDisorderDiagnosis = :diagnosis")
    Page<ChildNeon> findByDevelopmentalDisorderDiagnosisWithPagination(@Param("diagnosis") ChildNeon.DevelopmentalDisorderStatus diagnosis, Pageable pageable);
    
    @Query("SELECT c FROM ChildNeon c WHERE c.hasEarlyIntervention = :hasEarlyIntervention")
    Page<ChildNeon> findByHasEarlyInterventionWithPagination(@Param("hasEarlyIntervention") Boolean hasEarlyIntervention, Pageable pageable);
    
    @Query("SELECT c FROM ChildNeon c WHERE c.primaryLanguage = :language")
    Page<ChildNeon> findByPrimaryLanguageWithPagination(@Param("language") String language, Pageable pageable);
    
    @Query("SELECT c FROM ChildNeon c WHERE c.familyDevelopmentalIssues = :issue")
    Page<ChildNeon> findByFamilyDevelopmentalIssuesWithPagination(@Param("issue") ChildNeon.FamilyDevelopmentalIssues issue, Pageable pageable);
    
    @Query("SELECT c FROM ChildNeon c WHERE c.dateOfBirth BETWEEN :startDate AND :endDate")
    Page<ChildNeon> findByDateOfBirthBetweenWithPagination(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);
    
    @Query("SELECT c FROM ChildNeon c WHERE c.currentAgeMonths BETWEEN :minAge AND :maxAge")
    Page<ChildNeon> findByAgeRangeWithPagination(@Param("minAge") Integer minAge, @Param("maxAge") Integer maxAge, Pageable pageable);
    

    
    @Query("SELECT COUNT(c) FROM ChildNeon c WHERE c.status = :status")
    long countByStatus(@Param("status") ChildNeon.Status status);
    
    @Query("SELECT COUNT(c) FROM ChildNeon c WHERE c.gender = :gender")
    long countByGender(@Param("gender") ChildNeon.Gender gender);
    
    @Query("SELECT COUNT(c) FROM ChildNeon c WHERE c.isPremature = :isPremature")
    long countByIsPremature(@Param("isPremature") Boolean isPremature);
    
    @Query("SELECT COUNT(c) FROM ChildNeon c WHERE c.developmentalDisorderDiagnosis = :diagnosis")
    long countByDevelopmentalDisorderDiagnosis(@Param("diagnosis") ChildNeon.DevelopmentalDisorderStatus diagnosis);
    
    @Query("SELECT COUNT(c) FROM ChildNeon c WHERE c.hasEarlyIntervention = :hasEarlyIntervention")
    long countByHasEarlyIntervention(@Param("hasEarlyIntervention") Boolean hasEarlyIntervention);
    
    @Query("SELECT c.primaryLanguage, COUNT(c) FROM ChildNeon c GROUP BY c.primaryLanguage")
    List<Object[]> countByPrimaryLanguage();
    
    @Query("SELECT c.familyDevelopmentalIssues, COUNT(c) FROM ChildNeon c GROUP BY c.familyDevelopmentalIssues")
    List<Object[]> countByFamilyDevelopmentalIssues();
}

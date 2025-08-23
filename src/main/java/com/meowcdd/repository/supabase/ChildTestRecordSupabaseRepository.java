package com.meowcdd.repository.supabase;

import com.meowcdd.entity.supabase.ChildTestRecordSupabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChildTestRecordSupabaseRepository extends JpaRepository<ChildTestRecordSupabase, Long> {
    
    List<ChildTestRecordSupabase> findByChildId(Long childId);
    
    List<ChildTestRecordSupabase> findByChildIdOrderByTestDateDesc(Long childId);
    
    List<ChildTestRecordSupabase> findByTestId(Long testId);
    
    List<ChildTestRecordSupabase> findByTestType(ChildTestRecordSupabase.TestType testType);
    
    List<ChildTestRecordSupabase> findByStatus(ChildTestRecordSupabase.Status status);
    
    List<ChildTestRecordSupabase> findByChildIdAndTestType(Long childId, ChildTestRecordSupabase.TestType testType);
    
    List<ChildTestRecordSupabase> findByChildIdAndStatus(Long childId, ChildTestRecordSupabase.Status status);
    
    @Query("SELECT ctr FROM ChildTestRecordSupabase ctr WHERE ctr.childId = :childId AND ctr.testDate BETWEEN :startDate AND :endDate")
    List<ChildTestRecordSupabase> findByChildIdAndTestDateBetween(@Param("childId") Long childId, 
                                                                 @Param("startDate") LocalDateTime startDate, 
                                                                 @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT ctr FROM ChildTestRecordSupabase ctr WHERE ctr.testDate BETWEEN :startDate AND :endDate")
    List<ChildTestRecordSupabase> findByTestDateBetween(@Param("startDate") LocalDateTime startDate, 
                                                       @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT ctr FROM ChildTestRecordSupabase ctr WHERE ctr.resultLevel = :resultLevel")
    List<ChildTestRecordSupabase> findByResultLevel(@Param("resultLevel") ChildTestRecordSupabase.ResultLevel resultLevel);
    
    @Query("SELECT ctr FROM ChildTestRecordSupabase ctr WHERE ctr.childId = :childId AND ctr.resultLevel = :resultLevel")
    List<ChildTestRecordSupabase> findByChildIdAndResultLevel(@Param("childId") Long childId, 
                                                             @Param("resultLevel") ChildTestRecordSupabase.ResultLevel resultLevel);
    
    @Query("SELECT ctr FROM ChildTestRecordSupabase ctr WHERE ctr.percentageScore >= :minScore AND ctr.percentageScore <= :maxScore")
    List<ChildTestRecordSupabase> findByPercentageScoreBetween(@Param("minScore") Double minScore, 
                                                              @Param("maxScore") Double maxScore);
    
    @Query("SELECT ctr FROM ChildTestRecordSupabase ctr WHERE ctr.assessor = :assessor")
    List<ChildTestRecordSupabase> findByAssessor(@Param("assessor") String assessor);
    
    @Query("SELECT ctr FROM ChildTestRecordSupabase ctr WHERE ctr.environment = :environment")
    List<ChildTestRecordSupabase> findByEnvironment(@Param("environment") String environment);
    
    @Query("SELECT ctr FROM ChildTestRecordSupabase ctr WHERE ctr.parentPresent = :parentPresent")
    List<ChildTestRecordSupabase> findByParentPresent(@Param("parentPresent") Boolean parentPresent);
    
    @Query("SELECT COUNT(ctr) FROM ChildTestRecordSupabase ctr WHERE ctr.childId = :childId AND ctr.status = 'COMPLETED'")
    long countCompletedTestsByChildId(@Param("childId") Long childId);
    
    @Query("SELECT AVG(ctr.percentageScore) FROM ChildTestRecordSupabase ctr WHERE ctr.childId = :childId AND ctr.status = 'COMPLETED'")
    Double getAverageScoreByChildId(@Param("childId") Long childId);
    
    @Query("SELECT MAX(ctr.testDate) FROM ChildTestRecordSupabase ctr WHERE ctr.childId = :childId")
    LocalDateTime getLastTestDateByChildId(@Param("childId") Long childId);
    
    boolean existsByChildIdAndTestId(Long childId, Long testId);
}

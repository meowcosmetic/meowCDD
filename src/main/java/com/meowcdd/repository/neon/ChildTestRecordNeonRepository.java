package com.meowcdd.repository.neon;

import com.meowcdd.entity.neon.ChildTestRecordNeon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChildTestRecordNeonRepository extends JpaRepository<ChildTestRecordNeon, Long> {
    
    List<ChildTestRecordNeon> findByChildId(String childId);
    
    List<ChildTestRecordNeon> findByTestId(String testId);
    
    List<ChildTestRecordNeon> findByStatus(ChildTestRecordNeon.Status status);
    
    List<ChildTestRecordNeon> findByAssessor(String assessor);
    
    @Query("SELECT ctr FROM ChildTestRecordNeon ctr WHERE ctr.childId = :childId AND ctr.testId = :testId")
    List<ChildTestRecordNeon> findByChildIdAndTestId(@Param("childId") String childId, @Param("testId") String testId);
    
    @Query("SELECT ctr FROM ChildTestRecordNeon ctr WHERE ctr.testDate BETWEEN :startDate AND :endDate")
    List<ChildTestRecordNeon> findByTestDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT ctr FROM ChildTestRecordNeon ctr WHERE ctr.childId = :childId AND ctr.testDate BETWEEN :startDate AND :endDate")
    List<ChildTestRecordNeon> findByChildIdAndTestDateBetween(@Param("childId") String childId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT ctr FROM ChildTestRecordNeon ctr WHERE ctr.totalScore >= :minScore AND ctr.totalScore <= :maxScore")
    List<ChildTestRecordNeon> findByScoreBetween(@Param("minScore") Double minScore, @Param("maxScore") Double maxScore);
    
    @Query("SELECT ctr FROM ChildTestRecordNeon ctr WHERE ctr.childId = :childId")
    Page<ChildTestRecordNeon> findByChildIdWithPagination(@Param("childId") String childId, Pageable pageable);
    
    @Query("SELECT ctr FROM ChildTestRecordNeon ctr WHERE ctr.testId = :testId")
    Page<ChildTestRecordNeon> findByTestIdWithPagination(@Param("testId") String testId, Pageable pageable);
    
    @Query("SELECT ctr FROM ChildTestRecordNeon ctr WHERE ctr.status = :status")
    Page<ChildTestRecordNeon> findByStatusWithPagination(@Param("status") ChildTestRecordNeon.Status status, Pageable pageable);
    
    @Query("SELECT ctr FROM ChildTestRecordNeon ctr WHERE ctr.assessor = :assessor")
    Page<ChildTestRecordNeon> findByAssessorWithPagination(@Param("assessor") String assessor, Pageable pageable);
    
    @Query("SELECT ctr FROM ChildTestRecordNeon ctr WHERE ctr.testDate BETWEEN :startDate AND :endDate")
    Page<ChildTestRecordNeon> findByTestDateBetweenWithPagination(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);
    
    @Query("SELECT ctr FROM ChildTestRecordNeon ctr WHERE ctr.totalScore >= :minScore AND ctr.totalScore <= :maxScore")
    Page<ChildTestRecordNeon> findByScoreBetweenWithPagination(@Param("minScore") Double minScore, @Param("maxScore") Double maxScore, Pageable pageable);
    
    @Query("SELECT COUNT(ctr) FROM ChildTestRecordNeon ctr WHERE ctr.childId = :childId")
    long countByChildId(@Param("childId") String childId);
    
    @Query("SELECT COUNT(ctr) FROM ChildTestRecordNeon ctr WHERE ctr.testId = :testId")
    long countByTestId(@Param("testId") String testId);
    
    @Query("SELECT COUNT(ctr) FROM ChildTestRecordNeon ctr WHERE ctr.status = :status")
    long countByStatus(@Param("status") ChildTestRecordNeon.Status status);
    
    @Query("SELECT COUNT(ctr) FROM ChildTestRecordNeon ctr WHERE ctr.assessor = :assessor")
    long countByAssessor(@Param("assessor") String assessor);
    
    @Query("SELECT AVG(ctr.totalScore) FROM ChildTestRecordNeon ctr WHERE ctr.childId = :childId")
    Double getAverageScoreByChildId(@Param("childId") String childId);
    
    @Query("SELECT AVG(ctr.totalScore) FROM ChildTestRecordNeon ctr WHERE ctr.testId = :testId")
    Double getAverageScoreByTestId(@Param("testId") String testId);
    
    @Query("SELECT DISTINCT ctr.testType FROM ChildTestRecordNeon ctr WHERE ctr.testType IS NOT NULL ORDER BY ctr.testType")
    List<String> findDistinctCategories();
    
    @Query("SELECT DISTINCT ctr.testType FROM ChildTestRecordNeon ctr WHERE ctr.childId = :childId AND ctr.testType IS NOT NULL ORDER BY ctr.testType")
    List<String> findDistinctCategoriesByChildId(@Param("childId") String childId);
    
    @Query("SELECT ctr FROM ChildTestRecordNeon ctr WHERE ctr.childId = :childId ORDER BY ctr.testDate DESC LIMIT 1")
    Optional<ChildTestRecordNeon> findLatestByChildId(@Param("childId") String childId);
    
    @Query("SELECT ctr FROM ChildTestRecordNeon ctr WHERE ctr.childId = :childId ORDER BY ctr.testDate DESC")
    List<ChildTestRecordNeon> findLatestByChildIdWithLimit(@Param("childId") String childId, Pageable pageable);
    
    @Query(value = "WITH latest AS (" +
           "SELECT *, ROW_NUMBER() OVER (PARTITION BY test_id ORDER BY test_date DESC) AS rn " +
           "FROM child_test_records WHERE child_id = :childId" +
           ") SELECT ctr.id, ctr.child_id, ctr.test_id, ctr.test_type, ctr.test_date, ctr.start_time, ctr.end_time, " +
           "ctr.status, ctr.total_score, ctr.max_score, ctr.percentage_score, ctr.result_level, ctr.interpretation, " +
           "ctr.question_answers, ctr.correct_answers, ctr.total_questions, ctr.skipped_questions, ctr.notes, " +
           "ctr.environment, ctr.assessor, ctr.parent_present, ctr.created_at, ctr.updated_at, ct.category as test_category " +
           "FROM latest ctr LEFT JOIN cdd_tests ct ON CAST(ctr.test_id AS INTEGER) = ct.id " +
           "WHERE ctr.rn = 1 ORDER BY ctr.test_date DESC", nativeQuery = true)
    List<Object[]> findLatestByChildIdAndDistinctCategoryWithTestInfo(@Param("childId") String childId);
    
    @Query("SELECT ctr FROM ChildTestRecordNeon ctr WHERE ctr.childId = :childId AND ctr.testType = :category ORDER BY ctr.testDate DESC LIMIT 1")
    Optional<ChildTestRecordNeon> findLatestByChildIdAndCategory(@Param("childId") String childId, @Param("category") String category);
    
    @Query("SELECT ctr FROM ChildTestRecordNeon ctr WHERE ctr.childId = :childId AND ctr.testType = :category ORDER BY ctr.testDate DESC")
    List<ChildTestRecordNeon> findLatestByChildIdAndCategoryWithLimit(@Param("childId") String childId, @Param("category") String category, Pageable pageable);
}

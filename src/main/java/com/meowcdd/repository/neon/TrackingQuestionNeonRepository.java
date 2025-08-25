package com.meowcdd.repository.neon;

import com.meowcdd.entity.neon.TrackingQuestionNeon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackingQuestionNeonRepository extends JpaRepository<TrackingQuestionNeon, Long> {
    
    List<TrackingQuestionNeon> findByCategory(String category);
    
    List<TrackingQuestionNeon> findBySubcategory(String subcategory);
    
    List<TrackingQuestionNeon> findByQuestionType(TrackingQuestionNeon.QuestionType questionType);
    
    List<TrackingQuestionNeon> findByDifficultyLevel(TrackingQuestionNeon.DifficultyLevel difficultyLevel);
    
    List<TrackingQuestionNeon> findByStatus(TrackingQuestionNeon.Status status);
    
    @Query("SELECT tq FROM TrackingQuestionNeon tq WHERE tq.ageRangeMin <= :age AND tq.ageRangeMax >= :age")
    List<TrackingQuestionNeon> findByAgeRange(@Param("age") Integer age);
    
    @Query("SELECT tq FROM TrackingQuestionNeon tq WHERE tq.category = :category AND tq.subcategory = :subcategory")
    List<TrackingQuestionNeon> findByCategoryAndSubcategory(@Param("category") String category, @Param("subcategory") String subcategory);
    
    @Query("SELECT tq FROM TrackingQuestionNeon tq WHERE tq.questionType = :questionType AND tq.difficultyLevel = :difficultyLevel")
    List<TrackingQuestionNeon> findByQuestionTypeAndDifficultyLevel(@Param("questionType") TrackingQuestionNeon.QuestionType questionType, @Param("difficultyLevel") TrackingQuestionNeon.DifficultyLevel difficultyLevel);
    
    @Query("SELECT tq FROM TrackingQuestionNeon tq WHERE tq.estimatedTimeSeconds <= :maxTime")
    List<TrackingQuestionNeon> findByMaxEstimatedTime(@Param("maxTime") Integer maxTime);
    
    @Query("SELECT tq FROM TrackingQuestionNeon tq WHERE tq.questionText LIKE %:keyword% OR tq.category LIKE %:keyword% OR tq.subcategory LIKE %:keyword%")
    Page<TrackingQuestionNeon> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT tq FROM TrackingQuestionNeon tq WHERE tq.category = :category")
    Page<TrackingQuestionNeon> findByCategoryWithPagination(@Param("category") String category, Pageable pageable);
    
    @Query("SELECT tq FROM TrackingQuestionNeon tq WHERE tq.subcategory = :subcategory")
    Page<TrackingQuestionNeon> findBySubcategoryWithPagination(@Param("subcategory") String subcategory, Pageable pageable);
    
    @Query("SELECT tq FROM TrackingQuestionNeon tq WHERE tq.questionType = :questionType")
    Page<TrackingQuestionNeon> findByQuestionTypeWithPagination(@Param("questionType") TrackingQuestionNeon.QuestionType questionType, Pageable pageable);
    
    @Query("SELECT tq FROM TrackingQuestionNeon tq WHERE tq.difficultyLevel = :difficultyLevel")
    Page<TrackingQuestionNeon> findByDifficultyLevelWithPagination(@Param("difficultyLevel") TrackingQuestionNeon.DifficultyLevel difficultyLevel, Pageable pageable);
    
    @Query("SELECT tq FROM TrackingQuestionNeon tq WHERE tq.status = :status")
    Page<TrackingQuestionNeon> findByStatusWithPagination(@Param("status") TrackingQuestionNeon.Status status, Pageable pageable);
    
    @Query("SELECT tq FROM TrackingQuestionNeon tq WHERE tq.ageRangeMin <= :age AND tq.ageRangeMax >= :age")
    Page<TrackingQuestionNeon> findByAgeRangeWithPagination(@Param("age") Integer age, Pageable pageable);
    
    @Query("SELECT COUNT(tq) FROM TrackingQuestionNeon tq WHERE tq.category = :category")
    long countByCategory(@Param("category") String category);
    
    @Query("SELECT COUNT(tq) FROM TrackingQuestionNeon tq WHERE tq.subcategory = :subcategory")
    long countBySubcategory(@Param("subcategory") String subcategory);
    
    @Query("SELECT COUNT(tq) FROM TrackingQuestionNeon tq WHERE tq.questionType = :questionType")
    long countByQuestionType(@Param("questionType") TrackingQuestionNeon.QuestionType questionType);
    
    @Query("SELECT COUNT(tq) FROM TrackingQuestionNeon tq WHERE tq.difficultyLevel = :difficultyLevel")
    long countByDifficultyLevel(@Param("difficultyLevel") TrackingQuestionNeon.DifficultyLevel difficultyLevel);
    
    @Query("SELECT COUNT(tq) FROM TrackingQuestionNeon tq WHERE tq.status = :status")
    long countByStatus(@Param("status") TrackingQuestionNeon.Status status);
    
    @Query("SELECT tq.category, COUNT(tq) FROM TrackingQuestionNeon tq GROUP BY tq.category")
    List<Object[]> countByCategoryGroup();
    
    @Query("SELECT tq.questionType, COUNT(tq) FROM TrackingQuestionNeon tq GROUP BY tq.questionType")
    List<Object[]> countByQuestionTypeGroup();
    
    @Query("SELECT tq.difficultyLevel, COUNT(tq) FROM TrackingQuestionNeon tq GROUP BY tq.difficultyLevel")
    List<Object[]> countByDifficultyLevelGroup();
}

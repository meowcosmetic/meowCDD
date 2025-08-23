package com.meowcdd.repository.supabase;

import com.meowcdd.entity.supabase.TrackingQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackingQuestionSupabaseRepository extends JpaRepository<TrackingQuestion, Long> {
    
    /**
     * Tìm tất cả câu hỏi theo domain
     */
    List<TrackingQuestion> findByDomain(String domain);
    
    /**
     * Tìm tất cả câu hỏi theo độ tuổi
     */
    List<TrackingQuestion> findByAgeRange(String ageRange);
    
    /**
     * Tìm tất cả câu hỏi theo tần suất
     */
    List<TrackingQuestion> findByFrequency(String frequency);
    
    /**
     * Tìm tất cả câu hỏi theo domain và độ tuổi
     */
    List<TrackingQuestion> findByDomainAndAgeRange(String domain, String ageRange);
    
    /**
     * Tìm tất cả câu hỏi theo domain, độ tuổi và tần suất
     */
    List<TrackingQuestion> findByDomainAndAgeRangeAndFrequency(String domain, String ageRange, String frequency);
    
    /**
     * Tìm câu hỏi theo context (sử dụng JSON search)
     */
    @Query(value = "SELECT * FROM tracking_questions WHERE context::text LIKE %:context%", nativeQuery = true)
    List<TrackingQuestion> findByContextContaining(@Param("context") String context);
    
    /**
     * Tìm tất cả câu hỏi có sẵn cho một độ tuổi cụ thể
     */
    @Query(value = "SELECT * FROM tracking_questions WHERE age_range = :ageRange ORDER BY domain, id", nativeQuery = true)
    List<TrackingQuestion> findAvailableQuestionsByAgeRange(@Param("ageRange") String ageRange);
}

package com.meowcdd.repository.supabase;

import com.meowcdd.entity.supabase.CDDTestSupabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CDDTestSupabaseRepository extends JpaRepository<CDDTestSupabase, Long> {

    Optional<CDDTestSupabase> findByAssessmentCode(String assessmentCode);

    List<CDDTestSupabase> findByStatus(CDDTestSupabase.Status status);

    List<CDDTestSupabase> findByCategory(String category);

    @Query("SELECT c FROM CDDTestSupabase c WHERE c.minAgeMonths <= :ageMonths AND c.maxAgeMonths >= :ageMonths")
    List<CDDTestSupabase> findByAgeMonths(@Param("ageMonths") Integer ageMonths);
    
    @Query("SELECT c FROM CDDTestSupabase c WHERE c.minAgeMonths <= :ageMonths AND c.maxAgeMonths >= :ageMonths AND c.status = :status")
    List<CDDTestSupabase> findByAgeMonthsAndStatus(@Param("ageMonths") Integer ageMonths, @Param("status") CDDTestSupabase.Status status);
    
    @Query("SELECT c FROM CDDTestSupabase c WHERE c.minAgeMonths <= :ageMonths AND c.maxAgeMonths >= :ageMonths AND c.category = :category")
    List<CDDTestSupabase> findByAgeMonthsAndCategory(@Param("ageMonths") Integer ageMonths, @Param("category") String category);
    
    @Query("SELECT c FROM CDDTestSupabase c WHERE c.minAgeMonths <= :ageMonths AND c.maxAgeMonths >= :ageMonths AND c.status = :status AND c.category = :category")
    List<CDDTestSupabase> findByAgeMonthsAndStatusAndCategory(@Param("ageMonths") Integer ageMonths, @Param("status") CDDTestSupabase.Status status, @Param("category") String category);

    @Query("SELECT c FROM CDDTestSupabase c WHERE c.status = 'ACTIVE'")
    List<CDDTestSupabase> findActiveTests();

    boolean existsByAssessmentCode(String assessmentCode);
}


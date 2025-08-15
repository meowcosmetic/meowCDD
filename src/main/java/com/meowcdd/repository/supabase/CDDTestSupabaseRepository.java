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

    Optional<CDDTestSupabase> findByCode(String code);

    List<CDDTestSupabase> findByStatus(CDDTestSupabase.Status status);

    List<CDDTestSupabase> findByCategory(String category);

    @Query("SELECT c FROM CDDTestSupabase c WHERE c.minAgeMonths <= :ageMonths AND c.maxAgeMonths >= :ageMonths")
    List<CDDTestSupabase> findByAgeMonths(@Param("ageMonths") Integer ageMonths);

    @Query("SELECT c FROM CDDTestSupabase c WHERE c.status = 'ACTIVE'")
    List<CDDTestSupabase> findActiveTests();

    boolean existsByCode(String code);
}


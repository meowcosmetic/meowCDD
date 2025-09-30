package com.meowcdd.repository.neon;

import com.meowcdd.entity.neon.InterventionMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterventionMethodRepository extends JpaRepository<InterventionMethod, Long> {
    
    // Filter out soft deleted records
    @Query("SELECT e FROM InterventionMethod e WHERE e.deletedAt IS NULL")
    List<InterventionMethod> findAllActive();
    
    @Query("SELECT e FROM InterventionMethod e WHERE e.deletedAt IS NULL")
    Page<InterventionMethod> findAllActive(Pageable pageable);
    
    @Query("SELECT e FROM InterventionMethod e WHERE e.code = :code AND e.deletedAt IS NULL")
    Optional<InterventionMethod> findByCodeActive(@Param("code") String code);
    
    @Query("SELECT COUNT(e) > 0 FROM InterventionMethod e WHERE e.code = :code AND e.deletedAt IS NULL")
    boolean existsByCodeActive(@Param("code") String code);
    
    @Query("SELECT e FROM InterventionMethod e WHERE e.id = :id AND e.deletedAt IS NULL")
    Optional<InterventionMethod> findByIdActive(@Param("id") Long id);
    
    // Keep original methods for internal use (including soft deleted)
    boolean existsByCode(String code);
    Optional<InterventionMethod> findByCode(String code);
    Page<InterventionMethod> findAll(Pageable pageable);
}


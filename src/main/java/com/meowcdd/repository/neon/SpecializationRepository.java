package com.meowcdd.repository.neon;

import com.meowcdd.entity.neon.Specialization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, UUID> {
    
    Optional<Specialization> findBySpecializationName(String specializationName);
    
    List<Specialization> findBySpecializationType(String specializationType);
    
    List<Specialization> findByCategory(String category);
    
    List<Specialization> findByIsActive(Boolean isActive);
    
    List<Specialization> findByStatus(Specialization.Status status);
    
    @Query("SELECT s FROM Specialization s WHERE s.specializationName LIKE %:name% OR s.description LIKE %:description%")
    List<Specialization> searchBySpecializationNameOrDescription(@Param("name") String name, @Param("description") String description);
    
    @Query("SELECT DISTINCT s.specializationType FROM Specialization s WHERE s.isActive = true ORDER BY s.specializationType")
    List<String> findDistinctSpecializationTypes();
    
    @Query("SELECT DISTINCT s.category FROM Specialization s WHERE s.isActive = true AND s.category IS NOT NULL ORDER BY s.category")
    List<String> findDistinctCategories();
    
    @Query("SELECT s FROM Specialization s WHERE s.isActive = true ORDER BY s.specializationName")
    List<Specialization> findActiveSpecializations();
    
    @Query("SELECT s FROM Specialization s WHERE s.minExperienceYears <= :experienceYears AND s.isActive = true")
    List<Specialization> findSpecializationsByMinExperience(@Param("experienceYears") Integer experienceYears);
    
    Page<Specialization> findByIsActive(Boolean isActive, Pageable pageable);
}

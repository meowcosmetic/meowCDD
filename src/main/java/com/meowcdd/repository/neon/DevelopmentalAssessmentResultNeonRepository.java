package com.meowcdd.repository.neon;

import com.meowcdd.entity.neon.DevelopmentalAssessmentResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DevelopmentalAssessmentResultNeonRepository extends JpaRepository<DevelopmentalAssessmentResult, Long> {
    Page<DevelopmentalAssessmentResult> findByChild_Id(Long childId, Pageable pageable);
    List<DevelopmentalAssessmentResult> findByProgram_Id(Long programId);
    List<DevelopmentalAssessmentResult> findByCriteria_Id(Long criteriaId);
}



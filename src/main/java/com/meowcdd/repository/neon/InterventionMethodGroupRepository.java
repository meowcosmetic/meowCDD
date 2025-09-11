package com.meowcdd.repository.neon;

import com.meowcdd.entity.neon.InterventionMethodGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterventionMethodGroupRepository extends JpaRepository<InterventionMethodGroup, Long> {
    boolean existsByCode(String code);
    Optional<InterventionMethodGroup> findByCode(String code);
    Page<InterventionMethodGroup> findAll(Pageable pageable);
}



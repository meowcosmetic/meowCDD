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
    boolean existsByCode(String code);
    Optional<InterventionMethod> findByCode(String code);
    Page<InterventionMethod> findAll(Pageable pageable);
}


package com.meowcdd.repository.neon;

import com.meowcdd.entity.neon.DevelopmentalProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DevelopmentalProgramNeonRepository extends JpaRepository<DevelopmentalProgram, Long> {
    boolean existsByCode(String code);
    Optional<DevelopmentalProgram> findByCode(String code);

    @Query("SELECT dp FROM DevelopmentalProgram dp WHERE " +
           "(:keyword IS NULL OR LOWER(dp.code) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           " OR LOWER(dp.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           " OR LOWER(dp.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<DevelopmentalProgram> search(String keyword, Pageable pageable);
}



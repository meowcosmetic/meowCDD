package com.meowcdd.repository.neon;

import com.meowcdd.entity.neon.DevelopmentalItemCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DevelopmentalItemCriteriaNeonRepository extends JpaRepository<DevelopmentalItemCriteria, Long> {
    List<DevelopmentalItemCriteria> findByItem_Id(Long itemId);

    @Query("SELECT c FROM DevelopmentalItemCriteria c WHERE " +
           "(:keyword IS NULL OR LOWER(c.code) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           " OR LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<DevelopmentalItemCriteria> search(String keyword, Pageable pageable);
}



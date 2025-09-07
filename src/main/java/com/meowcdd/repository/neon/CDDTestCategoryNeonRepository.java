package com.meowcdd.repository.neon;

import com.meowcdd.entity.neon.CDDTestCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CDDTestCategoryNeonRepository extends JpaRepository<CDDTestCategory, Long> {

    boolean existsByCode(String code);

    @Query("SELECT c FROM CDDTestCategory c WHERE " +
           "(:keyword IS NULL OR LOWER(c.code) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           " OR CAST(c.displayedName AS string) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           " OR CAST(c.description AS string) LIKE LOWER(CONCAT('%', :keyword, '%')))" )
    Page<CDDTestCategory> search(String keyword, Pageable pageable);
}



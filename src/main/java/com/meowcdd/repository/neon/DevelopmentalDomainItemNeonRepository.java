package com.meowcdd.repository.neon;

import com.meowcdd.entity.neon.DevelopmentalDomainItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DevelopmentalDomainItemNeonRepository extends JpaRepository<DevelopmentalDomainItem, Long> {
    List<DevelopmentalDomainItem> findByDomain_Id(UUID domainId);

    @Query("SELECT i FROM DevelopmentalDomainItem i WHERE " +
           "(:keyword IS NULL OR LOWER(i.code) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           " OR LOWER(i.title) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<DevelopmentalDomainItem> search(String keyword, Pageable pageable);
}



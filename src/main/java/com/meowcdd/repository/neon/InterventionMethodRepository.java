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

    List<InterventionMethod> findByDeletedAtIsNull();

    @Query("SELECT im FROM InterventionMethod im WHERE im.deletedAt IS NULL")
    Page<InterventionMethod> findAllActive(Pageable pageable);

    @Query("SELECT im FROM InterventionMethod im WHERE im.deletedAt IS NULL AND " +
           "(LOWER(im.displayedName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(im.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(im.code) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<InterventionMethod> search(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT im FROM InterventionMethod im WHERE im.deletedAt IS NULL AND " +
           "(:minAge IS NULL OR im.minAgeMonths IS NULL OR im.minAgeMonths <= :minAge) AND " +
           "(:maxAge IS NULL OR im.maxAgeMonths IS NULL OR im.maxAgeMonths >= :maxAge)")
    List<InterventionMethod> findByAgeRange(@Param("minAge") Integer minAge, @Param("maxAge") Integer maxAge);
}


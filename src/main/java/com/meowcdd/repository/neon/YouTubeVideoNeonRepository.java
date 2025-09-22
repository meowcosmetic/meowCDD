package com.meowcdd.repository.neon;

import com.meowcdd.entity.neon.YouTubeVideo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YouTubeVideoNeonRepository extends JpaRepository<YouTubeVideo, Long> {

    List<YouTubeVideo> findByLanguage(String language);
    List<YouTubeVideo> findByIsActiveTrue();
    List<YouTubeVideo> findByIsFeaturedTrue();
    List<YouTubeVideo> findByIsActiveTrueAndIsFeaturedTrue();

    List<YouTubeVideo> findBySupportedFormatId(Long supportedFormatId);
    Page<YouTubeVideo> findBySupportedFormatId(Long supportedFormatId, Pageable pageable);

    @Query("SELECT v FROM YouTubeVideo v JOIN v.developmentalDomains dd WHERE dd.id = :domainId")
    List<YouTubeVideo> findByDevelopmentalDomainId(@Param("domainId") java.util.UUID domainId);

    @Query("SELECT v FROM YouTubeVideo v WHERE (:title IS NULL OR CAST(v.title AS string) LIKE CONCAT('%', :title, '%')) AND (:language IS NULL OR v.language = :language) AND (:isActive IS NULL OR v.isActive = :isActive) AND (:isFeatured IS NULL OR v.isFeatured = :isFeatured)")
    Page<YouTubeVideo> findByFilters(@Param("title") String title,
                                     @Param("language") String language,
                                     @Param("isActive") Boolean isActive,
                                     @Param("isFeatured") Boolean isFeatured,
                                     Pageable pageable);

    @Query("SELECT v FROM YouTubeVideo v WHERE CAST(v.title AS string) LIKE CONCAT('%', :keyword, '%') OR CAST(v.description AS string) LIKE CONCAT('%', :keyword, '%') OR LOWER(v.keywords) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<YouTubeVideo> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}

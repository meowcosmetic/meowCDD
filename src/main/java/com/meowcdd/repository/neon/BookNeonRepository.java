package com.meowcdd.repository.neon;

import com.meowcdd.entity.neon.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Repository cho entity Book
 * Cung cấp các method truy vấn cơ bản và tùy chỉnh
 */
@Repository
public interface BookNeonRepository extends JpaRepository<Book, Long> {
    
    // === TÌM KIẾM THEO TIÊU ĐỀ ===
    Optional<Book> findByTitle(String title);
    List<Book> findByTitleContainingIgnoreCase(String title);
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    
    // === TÌM KIẾM THEO TÁC GIẢ ===
    List<Book> findByAuthorContainingIgnoreCase(String author);
    Page<Book> findByAuthorContainingIgnoreCase(String author, Pageable pageable);
    
    // === TÌM KIẾM THEO NHÀ XUẤT BẢN ===
    List<Book> findByPublisherContainingIgnoreCase(String publisher);
    
    // === TÌM KIẾM THEO ISBN ===
    Optional<Book> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);
    
    // === TÌM KIẾM THEO ĐỊNH DẠNG FILE ===
    List<Book> findBySupportedFormatId(Long supportedFormatId);
    Page<Book> findBySupportedFormatId(Long supportedFormatId, Pageable pageable);
    
    // === TÌM KIẾM THEO LĨNH VỰC PHÁT TRIỂN ===
    @Query("SELECT b FROM Book b JOIN b.developmentalDomains dd WHERE dd.id = :domainId")
    List<Book> findByDevelopmentalDomainId(@Param("domainId") UUID domainId);
    
    @Query("SELECT b FROM Book b JOIN b.developmentalDomains dd WHERE dd.id IN :domainIds")
    List<Book> findByDevelopmentalDomainIds(@Param("domainIds") Set<UUID> domainIds);
    
    // === TÌM KIẾM THEO ĐỘ TUỔI ===
    List<Book> findByMinAgeLessThanEqualAndMaxAgeGreaterThanEqual(Integer age, Integer maxAge);
    List<Book> findByAgeGroup(String ageGroup);
    
    // === TÌM KIẾM THEO NGÔN NGỮ ===
    List<Book> findByLanguage(String language);
    
    // === TÌM KIẾM THEO TRẠNG THÁI ===
    List<Book> findByIsActiveTrue();
    List<Book> findByIsFeaturedTrue();
    List<Book> findByIsActiveTrueAndIsFeaturedTrue();
    
    // === TÌM KIẾM THEO ĐÁNH GIÁ ===
    List<Book> findByAverageRatingGreaterThanEqual(Double minRating);
    List<Book> findByTotalRatingsGreaterThanEqual(Integer minRatings);
    
    // === TÌM KIẾM THEO NĂM XUẤT BẢN ===
    List<Book> findByPublicationYear(Integer year);
    List<Book> findByPublicationYearBetween(Integer startYear, Integer endYear);
    
    // === TÌM KIẾM TỔNG HỢP ===
    @Query("SELECT b FROM Book b WHERE " +
           "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))) AND " +
           "(:language IS NULL OR b.language = :language) AND " +
           "(:ageGroup IS NULL OR b.ageGroup = :ageGroup) AND " +
           "(:isActive IS NULL OR b.isActive = :isActive) AND " +
           "(:isFeatured IS NULL OR b.isFeatured = :isFeatured)")
    Page<Book> findByFilters(
        @Param("title") String title,
        @Param("author") String author,
        @Param("language") String language,
        @Param("ageGroup") String ageGroup,
        @Param("isActive") Boolean isActive,
        @Param("isFeatured") Boolean isFeatured,
        Pageable pageable
    );
    
    // === THỐNG KÊ ===
    @Query("SELECT COUNT(b) FROM Book b WHERE b.isActive = true")
    long countActiveBooks();
    
    @Query("SELECT COUNT(b) FROM Book b WHERE b.isFeatured = true")
    long countFeaturedBooks();
    
    @Query("SELECT AVG(b.averageRating) FROM Book b WHERE b.averageRating IS NOT NULL")
    Double getAverageRatingOfAllBooks();
    
    // === TÌM KIẾM THEO TỪ KHÓA ===
    @Query("SELECT b FROM Book b WHERE " +
           "LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(b.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(b.keywords) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Book> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}

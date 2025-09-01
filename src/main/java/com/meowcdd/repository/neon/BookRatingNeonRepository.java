package com.meowcdd.repository.neon;

import com.meowcdd.entity.neon.BookRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository cho entity BookRating
 * Cung cấp các method truy vấn cơ bản và tùy chỉnh
 */
@Repository
public interface BookRatingNeonRepository extends JpaRepository<BookRating, Long> {
    
    // === TÌM KIẾM THEO SÁCH ===
    List<BookRating> findByBook_Id(Long bookId);
    Page<BookRating> findByBook_Id(Long bookId, Pageable pageable);
    
    // === TÌM KIẾM THEO PHỤ HUYNH ===
    List<BookRating> findByParentId(String parentId);
    Page<BookRating> findByParentId(String parentId, Pageable pageable);
    
    // === TÌM KIẾM THEO SÁCH VÀ PHỤ HUYNH ===
    Optional<BookRating> findByBook_IdAndParentId(Long bookId, String parentId);
    boolean existsByBook_IdAndParentId(Long bookId, String parentId);
    
    // === TÌM KIẾM THEO ĐIỂM ĐÁNH GIÁ ===
    List<BookRating> findByRating(Integer rating);
    List<BookRating> findByRatingGreaterThanEqual(Integer minRating);
    List<BookRating> findByRatingLessThanEqual(Integer maxRating);
    List<BookRating> findByRatingBetween(Integer minRating, Integer maxRating);
    
    // === TÌM KIẾM THEO LOẠI ĐÁNH GIÁ ===
    List<BookRating> findByRatingType(BookRating.RatingType ratingType);
    Page<BookRating> findByRatingType(BookRating.RatingType ratingType, Pageable pageable);
    
    // === TÌM KIẾM THEO NGÀY ĐÁNH GIÁ ===
    List<BookRating> findByRatingDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<BookRating> findByRatingDateAfter(LocalDateTime date);
    List<BookRating> findByRatingDateBefore(LocalDateTime date);
    
    // === TÌM KIẾM THEO TRẠNG THÁI CHỈNH SỬA ===
    List<BookRating> findByIsEditedTrue();
    List<BookRating> findByIsEditedFalse();
    
    // === TÌM KIẾM THEO SÁCH VÀ LOẠI ĐÁNH GIÁ ===
    List<BookRating> findByBook_IdAndRatingType(Long bookId, BookRating.RatingType ratingType);
    
    // === TÌM KIẾM THEO PHỤ HUYNH VÀ LOẠI ĐÁNH GIÁ ===
    List<BookRating> findByParentIdAndRatingType(String parentId, BookRating.RatingType ratingType);
    
    // === THỐNG KÊ ===
    @Query("SELECT AVG(br.rating) FROM BookRating br WHERE br.book.id = :bookId")
    Double getAverageRatingByBookId(@Param("bookId") Long bookId);
    
    @Query("SELECT COUNT(br) FROM BookRating br WHERE br.book.id = :bookId")
    long countRatingsByBookId(@Param("bookId") Long bookId);
    
    @Query("SELECT COUNT(br) FROM BookRating br WHERE br.parentId = :parentId")
    long countRatingsByParentId(@Param("parentId") String parentId);
    
    @Query("SELECT AVG(br.rating) FROM BookRating br WHERE br.parentId = :parentId")
    Double getAverageRatingByParentId(@Param("parentId") String parentId);
    
    // === TÌM KIẾM ĐÁNH GIÁ CÓ REVIEW ===
    List<BookRating> findByReviewIsNotNull();
    List<BookRating> findByBook_IdAndReviewIsNotNull(Long bookId);
    
    // === TÌM KIẾM THEO TỪ KHÓA TRONG REVIEW ===
    @Query("SELECT br FROM BookRating br WHERE " +
           "LOWER(br.review) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(br.reviewTitle) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<BookRating> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
    
    // === TÌM KIẾM ĐÁNH GIÁ HỮU ÍCH ===
    List<BookRating> findByHelpfulCountGreaterThan(Integer minHelpfulCount);
    List<BookRating> findByHelpfulCountGreaterThanOrderByHelpfulCountDesc(Integer minHelpfulCount);
    
    // === TÌM KIẾM TỔNG HỢP ===
    @Query("SELECT br FROM BookRating br WHERE " +
           "(:bookId IS NULL OR br.book.id = :bookId) AND " +
           "(:parentId IS NULL OR br.parentId = :parentId) AND " +
           "(:ratingType IS NULL OR br.ratingType = :ratingType) AND " +
           "(:minRating IS NULL OR br.rating >= :minRating) AND " +
           "(:maxRating IS NULL OR br.rating <= :maxRating)")
    Page<BookRating> findByFilters(
        @Param("bookId") Long bookId,
        @Param("parentId") String parentId,
        @Param("ratingType") BookRating.RatingType ratingType,
        @Param("minRating") Integer minRating,
        @Param("maxRating") Integer maxRating,
        Pageable pageable
    );
}

package com.meowcdd.service;

import com.meowcdd.dto.BookRatingDto;
import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.entity.neon.Book;
import com.meowcdd.entity.neon.BookRating;
import com.meowcdd.repository.neon.BookNeonRepository;
import com.meowcdd.repository.neon.BookRatingNeonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service cho entity BookRating
 * Xử lý business logic và chuyển đổi entity-DTO
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookRatingNeonService {
    
    private final BookRatingNeonRepository bookRatingRepository;
    private final BookNeonRepository bookRepository;
    
    // === CRUD OPERATIONS ===
    
    /**
     * Tạo đánh giá mới
     */
    public BookRatingDto createRating(BookRatingDto ratingDto) {
        log.info("Creating new rating for book: {}", ratingDto.getBookId());
        
        // Validate book exists
        Book book = bookRepository.findById(ratingDto.getBookId())
            .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + ratingDto.getBookId()));
        
        // Check if parent already rated this book
        if (bookRatingRepository.existsByBook_IdAndParentId(ratingDto.getBookId(), ratingDto.getParentId())) {
            throw new IllegalArgumentException("Parent has already rated this book");
        }
        
        // Validate rating value
        if (ratingDto.getRating() < 1 || ratingDto.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        
        BookRating rating = convertToEntity(ratingDto);
        rating.setBook(book);
        rating.setRatingDate(LocalDateTime.now());
        
        BookRating savedRating = bookRatingRepository.save(rating);
        log.info("Rating created successfully with id: {}", savedRating.getId());
        
        // Update book statistics
        updateBookRatingStatistics(ratingDto.getBookId());
        
        return convertToDto(savedRating);
    }
    
    /**
     * Cập nhật đánh giá
     */
    public BookRatingDto updateRating(Long id, BookRatingDto ratingDto) {
        log.info("Updating rating with id: {}", id);
        
        BookRating existingRating = bookRatingRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Rating not found with id: " + id));
        
        // Validate rating value
        if (ratingDto.getRating() != null && (ratingDto.getRating() < 1 || ratingDto.getRating() > 5)) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        
        // Update fields
        updateRatingFields(existingRating, ratingDto);
        existingRating.setIsEdited(true);
        existingRating.setEditDate(LocalDateTime.now());
        
        BookRating updatedRating = bookRatingRepository.save(existingRating);
        log.info("Rating updated successfully with id: {}", updatedRating.getId());
        
        // Update book statistics
        updateBookRatingStatistics(existingRating.getBook().getId());
        
        return convertToDto(updatedRating);
    }
    
    /**
     * Lấy đánh giá theo ID
     */
    @Transactional(readOnly = true)
    public BookRatingDto getRatingById(Long id) {
        log.info("Getting rating with id: {}", id);
        
        BookRating rating = bookRatingRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Rating not found with id: " + id));
        
        return convertToDto(rating);
    }
    
    /**
     * Lấy tất cả đánh giá với phân trang
     */
    @Transactional(readOnly = true)
    public PageResponseDto<BookRatingDto> getAllRatings(int page, int size, String sortBy, String sortDir) {
        log.info("Getting all ratings with page: {}, size: {}, sortBy: {}, sortDir: {}", page, size, sortBy, sortDir);
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<BookRating> ratingPage = bookRatingRepository.findAll(pageable);
        
        List<BookRatingDto> ratingDtos = ratingPage.getContent().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        
        return PageResponseDto.<BookRatingDto>builder()
            .content(ratingDtos)
            .pageNumber(ratingPage.getNumber())
            .pageSize(ratingPage.getSize())
            .totalElements(ratingPage.getTotalElements())
            .totalPages(ratingPage.getTotalPages())
            .isLast(ratingPage.isLast())
            .build();
    }
    
    /**
     * Xóa đánh giá
     */
    public void deleteRating(Long id) {
        log.info("Deleting rating with id: {}", id);
        
        BookRating rating = bookRatingRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Rating not found with id: " + id));
        
        Long bookId = rating.getBook().getId();
        
        bookRatingRepository.deleteById(id);
        log.info("Rating deleted successfully with id: {}", id);
        
        // Update book statistics
        updateBookRatingStatistics(bookId);
    }
    
    // === SEARCH OPERATIONS ===
    
    /**
     * Lấy đánh giá theo sách
     */
    @Transactional(readOnly = true)
    public PageResponseDto<BookRatingDto> getRatingsByBook(Long bookId, int page, int size) {
        log.info("Getting ratings for book: {}", bookId);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("ratingDate").descending());
        Page<BookRating> ratingPage = bookRatingRepository.findByBook_Id(bookId, pageable);
        
        List<BookRatingDto> ratingDtos = ratingPage.getContent().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        
        return PageResponseDto.<BookRatingDto>builder()
            .content(ratingDtos)
            .pageNumber(ratingPage.getNumber())
            .pageSize(ratingPage.getSize())
            .totalElements(ratingPage.getTotalElements())
            .totalPages(ratingPage.getTotalPages())
            .isLast(ratingPage.isLast())
            .build();
    }
    
    /**
     * Lấy đánh giá theo phụ huynh
     */
    @Transactional(readOnly = true)
    public PageResponseDto<BookRatingDto> getRatingsByParent(String parentId, int page, int size) {
        log.info("Getting ratings for parent: {}", parentId);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("ratingDate").descending());
        Page<BookRating> ratingPage = bookRatingRepository.findByParentId(parentId, pageable);
        
        List<BookRatingDto> ratingDtos = ratingPage.getContent().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        
        return PageResponseDto.<BookRatingDto>builder()
            .content(ratingDtos)
            .pageNumber(ratingPage.getNumber())
            .pageSize(ratingPage.getSize())
            .totalElements(ratingPage.getTotalElements())
            .totalPages(ratingPage.getTotalPages())
            .isLast(ratingPage.isLast())
            .build();
    }
    
    /**
     * Lấy đánh giá theo loại
     */
    @Transactional(readOnly = true)
    public PageResponseDto<BookRatingDto> getRatingsByType(BookRating.RatingType ratingType, int page, int size) {
        log.info("Getting ratings by type: {}", ratingType);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("ratingDate").descending());
        Page<BookRating> ratingPage = bookRatingRepository.findByRatingType(ratingType, pageable);
        
        List<BookRatingDto> ratingDtos = ratingPage.getContent().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        
        return PageResponseDto.<BookRatingDto>builder()
            .content(ratingDtos)
            .pageNumber(ratingPage.getNumber())
            .pageSize(ratingPage.getSize())
            .totalElements(ratingPage.getTotalElements())
            .totalPages(ratingPage.getTotalPages())
            .isLast(ratingPage.isLast())
            .build();
    }
    
    /**
     * Tìm kiếm đánh giá theo từ khóa
     */
    @Transactional(readOnly = true)
    public PageResponseDto<BookRatingDto> searchRatingsByKeyword(String keyword, int page, int size) {
        log.info("Searching ratings by keyword: {}", keyword);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("ratingDate").descending());
        Page<BookRating> ratingPage = bookRatingRepository.searchByKeyword(keyword, pageable);
        
        List<BookRatingDto> ratingDtos = ratingPage.getContent().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        
        return PageResponseDto.<BookRatingDto>builder()
            .content(ratingDtos)
            .pageNumber(ratingPage.getNumber())
            .pageSize(ratingPage.getSize())
            .totalElements(ratingPage.getTotalElements())
            .totalPages(ratingPage.getTotalPages())
            .isLast(ratingPage.isLast())
            .build();
    }
    
    /**
     * Lấy đánh giá theo bộ lọc
     */
    @Transactional(readOnly = true)
    public PageResponseDto<BookRatingDto> findRatingsByFilters(Long bookId, String parentId, 
                                                             BookRating.RatingType ratingType, 
                                                             Integer minRating, Integer maxRating, 
                                                             int page, int size) {
        log.info("Finding ratings by filters: bookId={}, parentId={}, ratingType={}, minRating={}, maxRating={}", 
                bookId, parentId, ratingType, minRating, maxRating);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("ratingDate").descending());
        Page<BookRating> ratingPage = bookRatingRepository.findByFilters(bookId, parentId, ratingType, minRating, maxRating, pageable);
        
        List<BookRatingDto> ratingDtos = ratingPage.getContent().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        
        return PageResponseDto.<BookRatingDto>builder()
            .content(ratingDtos)
            .pageNumber(ratingPage.getNumber())
            .pageSize(ratingPage.getSize())
            .totalElements(ratingPage.getTotalElements())
            .totalPages(ratingPage.getTotalPages())
            .isLast(ratingPage.isLast())
            .build();
    }
    
    // === HELPER OPERATIONS ===
    
    /**
     * Kiểm tra phụ huynh đã đánh giá sách chưa
     */
    @Transactional(readOnly = true)
    public boolean hasParentRatedBook(Long bookId, String parentId) {
        return bookRatingRepository.existsByBook_IdAndParentId(bookId, parentId);
    }
    
    /**
     * Lấy đánh giá của phụ huynh cho sách
     */
    @Transactional(readOnly = true)
    public BookRatingDto getParentRatingForBook(Long bookId, String parentId) {
        BookRating rating = bookRatingRepository.findByBook_IdAndParentId(bookId, parentId)
            .orElse(null);
        
        return rating != null ? convertToDto(rating) : null;
    }
    
    /**
     * Đánh dấu đánh giá hữu ích/không hữu ích
     */
    public void markRatingHelpful(Long ratingId, boolean isHelpful) {
        log.info("Marking rating {} as {}", ratingId, isHelpful ? "helpful" : "not helpful");
        
        BookRating rating = bookRatingRepository.findById(ratingId)
            .orElseThrow(() -> new EntityNotFoundException("Rating not found with id: " + ratingId));
        
        if (isHelpful) {
            rating.setHelpfulCount(rating.getHelpfulCount() + 1);
        } else {
            rating.setNotHelpfulCount(rating.getNotHelpfulCount() + 1);
        }
        
        bookRatingRepository.save(rating);
        log.info("Rating marked successfully");
    }
    
    // === STATISTICS ===
    
    /**
     * Lấy thống kê đánh giá cho sách
     */
    @Transactional(readOnly = true)
    public RatingStatistics getRatingStatisticsForBook(Long bookId) {
        log.info("Getting rating statistics for book: {}", bookId);
        
        Double averageRating = bookRatingRepository.getAverageRatingByBookId(bookId);
        long totalRatings = bookRatingRepository.countRatingsByBookId(bookId);
        
        return RatingStatistics.builder()
            .bookId(bookId)
            .averageRating(averageRating != null ? averageRating : 0.0)
            .totalRatings(totalRatings)
            .build();
    }
    
    /**
     * Lấy thống kê đánh giá cho phụ huynh
     */
    @Transactional(readOnly = true)
    public RatingStatistics getRatingStatisticsForParent(String parentId) {
        log.info("Getting rating statistics for parent: {}", parentId);
        
        Double averageRating = bookRatingRepository.getAverageRatingByParentId(parentId);
        long totalRatings = bookRatingRepository.countRatingsByParentId(parentId);
        
        return RatingStatistics.builder()
            .parentId(parentId)
            .averageRating(averageRating != null ? averageRating : 0.0)
            .totalRatings(totalRatings)
            .build();
    }
    
    // === HELPER METHODS ===
    
    private BookRating convertToEntity(BookRatingDto dto) {
        return BookRating.builder()
            .id(dto.getId())
            .rating(dto.getRating())
            .review(dto.getReview())
            .reviewTitle(dto.getReviewTitle())
            .helpfulCount(dto.getHelpfulCount())
            .notHelpfulCount(dto.getNotHelpfulCount())
            .ratingDate(dto.getRatingDate())
            .isEdited(dto.getIsEdited())
            .editDate(dto.getEditDate())
            .ratingType(dto.getRatingType() != null ? BookRating.RatingType.valueOf(dto.getRatingType()) : BookRating.RatingType.PARENT_RATING)
            .parentId(dto.getParentId())
            .build();
    }
    
    private BookRatingDto convertToDto(BookRating entity) {
        return BookRatingDto.builder()
            .id(entity.getId())
            .bookId(entity.getBook().getId())
            .bookTitle(entity.getBook().getTitle())
            .parentId(entity.getParentId())
            .rating(entity.getRating())
            .review(entity.getReview())
            .reviewTitle(entity.getReviewTitle())
            .helpfulCount(entity.getHelpfulCount())
            .notHelpfulCount(entity.getNotHelpfulCount())
            .ratingDate(entity.getRatingDate())
            .isEdited(entity.getIsEdited())
            .editDate(entity.getEditDate())
            .ratingType(entity.getRatingType().name())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }
    
    private void updateRatingFields(BookRating rating, BookRatingDto dto) {
        if (dto.getRating() != null) rating.setRating(dto.getRating());
        if (dto.getReview() != null) rating.setReview(dto.getReview());
        if (dto.getReviewTitle() != null) rating.setReviewTitle(dto.getReviewTitle());
        if (dto.getRatingType() != null) rating.setRatingType(BookRating.RatingType.valueOf(dto.getRatingType()));
    }
    
    private void updateBookRatingStatistics(Long bookId) {
        // This method would update the book's average rating and total ratings
        // Implementation depends on whether you want to do this synchronously or asynchronously
        log.info("Updating rating statistics for book: {}", bookId);
        
        Double averageRating = bookRatingRepository.getAverageRatingByBookId(bookId);
        long totalRatings = bookRatingRepository.countRatingsByBookId(bookId);
        
        // Update book statistics (you might want to do this asynchronously)
        // bookService.updateBookRatingStatistics(bookId, averageRating, totalRatings);
    }
    
    // === INNER CLASSES ===
    
    @lombok.Data
    @lombok.Builder
    public static class RatingStatistics {
        private Long bookId;
        private String parentId;
        private double averageRating;
        private long totalRatings;
    }
}

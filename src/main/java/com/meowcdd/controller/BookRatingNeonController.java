package com.meowcdd.controller;

import com.meowcdd.dto.BookRatingDto;
import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.entity.neon.BookRating;
import com.meowcdd.service.BookRatingNeonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller cho entity BookRating
 * Cung cấp các REST API endpoints để quản lý đánh giá sách
 */
@RestController
@RequestMapping("/neon/book-ratings")
@RequiredArgsConstructor
@Slf4j
public class BookRatingNeonController {
    
    private final BookRatingNeonService bookRatingService;
    
    // === CRUD OPERATIONS ===
    
    /**
     * Tạo đánh giá mới
     */
    @PostMapping
    public ResponseEntity<BookRatingDto> createRating(@RequestBody BookRatingDto ratingDto) {
        log.info("Creating new rating for book: {}", ratingDto.getBookId());
        BookRatingDto createdRating = bookRatingService.createRating(ratingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRating);
    }
    
    /**
     * Lấy đánh giá theo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookRatingDto> getRatingById(@PathVariable Long id) {
        log.info("Getting rating with id: {}", id);
        BookRatingDto rating = bookRatingService.getRatingById(id);
        return ResponseEntity.ok(rating);
    }
    
    /**
     * Lấy tất cả đánh giá với phân trang
     */
    @GetMapping
    public ResponseEntity<PageResponseDto<BookRatingDto>> getAllRatings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        log.info("Getting all ratings with page: {}, size: {}, sortBy: {}, sortDir: {}", page, size, sortBy, sortDir);
        PageResponseDto<BookRatingDto> ratings = bookRatingService.getAllRatings(page, size, sortBy, sortDir);
        return ResponseEntity.ok(ratings);
    }
    
    /**
     * Cập nhật đánh giá
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookRatingDto> updateRating(@PathVariable Long id, @RequestBody BookRatingDto ratingDto) {
        log.info("Updating rating with id: {}", id);
        BookRatingDto updatedRating = bookRatingService.updateRating(id, ratingDto);
        return ResponseEntity.ok(updatedRating);
    }
    
    /**
     * Xóa đánh giá
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
        log.info("Deleting rating with id: {}", id);
        bookRatingService.deleteRating(id);
        return ResponseEntity.noContent().build();
    }
    
    // === SEARCH OPERATIONS ===
    
    /**
     * Lấy đánh giá theo sách
     */
    @GetMapping("/book/{bookId}")
    public ResponseEntity<PageResponseDto<BookRatingDto>> getRatingsByBook(
            @PathVariable Long bookId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting ratings for book: {}", bookId);
        PageResponseDto<BookRatingDto> ratings = bookRatingService.getRatingsByBook(bookId, page, size);
        return ResponseEntity.ok(ratings);
    }
    
    /**
     * Lấy đánh giá theo phụ huynh
     */
    @GetMapping("/parent/{parentId}")
    public ResponseEntity<PageResponseDto<BookRatingDto>> getRatingsByParent(
            @PathVariable String parentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting ratings for parent: {}", parentId);
        PageResponseDto<BookRatingDto> ratings = bookRatingService.getRatingsByParent(parentId, page, size);
        return ResponseEntity.ok(ratings);
    }
    
    /**
     * Lấy đánh giá theo loại
     */
    @GetMapping("/type/{ratingType}")
    public ResponseEntity<PageResponseDto<BookRatingDto>> getRatingsByType(
            @PathVariable BookRating.RatingType ratingType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting ratings by type: {}", ratingType);
        PageResponseDto<BookRatingDto> ratings = bookRatingService.getRatingsByType(ratingType, page, size);
        return ResponseEntity.ok(ratings);
    }
    
    /**
     * Tìm kiếm đánh giá theo từ khóa
     */
    @GetMapping("/search")
    public ResponseEntity<PageResponseDto<BookRatingDto>> searchRatingsByKeyword(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Searching ratings by keyword: {}", keyword);
        PageResponseDto<BookRatingDto> ratings = bookRatingService.searchRatingsByKeyword(keyword, page, size);
        return ResponseEntity.ok(ratings);
    }
    
    /**
     * Lấy đánh giá theo bộ lọc
     */
    @GetMapping("/filter")
    public ResponseEntity<PageResponseDto<BookRatingDto>> findRatingsByFilters(
            @RequestParam(required = false) Long bookId,
            @RequestParam(required = false) String parentId,
            @RequestParam(required = false) BookRating.RatingType ratingType,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) Integer maxRating,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Finding ratings by filters: bookId={}, parentId={}, ratingType={}, minRating={}, maxRating={}", 
                bookId, parentId, ratingType, minRating, maxRating);
        PageResponseDto<BookRatingDto> ratings = bookRatingService.findRatingsByFilters(bookId, parentId, ratingType, minRating, maxRating, page, size);
        return ResponseEntity.ok(ratings);
    }
    
    // === HELPER OPERATIONS ===
    
    /**
     * Kiểm tra phụ huynh đã đánh giá sách chưa
     */
    @GetMapping("/check/{bookId}/{parentId}")
    public ResponseEntity<Boolean> hasParentRatedBook(@PathVariable Long bookId, @PathVariable String parentId) {
        log.info("Checking if parent {} has rated book {}", parentId, bookId);
        boolean hasRated = bookRatingService.hasParentRatedBook(bookId, parentId);
        return ResponseEntity.ok(hasRated);
    }
    
    /**
     * Lấy đánh giá của phụ huynh cho sách
     */
    @GetMapping("/parent/{parentId}/book/{bookId}")
    public ResponseEntity<BookRatingDto> getParentRatingForBook(@PathVariable String parentId, @PathVariable Long bookId) {
        log.info("Getting rating for parent {} and book {}", parentId, bookId);
        BookRatingDto rating = bookRatingService.getParentRatingForBook(bookId, parentId);
        return ResponseEntity.ok(rating);
    }
    
    /**
     * Đánh dấu đánh giá hữu ích/không hữu ích
     */
    @PatchMapping("/{id}/helpful")
    public ResponseEntity<Void> markRatingHelpful(@PathVariable Long id, @RequestParam Boolean isHelpful) {
        log.info("Marking rating {} as {}", id, isHelpful ? "helpful" : "not helpful");
        bookRatingService.markRatingHelpful(id, isHelpful);
        return ResponseEntity.ok().build();
    }
    
    // === STATISTICS ===
    
    /**
     * Lấy thống kê đánh giá cho sách
     */
    @GetMapping("/statistics/book/{bookId}")
    public ResponseEntity<BookRatingNeonService.RatingStatistics> getRatingStatisticsForBook(@PathVariable Long bookId) {
        log.info("Getting rating statistics for book: {}", bookId);
        BookRatingNeonService.RatingStatistics statistics = bookRatingService.getRatingStatisticsForBook(bookId);
        return ResponseEntity.ok(statistics);
    }
    
    /**
     * Lấy thống kê đánh giá cho phụ huynh
     */
    @GetMapping("/statistics/parent/{parentId}")
    public ResponseEntity<BookRatingNeonService.RatingStatistics> getRatingStatisticsForParent(@PathVariable String parentId) {
        log.info("Getting rating statistics for parent: {}", parentId);
        BookRatingNeonService.RatingStatistics statistics = bookRatingService.getRatingStatisticsForParent(parentId);
        return ResponseEntity.ok(statistics);
    }
    
    // === PATCH OPERATIONS ===
    
    /**
     * Cập nhật một phần thông tin đánh giá
     */
    @PatchMapping("/{id}")
    public ResponseEntity<BookRatingDto> patchRating(@PathVariable Long id, @RequestBody BookRatingDto ratingDto) {
        log.info("Patching rating with id: {}", id);
        BookRatingDto updatedRating = bookRatingService.updateRating(id, ratingDto);
        return ResponseEntity.ok(updatedRating);
    }
    
    /**
     * Cập nhật điểm đánh giá
     */
    @PatchMapping("/{id}/rating")
    public ResponseEntity<BookRatingDto> updateRatingScore(@PathVariable Long id, @RequestParam Integer rating) {
        log.info("Updating rating score with id: {}, rating: {}", id, rating);
        BookRatingDto ratingDto = BookRatingDto.builder().rating(rating).build();
        BookRatingDto updatedRating = bookRatingService.updateRating(id, ratingDto);
        return ResponseEntity.ok(updatedRating);
    }
    
    /**
     * Cập nhật nội dung đánh giá
     */
    @PatchMapping("/{id}/review")
    public ResponseEntity<BookRatingDto> updateRatingReview(@PathVariable Long id, @RequestParam String review) {
        log.info("Updating rating review with id: {}", id);
        BookRatingDto ratingDto = BookRatingDto.builder().review(review).build();
        BookRatingDto updatedRating = bookRatingService.updateRating(id, ratingDto);
        return ResponseEntity.ok(updatedRating);
    }
}

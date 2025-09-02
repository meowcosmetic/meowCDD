package com.meowcdd.service;

import com.meowcdd.dto.BookDto;
import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.entity.neon.Book;
import com.meowcdd.entity.neon.DevelopmentalDomain;
import com.meowcdd.entity.neon.SupportedFormat;
import com.meowcdd.repository.neon.BookNeonRepository;
import com.meowcdd.repository.neon.DevelopmentalDomainNeonRepository;
import com.meowcdd.repository.neon.SupportedFormatNeonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * Service cho entity Book
 * Xử lý business logic và chuyển đổi entity-DTO
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookNeonService {
    
    private final BookNeonRepository bookRepository;
    private final SupportedFormatNeonRepository supportedFormatRepository;
    private final DevelopmentalDomainNeonRepository developmentalDomainRepository;
    
    // === CRUD OPERATIONS ===
    
    /**
     * Tạo sách mới
     */
    public BookDto createBook(BookDto bookDto) {
        log.info("Creating new book: {}", bookDto.getTitle());
        
        // Validate supported format
        SupportedFormat supportedFormat = supportedFormatRepository.findById(bookDto.getSupportedFormatId())
            .orElseThrow(() -> new EntityNotFoundException("Supported format not found with id: " + bookDto.getSupportedFormatId()));
        
        // Validate developmental domains
        Set<DevelopmentalDomain> developmentalDomains = new HashSet<>();
        if (bookDto.getDevelopmentalDomainIds() != null && !bookDto.getDevelopmentalDomainIds().isEmpty()) {
            developmentalDomains = bookDto.getDevelopmentalDomainIds().stream()
                .map(domainId -> developmentalDomainRepository.findById(domainId)
                    .orElseThrow(() -> new EntityNotFoundException("Developmental domain not found with id: " + domainId)))
                .collect(Collectors.toSet());
        }
        
        // Check for duplicate ISBN if provided
        if (bookDto.getIsbn() != null && !bookDto.getIsbn().trim().isEmpty()) {
            if (bookRepository.existsByIsbn(bookDto.getIsbn())) {
                throw new IllegalArgumentException("Book with ISBN " + bookDto.getIsbn() + " already exists");
            }
        }
        
        Book book = convertToEntity(bookDto);
        book.setSupportedFormat(supportedFormat);
        book.setDevelopmentalDomains(developmentalDomains);
        
        Book savedBook = bookRepository.save(book);
        log.info("Book created successfully with id: {}", savedBook.getId());
        
        return convertToDto(savedBook);
    }
    
    /**
     * Tạo sách mới với file nội dung
     */
    public BookDto createBookWithFile(BookDto bookDto) {
        log.info("Creating new book with file: {}", bookDto.getContentFileName());
        
        // Validate file size (max 20MB)
        if (bookDto.getContentFileSize() != null && bookDto.getContentFileSize() > 20971520) {
            throw new IllegalArgumentException("File size exceeds maximum limit of 20MB");
        }
        
        // Validate supported format
        SupportedFormat supportedFormat = supportedFormatRepository.findById(bookDto.getSupportedFormatId())
            .orElseThrow(() -> new EntityNotFoundException("Supported format not found with id: " + bookDto.getSupportedFormatId()));
        
        // Validate developmental domains
        Set<DevelopmentalDomain> developmentalDomains = new HashSet<>();
        if (bookDto.getDevelopmentalDomainIds() != null && !bookDto.getDevelopmentalDomainIds().isEmpty()) {
            developmentalDomains = bookDto.getDevelopmentalDomainIds().stream()
                .map(domainId -> developmentalDomainRepository.findById(domainId)
                    .orElseThrow(() -> new EntityNotFoundException("Developmental domain not found with id: " + domainId)))
                .collect(Collectors.toSet());
        }
        
        // Check for duplicate ISBN if provided
        if (bookDto.getIsbn() != null && !bookDto.getIsbn().trim().isEmpty()) {
            if (bookRepository.existsByIsbn(bookDto.getIsbn())) {
                throw new IllegalArgumentException("Book with ISBN " + bookDto.getIsbn() + " already exists");
            }
        }
        
        Book book = convertToEntity(bookDto);
        book.setSupportedFormat(supportedFormat);
        book.setDevelopmentalDomains(developmentalDomains);
        
        // Set file-related fields
        book.setContentFile(bookDto.getContentFile());
        book.setContentFileName(bookDto.getContentFileName());
        book.setContentFileType(bookDto.getContentFileType());
        book.setContentFileSize(bookDto.getContentFileSize());
        book.setContentMimeType(bookDto.getContentMimeType());
        book.setContentUploadedAt(bookDto.getContentUploadedAt());
        book.setContentUploadedBy(bookDto.getContentUploadedBy());
        book.setContentIsVerified(bookDto.getContentIsVerified());
        
        Book savedBook = bookRepository.save(book);
        log.info("Book with file created successfully with id: {}", savedBook.getId());
        
        return convertToDto(savedBook);
    }
    
    /**
     * Cập nhật sách
     */
    public BookDto updateBook(Long id, BookDto bookDto) {
        log.info("Updating book with id: {}", id);
        
        Book existingBook = bookRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        
        // Validate supported format if changed
        if (!existingBook.getSupportedFormat().getId().equals(bookDto.getSupportedFormatId())) {
            SupportedFormat supportedFormat = supportedFormatRepository.findById(bookDto.getSupportedFormatId())
                .orElseThrow(() -> new EntityNotFoundException("Supported format not found with id: " + bookDto.getSupportedFormatId()));
            existingBook.setSupportedFormat(supportedFormat);
        }
        
        // Validate developmental domains if changed
        if (bookDto.getDevelopmentalDomainIds() != null) {
            Set<DevelopmentalDomain> developmentalDomains = bookDto.getDevelopmentalDomainIds().stream()
                .map(domainId -> developmentalDomainRepository.findById(domainId)
                    .orElseThrow(() -> new EntityNotFoundException("Developmental domain not found with id: " + domainId)))
                .collect(Collectors.toSet());
            existingBook.setDevelopmentalDomains(developmentalDomains);
        }
        
        // Check for duplicate ISBN if changed
        if (bookDto.getIsbn() != null && !bookDto.getIsbn().trim().isEmpty() && 
            !bookDto.getIsbn().equals(existingBook.getIsbn())) {
            if (bookRepository.existsByIsbn(bookDto.getIsbn())) {
                throw new IllegalArgumentException("Book with ISBN " + bookDto.getIsbn() + " already exists");
            }
        }
        
        // Update fields
        updateBookFields(existingBook, bookDto);
        
        Book updatedBook = bookRepository.save(existingBook);
        log.info("Book updated successfully with id: {}", updatedBook.getId());
        
        return convertToDto(updatedBook);
    }
    
    /**
     * Lấy sách theo ID
     */
    @Transactional(readOnly = true)
    public BookDto getBookById(Long id) {
        log.info("Getting book with id: {}", id);
        
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        
        return convertToDto(book);
    }
    
    /**
     * Lấy tất cả sách với phân trang
     */
    @Transactional(readOnly = true)
    public PageResponseDto<BookDto> getAllBooks(int page, int size, String sortBy, String sortDir) {
        log.info("Getting all books with page: {}, size: {}, sortBy: {}, sortDir: {}", page, size, sortBy, sortDir);
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Book> bookPage = bookRepository.findAll(pageable);
        
        List<BookDto> bookDtos = bookPage.getContent().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        
        return PageResponseDto.<BookDto>builder()
            .content(bookDtos)
            .pageNumber(bookPage.getNumber())
            .pageSize(bookPage.getSize())
            .totalElements(bookPage.getTotalElements())
            .totalPages(bookPage.getTotalPages())
            .isLast(bookPage.isLast())
            .build();
    }
    
    /**
     * Xóa sách
     */
    public void deleteBook(Long id) {
        log.info("Deleting book with id: {}", id);
        
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book not found with id: " + id);
        }
        
        bookRepository.deleteById(id);
        log.info("Book deleted successfully with id: {}", id);
    }
    
    // === SEARCH OPERATIONS ===
    
    /**
     * Tìm kiếm sách theo từ khóa
     */
    @Transactional(readOnly = true)
    public PageResponseDto<BookDto> searchBooksByKeyword(String keyword, int page, int size) {
        log.info("Searching books by keyword: {}", keyword);
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository.searchByKeyword(keyword, pageable);
        
        List<BookDto> bookDtos = bookPage.getContent().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        
        return PageResponseDto.<BookDto>builder()
            .content(bookDtos)
            .pageNumber(bookPage.getNumber())
            .pageSize(bookPage.getSize())
            .totalElements(bookPage.getTotalElements())
            .totalPages(bookPage.getTotalPages())
            .isLast(bookPage.isLast())
            .build();
    }
    
    /**
     * Tìm kiếm sách theo bộ lọc
     */
    @Transactional(readOnly = true)
    public PageResponseDto<BookDto> findBooksByFilters(String title, String author, String language, 
                                                      String ageGroup, Boolean isActive, Boolean isFeatured, 
                                                      int page, int size) {
        log.info("Finding books by filters: title={}, author={}, language={}, ageGroup={}, isActive={}, isFeatured={}", 
                title, author, language, ageGroup, isActive, isFeatured);
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository.findByFilters(title, author, language, ageGroup, isActive, isFeatured, pageable);
        
        List<BookDto> bookDtos = bookPage.getContent().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        
        return PageResponseDto.<BookDto>builder()
            .content(bookDtos)
            .pageNumber(bookPage.getNumber())
            .pageSize(bookPage.getSize())
            .totalElements(bookPage.getTotalElements())
            .totalPages(bookPage.getTotalPages())
            .isLast(bookPage.isLast())
            .build();
    }
    
    /**
     * Lấy sách theo định dạng file
     */
    @Transactional(readOnly = true)
    public List<BookDto> getBooksByFormat(Long formatId) {
        log.info("Getting books by format id: {}", formatId);
        
        List<Book> books = bookRepository.findBySupportedFormatId(formatId);
        return books.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    /**
     * Lấy sách theo lĩnh vực phát triển
     */
    @Transactional(readOnly = true)
    public List<BookDto> getBooksByDevelopmentalDomain(UUID domainId) {
        log.info("Getting books by developmental domain id: {}", domainId);
        
        List<Book> books = bookRepository.findByDevelopmentalDomainId(domainId);
        return books.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    /**
     * Lấy sách nổi bật
     */
    @Transactional(readOnly = true)
    public List<BookDto> getFeaturedBooks() {
        log.info("Getting featured books");
        
        List<Book> books = bookRepository.findByIsActiveTrueAndIsFeaturedTrue();
        return books.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    // === STATISTICS ===
    
    /**
     * Lấy thống kê sách
     */
    @Transactional(readOnly = true)
    public BookStatistics getBookStatistics() {
        log.info("Getting book statistics");
        
        long totalBooks = bookRepository.count();
        long activeBooks = bookRepository.countActiveBooks();
        long featuredBooks = bookRepository.countFeaturedBooks();
        Double averageRating = bookRepository.getAverageRatingOfAllBooks();
        
        return BookStatistics.builder()
            .totalBooks(totalBooks)
            .activeBooks(activeBooks)
            .featuredBooks(featuredBooks)
            .averageRating(averageRating != null ? averageRating : 0.0)
            .build();
    }
    
    // === FILE OPERATIONS ===
    
    /**
     * Lấy sách có file nội dung
     */
    @Transactional(readOnly = true)
    public List<BookDto> getBooksWithContentFiles() {
        log.info("Getting books with content files");
        List<Book> books = bookRepository.findBooksWithContentFile();
        return books.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    /**
     * Lấy thống kê file nội dung
     */
    @Transactional(readOnly = true)
    public Object getContentFileStatistics() {
        log.info("Getting content file statistics");
        
        long totalFiles = bookRepository.countBooksWithContentFile();
        Long totalSize = bookRepository.getTotalContentFileSize();
        Double avgSize = bookRepository.getAverageContentFileSize();
        List<Object[]> fileTypes = bookRepository.countBooksByContentFileType();
        List<Object[]> uploaders = bookRepository.countBooksByUploader();
        
        java.util.Map<String, Object> statistics = new java.util.HashMap<>();
        statistics.put("totalFiles", totalFiles);
        statistics.put("totalSize", totalSize != null ? totalSize : 0);
        statistics.put("averageSize", avgSize != null ? avgSize : 0);
        statistics.put("fileTypes", fileTypes);
        statistics.put("uploaders", uploaders);
        
        return statistics;
    }
    
    /**
     * Lấy sách theo loại file
     */
    @Transactional(readOnly = true)
    public List<BookDto> getBooksByContentFileType(String fileType) {
        log.info("Getting books by content file type: {}", fileType);
        List<Book> books = bookRepository.findByContentFileType(fileType);
        return books.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    /**
     * Lấy sách theo người upload
     */
    @Transactional(readOnly = true)
    public List<BookDto> getBooksByUploader(String uploader) {
        log.info("Getting books by uploader: {}", uploader);
        List<Book> books = bookRepository.findByContentUploadedBy(uploader);
        return books.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    // === HELPER METHODS ===
    
    private Book convertToEntity(BookDto dto) {
        return Book.builder()
            .id(dto.getId())
            .title(dto.getTitle())
            .author(dto.getAuthor())
            .publisher(dto.getPublisher())
            .isbn(dto.getIsbn())
            .publicationYear(dto.getPublicationYear())
            .minAge(dto.getMinAge())
            .maxAge(dto.getMaxAge())
            .ageGroup(dto.getAgeGroup())
            .description(dto.getDescription())
            .summary(dto.getSummary())
            .language(dto.getLanguage())
            .fileSize(dto.getFileSize())
            .pageCount(dto.getPageCount())
            .averageRating(dto.getAverageRating())
            .totalRatings(dto.getTotalRatings())
            .totalViews(dto.getTotalViews())
            .isActive(dto.getIsActive())
            .isFeatured(dto.getIsFeatured())
            .keywords(dto.getKeywords())
            .tags(dto.getTags())
            .metadata(dto.getMetadata())
            .contentFile(dto.getContentFile())
            .contentFileName(dto.getContentFileName())
            .contentFileType(dto.getContentFileType())
            .contentFileSize(dto.getContentFileSize())
            .contentMimeType(dto.getContentMimeType())
            .contentUploadedAt(dto.getContentUploadedAt())
            .contentUploadedBy(dto.getContentUploadedBy())
            .contentIsVerified(dto.getContentIsVerified())
            .contentVerificationDate(dto.getContentVerificationDate())
            .build();
    }
    
    private BookDto convertToDto(Book entity) {
        return BookDto.builder()
            .id(entity.getId())
            .title(entity.getTitle())
            .author(entity.getAuthor())
            .publisher(entity.getPublisher())
            .isbn(entity.getIsbn())
            .publicationYear(entity.getPublicationYear())
            .supportedFormatId(entity.getSupportedFormat().getId())
            .supportedFormatName(entity.getSupportedFormat().getFormatName())
            .developmentalDomainIds(entity.getDevelopmentalDomains().stream()
                .map(DevelopmentalDomain::getId)
                .collect(Collectors.toSet()))
            .developmentalDomainNames(entity.getDevelopmentalDomains().stream()
                .map(DevelopmentalDomain::getName)
                .collect(Collectors.toSet()))
            .minAge(entity.getMinAge())
            .maxAge(entity.getMaxAge())
            .ageGroup(entity.getAgeGroup())
            .description(entity.getDescription())
            .summary(entity.getSummary())
            .language(entity.getLanguage())
            .fileSize(entity.getFileSize())
            .pageCount(entity.getPageCount())
            .averageRating(entity.getAverageRating())
            .totalRatings(entity.getTotalRatings())
            .totalViews(entity.getTotalViews())
            .isActive(entity.getIsActive())
            .isFeatured(entity.getIsFeatured())
            .keywords(entity.getKeywords())
            .tags(entity.getTags())
            .metadata(entity.getMetadata())
            .contentFile(entity.getContentFile())
            .contentFileName(entity.getContentFileName())
            .contentFileType(entity.getContentFileType())
            .contentFileSize(entity.getContentFileSize())
            .contentMimeType(entity.getContentMimeType())
            .contentUploadedAt(entity.getContentUploadedAt())
            .contentUploadedBy(entity.getContentUploadedBy())
            .contentIsVerified(entity.getContentIsVerified())
            .contentVerificationDate(entity.getContentVerificationDate())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }
    
    private void updateBookFields(Book book, BookDto dto) {
        if (dto.getTitle() != null) book.setTitle(dto.getTitle());
        if (dto.getAuthor() != null) book.setAuthor(dto.getAuthor());
        if (dto.getPublisher() != null) book.setPublisher(dto.getPublisher());
        if (dto.getIsbn() != null) book.setIsbn(dto.getIsbn());
        if (dto.getPublicationYear() != null) book.setPublicationYear(dto.getPublicationYear());
        if (dto.getMinAge() != null) book.setMinAge(dto.getMinAge());
        if (dto.getMaxAge() != null) book.setMaxAge(dto.getMaxAge());
        if (dto.getAgeGroup() != null) book.setAgeGroup(dto.getAgeGroup());
        if (dto.getDescription() != null) book.setDescription(dto.getDescription());
        if (dto.getSummary() != null) book.setSummary(dto.getSummary());
        if (dto.getLanguage() != null) book.setLanguage(dto.getLanguage());
        if (dto.getFileSize() != null) book.setFileSize(dto.getFileSize());
        if (dto.getPageCount() != null) book.setPageCount(dto.getPageCount());
        if (dto.getAverageRating() != null) book.setAverageRating(dto.getAverageRating());
        if (dto.getTotalRatings() != null) book.setTotalRatings(dto.getTotalRatings());
        if (dto.getTotalViews() != null) book.setTotalViews(dto.getTotalViews());
        if (dto.getIsActive() != null) book.setIsActive(dto.getIsActive());
        if (dto.getIsFeatured() != null) book.setIsFeatured(dto.getIsFeatured());
        if (dto.getKeywords() != null) book.setKeywords(dto.getKeywords());
        if (dto.getTags() != null) book.setTags(dto.getTags());
        if (dto.getMetadata() != null) book.setMetadata(dto.getMetadata());
        
        // Update file-related fields
        if (dto.getContentFile() != null) book.setContentFile(dto.getContentFile());
        if (dto.getContentFileName() != null) book.setContentFileName(dto.getContentFileName());
        if (dto.getContentFileType() != null) book.setContentFileType(dto.getContentFileType());
        if (dto.getContentFileSize() != null) book.setContentFileSize(dto.getContentFileSize());
        if (dto.getContentMimeType() != null) book.setContentMimeType(dto.getContentMimeType());
        if (dto.getContentUploadedAt() != null) book.setContentUploadedAt(dto.getContentUploadedAt());
        if (dto.getContentUploadedBy() != null) book.setContentUploadedBy(dto.getContentUploadedBy());
        if (dto.getContentIsVerified() != null) book.setContentIsVerified(dto.getContentIsVerified());
        if (dto.getContentVerificationDate() != null) book.setContentVerificationDate(dto.getContentVerificationDate());
    }
    
    // === INNER CLASSES ===
    
    @Data
    @Builder
    public static class BookStatistics {
        private long totalBooks;
        private long activeBooks;
        private long featuredBooks;
        private double averageRating;
    }
}

package com.meowcdd.controller;

import com.meowcdd.dto.BookDto;
import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.service.BookNeonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Controller cho entity Book
 * Cung cấp các API CRUD và tìm kiếm sách
 */
@RestController
@RequestMapping("/neon/books")
@RequiredArgsConstructor
@Slf4j
public class BookNeonController {
    
    private final BookNeonService bookService;
    
    // === CRUD OPERATIONS ===
    
    /**
     * Tạo sách mới
     */
    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        log.info("Creating new book: {}", bookDto.getTitle());
        BookDto createdBook = bookService.createBook(bookDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }
    
    /**
     * Upload sách với file nội dung
     */
    @PostMapping("/upload")
    public ResponseEntity<BookDto> uploadBookWithFile(
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("description") String description,
            @RequestParam("isbn") String isbn,
            @RequestParam("publicationYear") Integer publicationYear,
            @RequestParam("pageCount") Integer pageCount,
            @RequestParam("language") String language,
            @RequestParam("ageGroup") String ageGroup,
            @RequestParam("supportedFormatId") Long supportedFormatId,
            @RequestParam("developmentalDomainIds") String developmentalDomainIds,
            @RequestParam("contentUploadedBy") String contentUploadedBy,
            @RequestParam("file") MultipartFile file) {
        
        log.info("Uploading book with file: {}", file.getOriginalFilename());
        
        try {
            // Parse developmental domain IDs
            String[] domainIdStrings = developmentalDomainIds.split(",");
            List<UUID> domainIds = List.of(domainIdStrings).stream()
                    .map(UUID::fromString)
                    .toList();
            
            // Create BookDto
            BookDto bookDto = BookDto.builder()
                    .title(title)
                    .author(author)
                    .description(description)
                    .isbn(isbn)
                    .publicationYear(publicationYear)
                    .pageCount(pageCount)
                    .language(language)
                    .ageGroup(ageGroup)
                    .supportedFormatId(supportedFormatId)
                    .developmentalDomainIds(domainIds.stream().collect(java.util.stream.Collectors.toSet()))
                    .contentFile(file.getBytes())
                    .contentFileName(file.getOriginalFilename())
                    .contentFileType(getFileExtension(file.getOriginalFilename()))
                    .contentFileSize(file.getSize())
                    .contentMimeType(file.getContentType())
                    .contentUploadedAt(LocalDateTime.now())
                    .contentUploadedBy(contentUploadedBy)
                    .contentIsVerified(false)
                    .build();
            
            BookDto createdBook = bookService.createBookWithFile(bookDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
            
        } catch (IOException e) {
            log.error("Error reading file: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    /**
     * Cập nhật sách
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        log.info("Updating book with id: {}", id);
        BookDto updatedBook = bookService.updateBook(id, bookDto);
        return ResponseEntity.ok(updatedBook);
    }
    
    /**
     * Lấy sách theo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        log.info("Getting book with id: {}", id);
        BookDto book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }
    
    /**
     * Lấy tất cả sách với phân trang
     */
    @GetMapping
    public ResponseEntity<PageResponseDto<BookDto>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        log.info("Getting all books with page: {}, size: {}, sortBy: {}, sortDir: {}", page, size, sortBy, sortDir);
        PageResponseDto<BookDto> books = bookService.getAllBooks(page, size, sortBy, sortDir);
        return ResponseEntity.ok(books);
    }
    
    /**
     * Xóa sách
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        log.info("Deleting book with id: {}", id);
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
    
    // === SEARCH OPERATIONS ===
    
    /**
     * Tìm kiếm sách theo từ khóa
     */
    @GetMapping("/search")
    public ResponseEntity<PageResponseDto<BookDto>> searchBooksByKeyword(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        log.info("Searching books by keyword: {}", keyword);
        PageResponseDto<BookDto> books = bookService.searchBooksByKeyword(keyword, page, size);
        return ResponseEntity.ok(books);
    }
    
    /**
     * Tìm kiếm sách theo bộ lọc
     */
    @GetMapping("/filter")
    public ResponseEntity<PageResponseDto<BookDto>> findBooksByFilters(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String ageGroup,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) Boolean isFeatured,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        log.info("Finding books by filters: title={}, author={}, language={}, ageGroup={}, isActive={}, isFeatured={}", 
                title, author, language, ageGroup, isActive, isFeatured);
        PageResponseDto<BookDto> books = bookService.findBooksByFilters(title, author, language, ageGroup, isActive, isFeatured, page, size);
        return ResponseEntity.ok(books);
    }
    
    /**
     * Lấy sách theo định dạng file
     */
    @GetMapping("/format/{formatId}")
    public ResponseEntity<List<BookDto>> getBooksByFormat(@PathVariable Long formatId) {
        log.info("Getting books by format id: {}", formatId);
        List<BookDto> books = bookService.getBooksByFormat(formatId);
        return ResponseEntity.ok(books);
    }
    
    /**
     * Lấy sách theo lĩnh vực phát triển
     */
    @GetMapping("/domain/{domainId}")
    public ResponseEntity<List<BookDto>> getBooksByDevelopmentalDomain(@PathVariable UUID domainId) {
        log.info("Getting books by developmental domain id: {}", domainId);
        List<BookDto> books = bookService.getBooksByDevelopmentalDomain(domainId);
        return ResponseEntity.ok(books);
    }
    
    /**
     * Lấy sách nổi bật
     */
    @GetMapping("/featured")
    public ResponseEntity<List<BookDto>> getFeaturedBooks() {
        log.info("Getting featured books");
        List<BookDto> books = bookService.getFeaturedBooks();
        return ResponseEntity.ok(books);
    }
    
    // === FILE OPERATIONS ===
    
    /**
     * Download file nội dung sách
     */
    @GetMapping("/{id}/content-file")
    public ResponseEntity<ByteArrayResource> downloadContentFile(@PathVariable Long id) {
        log.info("Downloading content file for book id: {}", id);
        
        BookDto book = bookService.getBookById(id);
        if (book.getContentFile() == null || book.getContentFile().length == 0) {
            return ResponseEntity.notFound().build();
        }
        
        ByteArrayResource resource = new ByteArrayResource(book.getContentFile());
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + book.getContentFileName() + "\"")
                .contentType(MediaType.parseMediaType(book.getContentMimeType()))
                .contentLength(book.getContentFile().length)
                .body(resource);
    }
    
    /**
     * Download file nội dung sách với tên gốc
     */
    @GetMapping("/{id}/content-file/original")
    public ResponseEntity<ByteArrayResource> downloadContentFileOriginal(@PathVariable Long id) {
        log.info("Downloading content file with original name for book id: {}", id);
        
        BookDto book = bookService.getBookById(id);
        if (book.getContentFile() == null || book.getContentFile().length == 0) {
            return ResponseEntity.notFound().build();
        }
        
        ByteArrayResource resource = new ByteArrayResource(book.getContentFile());
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + book.getContentFileName() + "\"")
                .contentType(MediaType.parseMediaType(book.getContentMimeType()))
                .contentLength(book.getContentFile().length)
                .body(resource);
    }
    
    /**
     * Lấy sách có file nội dung
     */
    @GetMapping("/with-content-files")
    public ResponseEntity<List<BookDto>> getBooksWithContentFiles() {
        log.info("Getting books with content files");
        List<BookDto> books = bookService.getBooksWithContentFiles();
        return ResponseEntity.ok(books);
    }
    
    /**
     * Lấy thống kê file nội dung
     */
    @GetMapping("/content-file-statistics")
    public ResponseEntity<Object> getContentFileStatistics() {
        log.info("Getting content file statistics");
        Object statistics = bookService.getContentFileStatistics();
        return ResponseEntity.ok(statistics);
    }
    
    /**
     * Tìm sách theo loại file
     */
    @GetMapping("/content-file-type/{fileType}")
    public ResponseEntity<List<BookDto>> getBooksByContentFileType(@PathVariable String fileType) {
        log.info("Getting books by content file type: {}", fileType);
        List<BookDto> books = bookService.getBooksByContentFileType(fileType);
        return ResponseEntity.ok(books);
    }
    
    /**
     * Tìm sách theo người upload
     */
    @GetMapping("/uploader/{uploader}")
    public ResponseEntity<List<BookDto>> getBooksByUploader(@PathVariable String uploader) {
        log.info("Getting books by uploader: {}", uploader);
        List<BookDto> books = bookService.getBooksByUploader(uploader);
        return ResponseEntity.ok(books);
    }
    
    // === STATISTICS ===
    
    /**
     * Lấy thống kê sách
     */
    @GetMapping("/statistics")
    public ResponseEntity<BookNeonService.BookStatistics> getBookStatistics() {
        log.info("Getting book statistics");
        BookNeonService.BookStatistics statistics = bookService.getBookStatistics();
        return ResponseEntity.ok(statistics);
    }
    
    // === PATCH OPERATIONS ===
    
    /**
     * Cập nhật một phần thông tin sách
     */
    @PatchMapping("/{id}")
    public ResponseEntity<BookDto> patchBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        log.info("Patching book with id: {}", id);
        BookDto updatedBook = bookService.updateBook(id, bookDto);
        return ResponseEntity.ok(updatedBook);
    }
    
    /**
     * Cập nhật trạng thái hoạt động
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<BookDto> updateBookStatus(@PathVariable Long id, @RequestParam Boolean isActive) {
        log.info("Updating book status with id: {}, isActive: {}", id, isActive);
        BookDto bookDto = BookDto.builder().isActive(isActive).build();
        BookDto updatedBook = bookService.updateBook(id, bookDto);
        return ResponseEntity.ok(updatedBook);
    }
    
    /**
     * Cập nhật trạng thái nổi bật
     */
    @PatchMapping("/{id}/featured")
    public ResponseEntity<BookDto> updateBookFeatured(@PathVariable Long id, @RequestParam Boolean isFeatured) {
        log.info("Updating book featured status with id: {}, isFeatured: {}", id, isFeatured);
        BookDto bookDto = BookDto.builder().isFeatured(isFeatured).build();
        BookDto updatedBook = bookService.updateBook(id, bookDto);
        return ResponseEntity.ok(updatedBook);
    }
    
    /**
     * Tăng lượt xem
     */
    @PatchMapping("/{id}/view")
    public ResponseEntity<BookDto> incrementBookViews(@PathVariable Long id) {
        log.info("Incrementing book views with id: {}", id);
        BookDto currentBook = bookService.getBookById(id);
        Long currentViews = currentBook.getTotalViews() != null ? currentBook.getTotalViews() : 0L;
        BookDto bookDto = BookDto.builder().totalViews(currentViews + 1).build();
        BookDto updatedBook = bookService.updateBook(id, bookDto);
        return ResponseEntity.ok(updatedBook);
    }
    
    // === HELPER METHODS ===
    
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "UNKNOWN";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
    }
}

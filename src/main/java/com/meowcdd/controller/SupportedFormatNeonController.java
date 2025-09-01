package com.meowcdd.controller;

import com.meowcdd.dto.SupportedFormatDto;
import com.meowcdd.service.SupportedFormatNeonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller cho entity SupportedFormat
 * Cung cấp các REST API endpoints để quản lý định dạng file được hỗ trợ
 */
@RestController
@RequestMapping("/neon/supported-formats")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class SupportedFormatNeonController {
    
    private final SupportedFormatNeonService supportedFormatService;
    
    /**
     * Lấy tất cả định dạng được hỗ trợ
     * GET /api/v1/supported-formats
     * @return ResponseEntity chứa danh sách tất cả định dạng
     */
    @GetMapping
    public ResponseEntity<List<SupportedFormatDto>> getAllFormats() {
        log.info("API: Lấy tất cả định dạng được hỗ trợ");
        try {
            List<SupportedFormatDto> formats = supportedFormatService.getAllFormats();
            return ResponseEntity.ok(formats);
        } catch (Exception e) {
            log.error("Lỗi khi lấy tất cả định dạng: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lấy tất cả định dạng đang hoạt động
     * GET /api/v1/supported-formats/active
     * @return ResponseEntity chứa danh sách định dạng đang hoạt động
     */
    @GetMapping("/active")
    public ResponseEntity<List<SupportedFormatDto>> getActiveFormats() {
        log.info("API: Lấy tất cả định dạng đang hoạt động");
        try {
            List<SupportedFormatDto> formats = supportedFormatService.getActiveFormats();
            return ResponseEntity.ok(formats);
        } catch (Exception e) {
            log.error("Lỗi khi lấy định dạng đang hoạt động: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lấy định dạng theo ID
     * GET /api/v1/supported-formats/{id}
     * @param id ID của định dạng
     * @return ResponseEntity chứa thông tin định dạng
     */
    @GetMapping("/{id}")
    public ResponseEntity<SupportedFormatDto> getFormatById(@PathVariable Long id) {
        log.info("API: Lấy định dạng theo ID: {}", id);
        try {
            Optional<SupportedFormatDto> format = supportedFormatService.getFormatById(id);
            return format.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Lỗi khi lấy định dạng theo ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lấy định dạng theo tên
     * GET /api/v1/supported-formats/name/{formatName}
     * @param formatName tên định dạng
     * @return ResponseEntity chứa thông tin định dạng
     */
    @GetMapping("/name/{formatName}")
    public ResponseEntity<SupportedFormatDto> getFormatByName(@PathVariable String formatName) {
        log.info("API: Lấy định dạng theo tên: {}", formatName);
        try {
            Optional<SupportedFormatDto> format = supportedFormatService.getFormatByName(formatName);
            return format.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Lỗi khi lấy định dạng theo tên {}: {}", formatName, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lấy định dạng theo phần mở rộng file
     * GET /api/v1/supported-formats/extension/{fileExtension}
     * @param fileExtension phần mở rộng file (VD: .pdf, .jpg)
     * @return ResponseEntity chứa thông tin định dạng
     */
    @GetMapping("/extension/{fileExtension}")
    public ResponseEntity<SupportedFormatDto> getFormatByExtension(@PathVariable String fileExtension) {
        log.info("API: Lấy định dạng theo phần mở rộng: {}", fileExtension);
        try {
            Optional<SupportedFormatDto> format = supportedFormatService.getFormatByExtension(fileExtension);
            return format.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Lỗi khi lấy định dạng theo phần mở rộng {}: {}", fileExtension, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lấy định dạng theo MIME type
     * GET /api/v1/supported-formats/mime-type/{mimeType}
     * @param mimeType MIME type cần tìm
     * @return ResponseEntity chứa thông tin định dạng
     */
    @GetMapping("/mime-type/{mimeType}")
    public ResponseEntity<SupportedFormatDto> getFormatByMimeType(@PathVariable String mimeType) {
        log.info("API: Lấy định dạng theo MIME type: {}", mimeType);
        try {
            Optional<SupportedFormatDto> format = supportedFormatService.getFormatByMimeType(mimeType);
            return format.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Lỗi khi lấy định dạng theo MIME type {}: {}", mimeType, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lấy danh sách định dạng theo category
     * GET /api/v1/supported-formats/category/{category}
     * @param category loại định dạng (DOCUMENT, IMAGE, AUDIO, etc.)
     * @return ResponseEntity chứa danh sách định dạng thuộc category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<SupportedFormatDto>> getFormatsByCategory(@PathVariable String category) {
        log.info("API: Lấy định dạng theo category: {}", category);
        try {
            List<SupportedFormatDto> formats = supportedFormatService.getFormatsByCategory(category);
            return ResponseEntity.ok(formats);
        } catch (Exception e) {
            log.error("Lỗi khi lấy định dạng theo category {}: {}", category, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lấy danh sách định dạng đang hoạt động theo category
     * GET /api/v1/supported-formats/category/{category}/active
     * @param category loại định dạng
     * @return ResponseEntity chứa danh sách định dạng đang hoạt động
     */
    @GetMapping("/category/{category}/active")
    public ResponseEntity<List<SupportedFormatDto>> getActiveFormatsByCategory(@PathVariable String category) {
        log.info("API: Lấy định dạng đang hoạt động theo category: {}", category);
        try {
            List<SupportedFormatDto> formats = supportedFormatService.getActiveFormatsByCategory(category);
            return ResponseEntity.ok(formats);
        } catch (Exception e) {
            log.error("Lỗi khi lấy định dạng đang hoạt động theo category {}: {}", category, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Tìm kiếm định dạng theo tên
     * GET /api/v1/supported-formats/search?name={formatName}
     * @param formatName tên định dạng cần tìm (có thể là một phần)
     * @return ResponseEntity chứa danh sách định dạng tìm được
     */
    @GetMapping("/search")
    public ResponseEntity<List<SupportedFormatDto>> searchFormatsByName(@RequestParam String name) {
        log.info("API: Tìm kiếm định dạng theo tên: {}", name);
        try {
            List<SupportedFormatDto> formats = supportedFormatService.searchFormatsByName(name);
            return ResponseEntity.ok(formats);
        } catch (Exception e) {
            log.error("Lỗi khi tìm kiếm định dạng theo tên {}: {}", name, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Kiểm tra định dạng có được hỗ trợ không
     * GET /api/v1/supported-formats/check-support?extension={fileExtension}
     * @param fileExtension phần mở rộng file cần kiểm tra
     * @return ResponseEntity chứa kết quả kiểm tra
     */
    @GetMapping("/check-support")
    public ResponseEntity<Boolean> checkFormatSupport(@RequestParam String extension) {
        log.info("API: Kiểm tra định dạng có được hỗ trợ: {}", extension);
        try {
            boolean isSupported = supportedFormatService.isFormatSupported(extension);
            return ResponseEntity.ok(isSupported);
        } catch (Exception e) {
            log.error("Lỗi khi kiểm tra hỗ trợ định dạng {}: {}", extension, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Tạo định dạng mới
     * POST /api/v1/supported-formats
     * @param formatDto thông tin định dạng cần tạo
     * @return ResponseEntity chứa định dạng đã tạo
     */
    @PostMapping
    public ResponseEntity<SupportedFormatDto> createFormat(@RequestBody SupportedFormatDto formatDto) {
        log.info("API: Tạo định dạng mới: {}", formatDto.getFormatName());
        try {
            SupportedFormatDto createdFormat = supportedFormatService.createFormat(formatDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFormat);
        } catch (IllegalArgumentException e) {
            log.error("Lỗi validation khi tạo định dạng: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Lỗi khi tạo định dạng: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Cập nhật định dạng
     * PUT /api/v1/supported-formats/{id}
     * @param id ID của định dạng cần cập nhật
     * @param formatDto thông tin mới
     * @return ResponseEntity chứa định dạng đã cập nhật
     */
    @PutMapping("/{id}")
    public ResponseEntity<SupportedFormatDto> updateFormat(@PathVariable Long id, @RequestBody SupportedFormatDto formatDto) {
        log.info("API: Cập nhật định dạng với ID: {}", id);
        try {
            SupportedFormatDto updatedFormat = supportedFormatService.updateFormat(id, formatDto);
            return ResponseEntity.ok(updatedFormat);
        } catch (IllegalArgumentException e) {
            log.error("Lỗi validation khi cập nhật định dạng: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Lỗi khi cập nhật định dạng: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Vô hiệu hóa định dạng (soft delete)
     * PATCH /api/v1/supported-formats/{id}/deactivate
     * @param id ID của định dạng cần vô hiệu hóa
     * @return ResponseEntity trạng thái thao tác
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateFormat(@PathVariable Long id) {
        log.info("API: Vô hiệu hóa định dạng với ID: {}", id);
        try {
            supportedFormatService.deactivateFormat(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            log.error("Lỗi khi vô hiệu hóa định dạng: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Lỗi khi vô hiệu hóa định dạng: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Kích hoạt lại định dạng
     * PATCH /api/v1/supported-formats/{id}/activate
     * @param id ID của định dạng cần kích hoạt
     * @return ResponseEntity trạng thái thao tác
     */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateFormat(@PathVariable Long id) {
        log.info("API: Kích hoạt định dạng với ID: {}", id);
        try {
            supportedFormatService.activateFormat(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            log.error("Lỗi khi kích hoạt định dạng: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Lỗi khi kích hoạt định dạng: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Xóa hoàn toàn định dạng khỏi database
     * DELETE /api/v1/supported-formats/{id}
     * @param id ID của định dạng cần xóa
     * @return ResponseEntity trạng thái thao tác
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormat(@PathVariable Long id) {
        log.info("API: Xóa hoàn toàn định dạng với ID: {}", id);
        try {
            supportedFormatService.deleteFormat(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            log.error("Lỗi khi xóa định dạng: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Lỗi khi xóa định dạng: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

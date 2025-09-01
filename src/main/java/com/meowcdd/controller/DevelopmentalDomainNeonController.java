package com.meowcdd.controller;

import com.meowcdd.dto.DevelopmentalDomainDto;
import com.meowcdd.service.DevelopmentalDomainNeonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller cho entity DevelopmentalDomain
 * Cung cấp các REST API endpoints để quản lý lĩnh vực phát triển của trẻ em
 */
@RestController
@RequestMapping("/neon/developmental-domains")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class DevelopmentalDomainNeonController {
    
    private final DevelopmentalDomainNeonService developmentalDomainService;
    
    /**
     * Lấy tất cả lĩnh vực phát triển
     * GET /api/v1/developmental-domains
     * @return ResponseEntity chứa danh sách tất cả lĩnh vực phát triển
     */
    @GetMapping
    public ResponseEntity<List<DevelopmentalDomainDto>> getAllDomains() {
        log.info("API: Lấy tất cả lĩnh vực phát triển");
        try {
            List<DevelopmentalDomainDto> domains = developmentalDomainService.getAllDomainsOrderByName();
            return ResponseEntity.ok(domains);
        } catch (Exception e) {
            log.error("Lỗi khi lấy tất cả lĩnh vực phát triển: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lấy tất cả lĩnh vực phát triển (sắp xếp theo tên hiển thị)
     * GET /api/v1/developmental-domains/displayed-name
     * @return ResponseEntity chứa danh sách tất cả lĩnh vực phát triển
     */
    @GetMapping("/displayed-name")
    public ResponseEntity<List<DevelopmentalDomainDto>> getAllDomainsOrderByDisplayedName() {
        log.info("API: Lấy tất cả lĩnh vực phát triển (sắp xếp theo tên hiển thị)");
        try {
            List<DevelopmentalDomainDto> domains = developmentalDomainService.getAllDomainsOrderByDisplayedName();
            return ResponseEntity.ok(domains);
        } catch (Exception e) {
            log.error("Lỗi khi lấy tất cả lĩnh vực phát triển: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lấy lĩnh vực phát triển theo ID
     * GET /api/v1/developmental-domains/{id}
     * @param id ID của lĩnh vực phát triển
     * @return ResponseEntity chứa thông tin lĩnh vực phát triển
     */
    @GetMapping("/{id}")
    public ResponseEntity<DevelopmentalDomainDto> getDomainById(@PathVariable UUID id) {
        log.info("API: Lấy lĩnh vực phát triển theo ID: {}", id);
        try {
            Optional<DevelopmentalDomainDto> domain = developmentalDomainService.getDomainById(id);
            return domain.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Lỗi khi lấy lĩnh vực phát triển theo ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lấy lĩnh vực phát triển theo tên
     * GET /api/v1/developmental-domains/name/{name}
     * @param name tên lĩnh vực phát triển
     * @return ResponseEntity chứa thông tin lĩnh vực phát triển
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<DevelopmentalDomainDto> getDomainByName(@PathVariable String name) {
        log.info("API: Lấy lĩnh vực phát triển theo tên: {}", name);
        try {
            Optional<DevelopmentalDomainDto> domain = developmentalDomainService.getDomainByName(name);
            return domain.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Lỗi khi lấy lĩnh vực phát triển theo tên {}: {}", name, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lấy lĩnh vực phát triển theo tên hiển thị
     * GET /api/v1/developmental-domains/displayed-name/{displayedName}
     * @param displayedName tên hiển thị của lĩnh vực phát triển
     * @return ResponseEntity chứa thông tin lĩnh vực phát triển
     */
    @GetMapping("/displayed-name/{displayedName}")
    public ResponseEntity<DevelopmentalDomainDto> getDomainByDisplayedName(@PathVariable String displayedName) {
        log.info("API: Lấy lĩnh vực phát triển theo tên hiển thị: {}", displayedName);
        try {
            Optional<DevelopmentalDomainDto> domain = developmentalDomainService.getDomainByDisplayedName(displayedName);
            return domain.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Lỗi khi lấy lĩnh vực phát triển theo tên hiển thị {}: {}", displayedName, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lấy danh sách lĩnh vực phát triển theo category
     * GET /api/v1/developmental-domains/category/{category}
     * @param category loại lĩnh vực phát triển (CORE, SECONDARY, SPECIALIZED, INTEGRATED)
     * @return ResponseEntity chứa danh sách lĩnh vực phát triển thuộc category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<DevelopmentalDomainDto>> getDomainsByCategory(@PathVariable String category) {
        log.info("API: Lấy lĩnh vực phát triển theo category: {}", category);
        try {
            List<DevelopmentalDomainDto> domains = developmentalDomainService.getDomainsByCategory(category);
            return ResponseEntity.ok(domains);
        } catch (Exception e) {
            log.error("Lỗi khi lấy lĩnh vực phát triển theo category {}: {}", category, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lấy danh sách lĩnh vực phát triển theo category (sắp xếp theo tên hiển thị)
     * GET /api/v1/developmental-domains/category/{category}/displayed-name
     * @param category loại lĩnh vực phát triển (CORE, SECONDARY, SPECIALIZED, INTEGRATED)
     * @return ResponseEntity chứa danh sách lĩnh vực phát triển thuộc category
     */
    @GetMapping("/category/{category}/displayed-name")
    public ResponseEntity<List<DevelopmentalDomainDto>> getDomainsByCategoryOrderByDisplayedName(@PathVariable String category) {
        log.info("API: Lấy lĩnh vực phát triển theo category (sắp xếp theo tên hiển thị): {}", category);
        try {
            List<DevelopmentalDomainDto> domains = developmentalDomainService.getDomainsByCategoryOrderByDisplayedName(category);
            return ResponseEntity.ok(domains);
        } catch (Exception e) {
            log.error("Lỗi khi lấy lĩnh vực phát triển theo category {}: {}", category, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Tìm kiếm lĩnh vực phát triển theo tên
     * GET /api/v1/developmental-domains/search?name={name}
     * @param name tên lĩnh vực phát triển cần tìm (có thể là một phần)
     * @return ResponseEntity chứa danh sách lĩnh vực phát triển tìm được
     */
    @GetMapping("/search")
    public ResponseEntity<List<DevelopmentalDomainDto>> searchDomainsByName(@RequestParam String name) {
        log.info("API: Tìm kiếm lĩnh vực phát triển theo tên: {}", name);
        try {
            List<DevelopmentalDomainDto> domains = developmentalDomainService.searchDomainsByName(name);
            return ResponseEntity.ok(domains);
        } catch (Exception e) {
            log.error("Lỗi khi tìm kiếm lĩnh vực phát triển theo tên {}: {}", name, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Tìm kiếm lĩnh vực phát triển theo tên hiển thị
     * GET /api/v1/developmental-domains/search-displayed-name?displayedName={displayedName}
     * @param displayedName tên hiển thị cần tìm (có thể là một phần)
     * @return ResponseEntity chứa danh sách lĩnh vực phát triển tìm được
     */
    @GetMapping("/search-displayed-name")
    public ResponseEntity<List<DevelopmentalDomainDto>> searchDomainsByDisplayedName(@RequestParam String displayedName) {
        log.info("API: Tìm kiếm lĩnh vực phát triển theo tên hiển thị: {}", displayedName);
        try {
            List<DevelopmentalDomainDto> domains = developmentalDomainService.searchDomainsByDisplayedName(displayedName);
            return ResponseEntity.ok(domains);
        } catch (Exception e) {
            log.error("Lỗi khi tìm kiếm lĩnh vực phát triển theo tên hiển thị {}: {}", displayedName, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Tìm kiếm lĩnh vực phát triển theo từ khóa (tên hoặc mô tả)
     * GET /api/v1/developmental-domains/search-keyword?keyword={keyword}
     * @param keyword từ khóa cần tìm
     * @return ResponseEntity chứa danh sách lĩnh vực phát triển tìm được
     */
    @GetMapping("/search-keyword")
    public ResponseEntity<List<DevelopmentalDomainDto>> searchDomainsByKeyword(@RequestParam String keyword) {
        log.info("API: Tìm kiếm lĩnh vực phát triển theo từ khóa: {}", keyword);
        try {
            List<DevelopmentalDomainDto> domains = developmentalDomainService.searchDomainsByKeyword(keyword);
            return ResponseEntity.ok(domains);
        } catch (Exception e) {
            log.error("Lỗi khi tìm kiếm lĩnh vực phát triển theo từ khóa {}: {}", keyword, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Tìm kiếm lĩnh vực phát triển theo từ khóa mở rộng (tên, tên hiển thị hoặc mô tả)
     * GET /api/v1/developmental-domains/search-keyword-extended?keyword={keyword}
     * @param keyword từ khóa cần tìm
     * @return ResponseEntity chứa danh sách lĩnh vực phát triển tìm được
     */
    @GetMapping("/search-keyword-extended")
    public ResponseEntity<List<DevelopmentalDomainDto>> searchDomainsByKeywordExtended(@RequestParam String keyword) {
        log.info("API: Tìm kiếm lĩnh vực phát triển theo từ khóa mở rộng: {}", keyword);
        try {
            List<DevelopmentalDomainDto> domains = developmentalDomainService.searchDomainsByKeywordExtended(keyword);
            return ResponseEntity.ok(domains);
        } catch (Exception e) {
            log.error("Lỗi khi tìm kiếm lĩnh vực phát triển theo từ khóa mở rộng {}: {}", keyword, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lấy danh sách tất cả categories
     * GET /api/v1/developmental-domains/categories
     * @return ResponseEntity chứa danh sách categories
     */
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getDistinctCategories() {
        log.info("API: Lấy danh sách tất cả categories");
        try {
            List<String> categories = developmentalDomainService.getDistinctCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            log.error("Lỗi khi lấy danh sách categories: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Đếm số lượng lĩnh vực phát triển theo category
     * GET /api/v1/developmental-domains/count/{category}
     * @param category loại lĩnh vực phát triển
     * @return ResponseEntity chứa số lượng
     */
    @GetMapping("/count/{category}")
    public ResponseEntity<Long> countDomainsByCategory(@PathVariable String category) {
        log.info("API: Đếm số lượng lĩnh vực phát triển theo category: {}", category);
        try {
            long count = developmentalDomainService.countDomainsByCategory(category);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            log.error("Lỗi khi đếm lĩnh vực phát triển theo category {}: {}", category, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Kiểm tra lĩnh vực phát triển có tồn tại theo ID không
     * GET /api/v1/developmental-domains/exists/{id}
     * @param id ID của lĩnh vực phát triển
     * @return ResponseEntity chứa kết quả kiểm tra
     */
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> checkDomainExists(@PathVariable UUID id) {
        log.info("API: Kiểm tra lĩnh vực phát triển tồn tại theo ID: {}", id);
        try {
            boolean exists = developmentalDomainService.existsById(id);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            log.error("Lỗi khi kiểm tra tồn tại lĩnh vực phát triển {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Kiểm tra lĩnh vực phát triển có tồn tại theo tên không
     * GET /api/v1/developmental-domains/exists-name?name={name}
     * @param name tên lĩnh vực phát triển
     * @return ResponseEntity chứa kết quả kiểm tra
     */
    @GetMapping("/exists-name")
    public ResponseEntity<Boolean> checkDomainExistsByName(@RequestParam String name) {
        log.info("API: Kiểm tra lĩnh vực phát triển tồn tại theo tên: {}", name);
        try {
            boolean exists = developmentalDomainService.existsByName(name);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            log.error("Lỗi khi kiểm tra tồn tại lĩnh vực phát triển theo tên {}: {}", name, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Kiểm tra lĩnh vực phát triển có tồn tại theo tên hiển thị không
     * GET /api/v1/developmental-domains/exists-displayed-name?displayedName={displayedName}
     * @param displayedName tên hiển thị lĩnh vực phát triển
     * @return ResponseEntity chứa kết quả kiểm tra
     */
    @GetMapping("/exists-displayed-name")
    public ResponseEntity<Boolean> checkDomainExistsByDisplayedName(@RequestParam String displayedName) {
        log.info("API: Kiểm tra lĩnh vực phát triển tồn tại theo tên hiển thị: {}", displayedName);
        try {
            boolean exists = developmentalDomainService.existsByDisplayedName(displayedName);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            log.error("Lỗi khi kiểm tra tồn tại lĩnh vực phát triển theo tên hiển thị {}: {}", displayedName, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Tạo lĩnh vực phát triển mới
     * POST /api/v1/developmental-domains
     * @param domainDto thông tin lĩnh vực phát triển cần tạo
     * @return ResponseEntity chứa lĩnh vực phát triển đã tạo
     */
    @PostMapping
    public ResponseEntity<DevelopmentalDomainDto> createDomain(@RequestBody DevelopmentalDomainDto domainDto) {
        log.info("API: Tạo lĩnh vực phát triển mới: {}", domainDto.getName());
        try {
            DevelopmentalDomainDto createdDomain = developmentalDomainService.createDomain(domainDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDomain);
        } catch (IllegalArgumentException e) {
            log.error("Lỗi validation khi tạo lĩnh vực phát triển: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Lỗi khi tạo lĩnh vực phát triển: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Cập nhật lĩnh vực phát triển
     * PUT /api/v1/developmental-domains/{id}
     * @param id ID của lĩnh vực phát triển cần cập nhật
     * @param domainDto thông tin mới
     * @return ResponseEntity chứa lĩnh vực phát triển đã cập nhật
     */
    @PutMapping("/{id}")
    public ResponseEntity<DevelopmentalDomainDto> updateDomain(@PathVariable UUID id, @RequestBody DevelopmentalDomainDto domainDto) {
        log.info("API: Cập nhật lĩnh vực phát triển với ID: {}", id);
        try {
            DevelopmentalDomainDto updatedDomain = developmentalDomainService.updateDomain(id, domainDto);
            return ResponseEntity.ok(updatedDomain);
        } catch (IllegalArgumentException e) {
            log.error("Lỗi validation khi cập nhật lĩnh vực phát triển: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Lỗi khi cập nhật lĩnh vực phát triển: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Xóa lĩnh vực phát triển
     * DELETE /api/v1/developmental-domains/{id}
     * @param id ID của lĩnh vực phát triển cần xóa
     * @return ResponseEntity trạng thái thao tác
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDomain(@PathVariable UUID id) {
        log.info("API: Xóa lĩnh vực phát triển với ID: {}", id);
        try {
            developmentalDomainService.deleteDomain(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            log.error("Lỗi khi xóa lĩnh vực phát triển: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Lỗi khi xóa lĩnh vực phát triển: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

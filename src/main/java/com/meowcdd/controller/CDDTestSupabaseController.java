package com.meowcdd.controller;

import com.meowcdd.dto.CDDTestDto;
import com.meowcdd.service.CDDTestSupabaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.meowcdd.dto.PageResponseDto;

@RestController
@RequestMapping("/supabase/cdd-tests")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CDDTestSupabaseController {

    private final CDDTestSupabaseService cddTestSupabaseService;

    @PostMapping
    public ResponseEntity<CDDTestDto> createTest(@Valid @RequestBody CDDTestDto cddTestDto) {
        log.info("Creating CDD test with code: {}", cddTestDto.getAssessmentCode());
        try {
            CDDTestDto createdTest = cddTestSupabaseService.createTest(cddTestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTest);
        } catch (IllegalArgumentException e) {
            log.error("Error creating CDD test: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Paginated endpoints - phải đặt trước /{id} để tránh conflict
    @GetMapping("/paginated")
    public ResponseEntity<PageResponseDto<CDDTestDto>> getAllTestsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting all CDD tests with pagination (page: {}, size: {})", page, size);
        List<CDDTestDto> allTests = cddTestSupabaseService.getAllTests();
        PageResponseDto<CDDTestDto> tests = paginateResults(allTests, page, size);
        return ResponseEntity.ok(tests);
    }
    
    @GetMapping("/age/{ageMonths}/paginated")
    public ResponseEntity<PageResponseDto<CDDTestDto>> getTestsByAgeMonthsWithPagination(
            @PathVariable Integer ageMonths,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting CDD tests by age months: {} with pagination (page: {}, size: {})", ageMonths, page, size);
        PageResponseDto<CDDTestDto> tests = cddTestSupabaseService.getTestsByAgeMonthsWithPagination(ageMonths, page, size);
        return ResponseEntity.ok(tests);
    }
    
    @GetMapping("/age/{ageMonths}/status/{status}/paginated")
    public ResponseEntity<PageResponseDto<CDDTestDto>> getTestsByAgeMonthsAndStatusWithPagination(
            @PathVariable Integer ageMonths,
            @PathVariable String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting CDD tests by age months: {}, status: {} with pagination (page: {}, size: {})", ageMonths, status, page, size);
        try {
            PageResponseDto<CDDTestDto> tests = cddTestSupabaseService.getTestsByAgeMonthsAndStatusWithPagination(ageMonths, status, page, size);
            return ResponseEntity.ok(tests);
        } catch (IllegalArgumentException e) {
            log.error("Invalid status: {}", status);
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/age/{ageMonths}/category/{category}/paginated")
    public ResponseEntity<PageResponseDto<CDDTestDto>> getTestsByAgeMonthsAndCategoryWithPagination(
            @PathVariable Integer ageMonths,
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting CDD tests by age months: {}, category: {} with pagination (page: {}, size: {})", ageMonths, category, page, size);
        PageResponseDto<CDDTestDto> tests = cddTestSupabaseService.getTestsByAgeMonthsAndCategoryWithPagination(ageMonths, category, page, size);
        return ResponseEntity.ok(tests);
    }
    
    @GetMapping("/age/{ageMonths}/category/{category}/status/{status}/paginated")
    public ResponseEntity<PageResponseDto<CDDTestDto>> getTestsByAgeMonthsAndCategoryAndStatusWithPagination(
            @PathVariable Integer ageMonths,
            @PathVariable String category,
            @PathVariable String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting CDD tests by age months: {}, category: {}, status: {} with pagination (page: {}, size: {})", ageMonths, category, status, page, size);
        try {
            PageResponseDto<CDDTestDto> tests = cddTestSupabaseService.getTestsByAgeMonthsAndStatusAndCategoryWithPagination(ageMonths, status, category, page, size);
            return ResponseEntity.ok(tests);
        } catch (IllegalArgumentException e) {
            log.error("Invalid status: {}", status);
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/age/{ageMonths}/filter/paginated")
    public ResponseEntity<PageResponseDto<CDDTestDto>> getTestsByAgeMonthsAndStatusAndCategoryWithPagination(
            @RequestParam Integer ageMonths,
            @RequestParam String status,
            @RequestParam String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting CDD tests by age months: {}, status: {}, category: {} with pagination (page: {}, size: {})", ageMonths, status, category, page, size);
        try {
            PageResponseDto<CDDTestDto> tests = cddTestSupabaseService.getTestsByAgeMonthsAndStatusAndCategoryWithPagination(ageMonths, status, category, page, size);
            return ResponseEntity.ok(tests);
        } catch (IllegalArgumentException e) {
            log.error("Invalid status: {}", status);
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/category/{category}/status/{status}/paginated")
    public ResponseEntity<PageResponseDto<CDDTestDto>> getTestsByCategoryAndStatusWithPagination(
            @PathVariable String category,
            @PathVariable String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting CDD tests by category: {}, status: {} with pagination (page: {}, size: {})", category, status, page, size);
        try {
            List<CDDTestDto> allTests = cddTestSupabaseService.getTestsByCategory(category);
            List<CDDTestDto> filteredTests = allTests.stream()
                    .filter(test -> test.getStatus().equalsIgnoreCase(status))
                    .collect(Collectors.toList());
            PageResponseDto<CDDTestDto> tests = paginateResults(filteredTests, page, size);
            return ResponseEntity.ok(tests);
        } catch (IllegalArgumentException e) {
            log.error("Invalid status: {}", status);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CDDTestDto> getTestById(@PathVariable Long id) {
        log.info("Getting CDD test by ID: {}", id);
        Optional<CDDTestDto> test = cddTestSupabaseService.getTestById(id);
        return test.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<CDDTestDto> getTestByCode(@PathVariable String code) {
        log.info("Getting CDD test by code: {}", code);
        Optional<CDDTestDto> test = cddTestSupabaseService.getTestByCode(code);
        return test.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CDDTestDto>> getAllTests() {
        log.info("Getting all CDD tests");
        List<CDDTestDto> tests = cddTestSupabaseService.getAllTests();
        return ResponseEntity.ok(tests);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<CDDTestDto>> getTestsByStatus(@PathVariable String status) {
        log.info("Getting CDD tests by status: {}", status);
        try {
            List<CDDTestDto> tests = cddTestSupabaseService.getTestsByStatus(status);
            return ResponseEntity.ok(tests);
        } catch (IllegalArgumentException e) {
            log.error("Invalid status: {}", status);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<CDDTestDto>> getTestsByCategory(@PathVariable String category) {
        log.info("Getting CDD tests by category: {}", category);
        List<CDDTestDto> tests = cddTestSupabaseService.getTestsByCategory(category);
        return ResponseEntity.ok(tests);
    }

    @GetMapping("/age/{ageMonths}")
    public ResponseEntity<List<CDDTestDto>> getTestsByAgeMonths(@PathVariable Integer ageMonths) {
        log.info("Getting CDD tests by age months: {}", ageMonths);
        List<CDDTestDto> tests = cddTestSupabaseService.getTestsByAgeMonths(ageMonths);
        return ResponseEntity.ok(tests);
    }
    

    


    @PutMapping("/{id}")
    public ResponseEntity<CDDTestDto> updateTest(@PathVariable Long id, @Valid @RequestBody CDDTestDto cddTestDto) {
        log.info("Updating CDD test with ID: {}", id);
        try {
            CDDTestDto updatedTest = cddTestSupabaseService.updateTest(id, cddTestDto);
            return ResponseEntity.ok(updatedTest);
        } catch (IllegalArgumentException e) {
            log.error("Error updating CDD test: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable Long id) {
        log.info("Deleting CDD test with ID: {}", id);
        try {
            cddTestSupabaseService.deleteTest(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.error("Error deleting CDD test: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{code}")
    public ResponseEntity<Boolean> checkTestExists(@PathVariable String code) {
        log.info("Checking if CDD test exists with code: {}", code);
        Optional<CDDTestDto> test = cddTestSupabaseService.getTestByCode(code);
        return ResponseEntity.ok(test.isPresent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTestCount() {
        log.info("Getting total CDD test count");
        List<CDDTestDto> tests = cddTestSupabaseService.getAllTests();
        return ResponseEntity.ok((long) tests.size());
    }

    @GetMapping("/active")
    public ResponseEntity<List<CDDTestDto>> getActiveTests() {
        log.info("Getting all active CDD tests");
        List<CDDTestDto> tests = cddTestSupabaseService.getTestsByStatus("ACTIVE");
        return ResponseEntity.ok(tests);
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<CDDTestDto>> getInactiveTests() {
        log.info("Getting all inactive CDD tests");
        List<CDDTestDto> tests = cddTestSupabaseService.getTestsByStatus("INACTIVE");
        return ResponseEntity.ok(tests);
    }
    
    // Helper method for pagination
    private PageResponseDto<CDDTestDto> paginateResults(List<CDDTestDto> allTests, int page, int size) {
        int totalElements = allTests.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        
        // Validate page number
        if (page < 0) page = 0;
        if (page >= totalPages && totalPages > 0) page = totalPages - 1;
        
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalElements);
        
        List<CDDTestDto> pageContent = allTests.subList(startIndex, endIndex);
        
        return PageResponseDto.of(pageContent, page, size, totalElements);
    }
}

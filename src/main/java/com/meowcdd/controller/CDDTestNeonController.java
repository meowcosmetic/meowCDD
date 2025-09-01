package com.meowcdd.controller;

import com.meowcdd.entity.neon.CDDTestNeon;
import com.meowcdd.service.CDDTestNeonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/neon/cdd-tests")
@RequiredArgsConstructor
@Slf4j
public class CDDTestNeonController {

    private final CDDTestNeonService cddTestNeonService;

    @GetMapping("/paginated")
    public ResponseEntity<Page<CDDTestNeon>> getAllCDDTestsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting all CDD tests with pagination (page: {}, size: {})", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<CDDTestNeon> tests = cddTestNeonService.getAllCDDTestsWithPagination(pageable);
        
        // Log detailed data from database
        log.info("=== DATABASE DATA DEBUG ===");
        log.info("Total elements: {}", tests.getTotalElements());
        log.info("Total pages: {}", tests.getTotalPages());
        log.info("Current page: {}", tests.getNumber());
        log.info("Page size: {}", tests.getSize());
        
        for (CDDTestNeon test : tests.getContent()) {
            log.info("--- Test ID: {} ---", test.getId());
            log.info("Assessment Code: {}", test.getAssessmentCode());
            log.info("Category: {}", test.getCategory());
            log.info("Status: {}", test.getStatus());
            log.info("Names JSON: {}", test.getNamesJson());
            log.info("Descriptions JSON: {}", test.getDescriptionsJson());
            log.info("Instructions JSON: {}", test.getInstructionsJson());
            log.info("Questions JSON: {}", test.getQuestionsJson());
            log.info("Scoring Criteria JSON: {}", test.getScoringCriteriaJson());
            log.info("Required Materials JSON: {}", test.getRequiredMaterialsJson());
            log.info("Notes JSON: {}", test.getNotesJson());
            log.info("--- End Test ID: {} ---", test.getId());
        }
        log.info("=== END DATABASE DATA DEBUG ===");
        
        return ResponseEntity.ok(tests);
    }

    @GetMapping
    public ResponseEntity<List<CDDTestNeon>> getAllCDDTests() {
        log.info("Getting all CDD tests");
        List<CDDTestNeon> tests = cddTestNeonService.getAllCDDTests();
        return ResponseEntity.ok(tests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CDDTestNeon> getCDDTestById(@PathVariable Long id) {
        log.info("Getting CDD test by ID: {}", id);
        return cddTestNeonService.getCDDTestById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{assessmentCode}")
    public ResponseEntity<CDDTestNeon> getCDDTestByAssessmentCode(@PathVariable String assessmentCode) {
        log.info("Getting CDD test by assessment code: {}", assessmentCode);
        return cddTestNeonService.getCDDTestByAssessmentCode(assessmentCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<CDDTestNeon>> getCDDTestsByStatus(@PathVariable CDDTestNeon.Status status) {
        log.info("Getting CDD tests by status: {}", status);
        List<CDDTestNeon> tests = cddTestNeonService.getCDDTestsByStatus(status);
        return ResponseEntity.ok(tests);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<CDDTestNeon>> getCDDTestsByCategory(@PathVariable String category) {
        log.info("Getting CDD tests by category: {}", category);
        List<CDDTestNeon> tests = cddTestNeonService.getCDDTestsByCategory(category);
        return ResponseEntity.ok(tests);
    }

    @GetMapping("/category/{category}/paginated")
    public ResponseEntity<Page<CDDTestNeon>> getCDDTestsByCategoryWithPagination(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting CDD tests by category: {} with pagination (page: {}, size: {})", 
                category, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<CDDTestNeon> tests = cddTestNeonService.getCDDTestsByCategoryWithPagination(category, pageable);
        return ResponseEntity.ok(tests);
    }

    @GetMapping("/category/{category}/status/{status}/paginated")
    public ResponseEntity<Page<CDDTestNeon>> getCDDTestsByCategoryAndStatusWithPagination(
            @PathVariable String category,
            @PathVariable CDDTestNeon.Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting CDD tests by category: {}, status: {} with pagination (page: {}, size: {})", 
                category, status, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<CDDTestNeon> tests = cddTestNeonService.getCDDTestsByCategoryAndStatusWithPagination(category, status, pageable);
        return ResponseEntity.ok(tests);
    }

    @GetMapping("/age/{ageMonths}")
    public ResponseEntity<List<CDDTestNeon>> getCDDTestsForAge(@PathVariable Integer ageMonths) {
        log.info("Getting CDD tests for age: {} months", ageMonths);
        List<CDDTestNeon> tests = cddTestNeonService.getCDDTestsForAge(ageMonths);
        return ResponseEntity.ok(tests);
    }

    @GetMapping("/age/{ageMonths}/status/{status}/paginated")
    public ResponseEntity<Page<CDDTestNeon>> getCDDTestsForAgeAndStatusWithPagination(
            @PathVariable Integer ageMonths,
            @PathVariable CDDTestNeon.Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting CDD tests for age: {} months, status: {} with pagination (page: {}, size: {})", 
                ageMonths, status, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<CDDTestNeon> tests = cddTestNeonService.getCDDTestsForAgeAndStatusWithPagination(ageMonths, status, pageable);
        return ResponseEntity.ok(tests);
    }

    @GetMapping("/age/{ageMonths}/category/{category}/status/{status}/paginated")
    public ResponseEntity<Page<CDDTestNeon>> getCDDTestsForAgeCategoryAndStatusWithPagination(
            @PathVariable Integer ageMonths,
            @PathVariable String category,
            @PathVariable CDDTestNeon.Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting CDD tests for age: {} months, category: {}, status: {} with pagination (page: {}, size: {})", 
                ageMonths, category, status, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<CDDTestNeon> tests = cddTestNeonService.getCDDTestsForAgeCategoryAndStatusWithPagination(ageMonths, category, status, pageable);
        return ResponseEntity.ok(tests);
    }

    @PostMapping
    public ResponseEntity<CDDTestNeon> createCDDTest(@RequestBody CDDTestNeon cddTest) {
        log.info("Creating new CDD test: {}", cddTest.getAssessmentCode());
        CDDTestNeon createdTest = cddTestNeonService.createCDDTest(cddTest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CDDTestNeon> updateCDDTest(@PathVariable Long id, @RequestBody CDDTestNeon cddTest) {
        log.info("Updating CDD test with ID: {}", id);
        return cddTestNeonService.updateCDDTest(id, cddTest)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCDDTest(@PathVariable Long id) {
        log.info("Deleting CDD test with ID: {}", id);
        if (cddTestNeonService.deleteCDDTest(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CDDTestNeon>> searchCDDTests(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Searching CDD tests with keyword: {}", keyword);
        Pageable pageable = PageRequest.of(page, size);
        Page<CDDTestNeon> tests = cddTestNeonService.searchCDDTests(keyword, pageable);
        return ResponseEntity.ok(tests);
    }

    @GetMapping("/stats/status")
    public ResponseEntity<Long> getCDDTestCountByStatus(@RequestParam CDDTestNeon.Status status) {
        log.info("Getting CDD test count by status: {}", status);
        long count = cddTestNeonService.getCDDTestCountByStatus(status);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/stats/category")
    public ResponseEntity<List<Object[]>> getCDDTestCountByCategory() {
        log.info("Getting CDD test count by category");
        List<Object[]> stats = cddTestNeonService.getCDDTestCountByCategory();
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/test/raw-data/{id}")
    public ResponseEntity<Object[]> getRawTestData(@PathVariable Long id) {
        log.info("Getting raw test data for ID: {}", id);
        Object[] rawData = cddTestNeonService.getRawTestData(id);
        if (rawData != null) {
            return ResponseEntity.ok(rawData);
        }
        return ResponseEntity.notFound().build();
    }
}

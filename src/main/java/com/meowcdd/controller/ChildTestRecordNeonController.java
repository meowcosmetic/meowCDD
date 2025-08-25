package com.meowcdd.controller;

import com.meowcdd.entity.neon.ChildTestRecordNeon;
import com.meowcdd.service.ChildTestRecordNeonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/neon/child-test-records", "/supabase/child-test-records"})
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ChildTestRecordNeonController {

    private final ChildTestRecordNeonService childTestRecordNeonService;

    @PostMapping
    public ResponseEntity<ChildTestRecordNeon> createChildTestRecord(@Valid @RequestBody ChildTestRecordNeon childTestRecord) {
        log.info("Creating child test record (Neon) for child ID: {}", childTestRecord.getChildId());
        ChildTestRecordNeon createdRecord = childTestRecordNeonService.createChildTestRecord(childTestRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChildTestRecordNeon> getChildTestRecordById(@PathVariable Long id) {
        log.info("Getting child test record by ID (Neon): {}", id);
        Optional<ChildTestRecordNeon> record = childTestRecordNeonService.getChildTestRecordById(id);
        return record.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ChildTestRecordNeon>> getAllChildTestRecords() {
        log.info("Getting all child test records (Neon)");
        List<ChildTestRecordNeon> records = childTestRecordNeonService.getAllChildTestRecords();
        return ResponseEntity.ok(records);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<ChildTestRecordNeon>> getAllChildTestRecordsWithPagination(Pageable pageable) {
        log.info("Getting all child test records with pagination (Neon)");
        Page<ChildTestRecordNeon> records = childTestRecordNeonService.getAllChildTestRecordsWithPagination(pageable);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/child/{childId}")
    public ResponseEntity<List<ChildTestRecordNeon>> getChildTestRecordsByChildId(@PathVariable String childId) {
        log.info("Getting child test records by child ID (Neon): {}", childId);
        List<ChildTestRecordNeon> records = childTestRecordNeonService.getChildTestRecordsByChildId(childId);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/child/{childId}/paginated")
    public ResponseEntity<Page<ChildTestRecordNeon>> getChildTestRecordsByChildIdWithPagination(
            @PathVariable String childId, Pageable pageable) {
        log.info("Getting child test records by child ID with pagination (Neon): {}", childId);
        Page<ChildTestRecordNeon> records = childTestRecordNeonService.getChildTestRecordsByChildIdWithPagination(childId, pageable);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/test/{testId}")
    public ResponseEntity<List<ChildTestRecordNeon>> getChildTestRecordsByTestId(@PathVariable String testId) {
        log.info("Getting child test records by test ID (Neon): {}", testId);
        List<ChildTestRecordNeon> records = childTestRecordNeonService.getChildTestRecordsByTestId(testId);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/test/{testId}/paginated")
    public ResponseEntity<Page<ChildTestRecordNeon>> getChildTestRecordsByTestIdWithPagination(
            @PathVariable String testId, Pageable pageable) {
        log.info("Getting child test records by test ID with pagination (Neon): {}", testId);
        Page<ChildTestRecordNeon> records = childTestRecordNeonService.getChildTestRecordsByTestIdWithPagination(testId, pageable);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ChildTestRecordNeon>> getChildTestRecordsByStatus(@PathVariable String status) {
        log.info("Getting child test records by status (Neon): {}", status);
        try {
            ChildTestRecordNeon.Status statusEnum = ChildTestRecordNeon.Status.valueOf(status.toUpperCase());
            List<ChildTestRecordNeon> records = childTestRecordNeonService.getChildTestRecordsByStatus(statusEnum);
            return ResponseEntity.ok(records);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/status/{status}/paginated")
    public ResponseEntity<Page<ChildTestRecordNeon>> getChildTestRecordsByStatusWithPagination(
            @PathVariable String status, Pageable pageable) {
        log.info("Getting child test records by status with pagination (Neon): {}", status);
        try {
            ChildTestRecordNeon.Status statusEnum = ChildTestRecordNeon.Status.valueOf(status.toUpperCase());
            Page<ChildTestRecordNeon> records = childTestRecordNeonService.getChildTestRecordsByStatusWithPagination(statusEnum, pageable);
            return ResponseEntity.ok(records);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/assessor/{assessor}")
    public ResponseEntity<List<ChildTestRecordNeon>> getChildTestRecordsByAssessor(@PathVariable String assessor) {
        log.info("Getting child test records by assessor (Neon): {}", assessor);
        List<ChildTestRecordNeon> records = childTestRecordNeonService.getChildTestRecordsByAssessor(assessor);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/assessor/{assessor}/paginated")
    public ResponseEntity<Page<ChildTestRecordNeon>> getChildTestRecordsByAssessorWithPagination(
            @PathVariable String assessor, Pageable pageable) {
        log.info("Getting child test records by assessor with pagination (Neon): {}", assessor);
        Page<ChildTestRecordNeon> records = childTestRecordNeonService.getChildTestRecordsByAssessorWithPagination(assessor, pageable);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/child/{childId}/test/{testId}")
    public ResponseEntity<List<ChildTestRecordNeon>> getChildTestRecordsByChildIdAndTestId(
            @PathVariable String childId, @PathVariable String testId) {
        log.info("Getting child test records by child ID: {} and test ID: {} (Neon)", childId, testId);
        List<ChildTestRecordNeon> records = childTestRecordNeonService.getChildTestRecordsByChildIdAndTestId(childId, testId);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<ChildTestRecordNeon>> getChildTestRecordsByTestDateBetween(
            @RequestParam String startDate, @RequestParam String endDate) {
        log.info("Getting child test records by test date between: {} and {} (Neon)", startDate, endDate);
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        List<ChildTestRecordNeon> records = childTestRecordNeonService.getChildTestRecordsByTestDateBetween(start, end);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/date-range/paginated")
    public ResponseEntity<Page<ChildTestRecordNeon>> getChildTestRecordsByTestDateBetweenWithPagination(
            @RequestParam String startDate, @RequestParam String endDate, Pageable pageable) {
        log.info("Getting child test records by test date between with pagination: {} and {} (Neon)", startDate, endDate);
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        Page<ChildTestRecordNeon> records = childTestRecordNeonService.getChildTestRecordsByTestDateBetweenWithPagination(start, end, pageable);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/child/{childId}/date-range")
    public ResponseEntity<List<ChildTestRecordNeon>> getChildTestRecordsByChildIdAndTestDateBetween(
            @PathVariable String childId, @RequestParam String startDate, @RequestParam String endDate) {
        log.info("Getting child test records by child ID: {} and test date between: {} and {} (Neon)", childId, startDate, endDate);
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        List<ChildTestRecordNeon> records = childTestRecordNeonService.getChildTestRecordsByChildIdAndTestDateBetween(childId, start, end);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/score-range")
    public ResponseEntity<List<ChildTestRecordNeon>> getChildTestRecordsByScoreBetween(
            @RequestParam Double minScore, @RequestParam Double maxScore) {
        log.info("Getting child test records by score between: {} and {} (Neon)", minScore, maxScore);
        List<ChildTestRecordNeon> records = childTestRecordNeonService.getChildTestRecordsByScoreBetween(minScore, maxScore);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/score-range/paginated")
    public ResponseEntity<Page<ChildTestRecordNeon>> getChildTestRecordsByScoreBetweenWithPagination(
            @RequestParam Double minScore, @RequestParam Double maxScore, Pageable pageable) {
        log.info("Getting child test records by score between with pagination: {} and {} (Neon)", minScore, maxScore);
        Page<ChildTestRecordNeon> records = childTestRecordNeonService.getChildTestRecordsByScoreBetweenWithPagination(minScore, maxScore, pageable);
        return ResponseEntity.ok(records);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChildTestRecordNeon> updateChildTestRecord(
            @PathVariable Long id, @Valid @RequestBody ChildTestRecordNeon childTestRecord) {
        log.info("Updating child test record with ID (Neon): {}", id);
        Optional<ChildTestRecordNeon> updatedRecord = childTestRecordNeonService.updateChildTestRecord(id, childTestRecord);
        return updatedRecord.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChildTestRecord(@PathVariable Long id) {
        log.info("Deleting child test record with ID (Neon): {}", id);
        boolean deleted = childTestRecordNeonService.deleteChildTestRecord(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/child/{childId}/count")
    public ResponseEntity<Long> getChildTestRecordCountByChildId(@PathVariable String childId) {
        log.info("Getting child test record count by child ID (Neon): {}", childId);
        long count = childTestRecordNeonService.getChildTestRecordCountByChildId(childId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/test/{testId}/count")
    public ResponseEntity<Long> getChildTestRecordCountByTestId(@PathVariable String testId) {
        log.info("Getting child test record count by test ID (Neon): {}", testId);
        long count = childTestRecordNeonService.getChildTestRecordCountByTestId(testId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/status/{status}/count")
    public ResponseEntity<Long> getChildTestRecordCountByStatus(@PathVariable String status) {
        log.info("Getting child test record count by status (Neon): {}", status);
        try {
            ChildTestRecordNeon.Status statusEnum = ChildTestRecordNeon.Status.valueOf(status.toUpperCase());
            long count = childTestRecordNeonService.getChildTestRecordCountByStatus(statusEnum);
            return ResponseEntity.ok(count);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/assessor/{assessor}/count")
    public ResponseEntity<Long> getChildTestRecordCountByAssessor(@PathVariable String assessor) {
        log.info("Getting child test record count by assessor (Neon): {}", assessor);
        long count = childTestRecordNeonService.getChildTestRecordCountByAssessor(assessor);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/child/{childId}/average-score")
    public ResponseEntity<Double> getAverageScoreByChildId(@PathVariable String childId) {
        log.info("Getting average score by child ID (Neon): {}", childId);
        Double averageScore = childTestRecordNeonService.getAverageScoreByChildId(childId);
        return ResponseEntity.ok(averageScore);
    }

    @GetMapping("/test/{testId}/average-score")
    public ResponseEntity<Double> getAverageScoreByTestId(@PathVariable String testId) {
        log.info("Getting average score by test ID (Neon): {}", testId);
        Double averageScore = childTestRecordNeonService.getAverageScoreByTestId(testId);
        return ResponseEntity.ok(averageScore);
    }
}

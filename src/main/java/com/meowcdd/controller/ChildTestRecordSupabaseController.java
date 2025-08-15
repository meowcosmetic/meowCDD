package com.meowcdd.controller;

import com.meowcdd.dto.ChildTestRecordSupabaseDto;
import com.meowcdd.service.ChildTestRecordSupabaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/supabase/child-test-records")
@RequiredArgsConstructor
@Slf4j
public class ChildTestRecordSupabaseController {

    private final ChildTestRecordSupabaseService childTestRecordSupabaseService;

    @PostMapping
    public ResponseEntity<ChildTestRecordSupabaseDto> createChildTestRecord(@Valid @RequestBody ChildTestRecordSupabaseDto childTestRecordDto) {
        log.info("Creating child test record (Supabase): {}", childTestRecordDto.getExternalId());
        ChildTestRecordSupabaseDto createdRecord = childTestRecordSupabaseService.createChildTestRecord(childTestRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChildTestRecordSupabaseDto> getChildTestRecordById(@PathVariable Long id) {
        log.info("Getting child test record by ID (Supabase): {}", id);
        ChildTestRecordSupabaseDto record = childTestRecordSupabaseService.getChildTestRecordById(id);
        return ResponseEntity.ok(record);
    }

    @GetMapping("/external/{externalId}")
    public ResponseEntity<ChildTestRecordSupabaseDto> getChildTestRecordByExternalId(@PathVariable String externalId) {
        log.info("Getting child test record by external ID (Supabase): {}", externalId);
        ChildTestRecordSupabaseDto record = childTestRecordSupabaseService.getChildTestRecordByExternalId(externalId);
        return ResponseEntity.ok(record);
    }

    @GetMapping
    public ResponseEntity<List<ChildTestRecordSupabaseDto>> getAllChildTestRecords() {
        log.info("Getting all child test records (Supabase)");
        List<ChildTestRecordSupabaseDto> records = childTestRecordSupabaseService.getAllChildTestRecords();
        return ResponseEntity.ok(records);
    }

    @GetMapping("/child/{childId}")
    public ResponseEntity<List<ChildTestRecordSupabaseDto>> getChildTestRecordsByChildId(@PathVariable Long childId) {
        log.info("Getting child test records by child ID (Supabase): {}", childId);
        List<ChildTestRecordSupabaseDto> records = childTestRecordSupabaseService.getChildTestRecordsByChildId(childId);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/test/{testId}")
    public ResponseEntity<List<ChildTestRecordSupabaseDto>> getChildTestRecordsByTestId(@PathVariable Long testId) {
        log.info("Getting child test records by test ID (Supabase): {}", testId);
        List<ChildTestRecordSupabaseDto> records = childTestRecordSupabaseService.getChildTestRecordsByTestId(testId);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/test-type/{testType}")
    public ResponseEntity<List<ChildTestRecordSupabaseDto>> getChildTestRecordsByTestType(@PathVariable String testType) {
        log.info("Getting child test records by test type (Supabase): {}", testType);
        List<ChildTestRecordSupabaseDto> records = childTestRecordSupabaseService.getChildTestRecordsByTestType(testType);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ChildTestRecordSupabaseDto>> getChildTestRecordsByStatus(@PathVariable String status) {
        log.info("Getting child test records by status (Supabase): {}", status);
        List<ChildTestRecordSupabaseDto> records = childTestRecordSupabaseService.getChildTestRecordsByStatus(status);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/child/{childId}/test-type/{testType}")
    public ResponseEntity<List<ChildTestRecordSupabaseDto>> getChildTestRecordsByChildIdAndTestType(
            @PathVariable Long childId, @PathVariable String testType) {
        log.info("Getting child test records by child ID: {} and test type: {} (Supabase)", childId, testType);
        List<ChildTestRecordSupabaseDto> records = childTestRecordSupabaseService.getChildTestRecordsByChildIdAndTestType(childId, testType);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/child/{childId}/status/{status}")
    public ResponseEntity<List<ChildTestRecordSupabaseDto>> getChildTestRecordsByChildIdAndStatus(
            @PathVariable Long childId, @PathVariable String status) {
        log.info("Getting child test records by child ID: {} and status: {} (Supabase)", childId, status);
        List<ChildTestRecordSupabaseDto> records = childTestRecordSupabaseService.getChildTestRecordsByChildIdAndStatus(childId, status);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<ChildTestRecordSupabaseDto>> getChildTestRecordsByDateRange(
            @RequestParam String startDate, @RequestParam String endDate) {
        log.info("Getting child test records by date range: {} to {} (Supabase)", startDate, endDate);
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        List<ChildTestRecordSupabaseDto> records = childTestRecordSupabaseService.getChildTestRecordsByDateRange(start, end);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/result-level/{resultLevel}")
    public ResponseEntity<List<ChildTestRecordSupabaseDto>> getChildTestRecordsByResultLevel(@PathVariable String resultLevel) {
        log.info("Getting child test records by result level (Supabase): {}", resultLevel);
        List<ChildTestRecordSupabaseDto> records = childTestRecordSupabaseService.getChildTestRecordsByResultLevel(resultLevel);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/score-range")
    public ResponseEntity<List<ChildTestRecordSupabaseDto>> getChildTestRecordsByPercentageScoreRange(
            @RequestParam Double minScore, @RequestParam Double maxScore) {
        log.info("Getting child test records by percentage score range: {} to {} (Supabase)", minScore, maxScore);
        List<ChildTestRecordSupabaseDto> records = childTestRecordSupabaseService.getChildTestRecordsByPercentageScoreRange(minScore, maxScore);
        return ResponseEntity.ok(records);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChildTestRecordSupabaseDto> updateChildTestRecord(
            @PathVariable Long id, @Valid @RequestBody ChildTestRecordSupabaseDto childTestRecordDto) {
        log.info("Updating child test record with ID (Supabase): {}", id);
        ChildTestRecordSupabaseDto updatedRecord = childTestRecordSupabaseService.updateChildTestRecord(id, childTestRecordDto);
        return ResponseEntity.ok(updatedRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChildTestRecord(@PathVariable Long id) {
        log.info("Deleting child test record with ID (Supabase): {}", id);
        childTestRecordSupabaseService.deleteChildTestRecord(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{externalId}")
    public ResponseEntity<Boolean> checkChildTestRecordExists(@PathVariable String externalId) {
        log.info("Checking if child test record exists with external ID (Supabase): {}", externalId);
        boolean exists = childTestRecordSupabaseService.existsByExternalId(externalId);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/child/{childId}/test/{testId}/exists")
    public ResponseEntity<Boolean> checkChildTestRecordExistsByChildIdAndTestId(
            @PathVariable Long childId, @PathVariable Long testId) {
        log.info("Checking if child test record exists by child ID: {} and test ID: {} (Supabase)", childId, testId);
        boolean exists = childTestRecordSupabaseService.existsByChildIdAndTestId(childId, testId);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/child/{childId}/completed-count")
    public ResponseEntity<Long> getCompletedTestsCountByChildId(@PathVariable Long childId) {
        log.info("Getting completed tests count by child ID (Supabase): {}", childId);
        long count = childTestRecordSupabaseService.countCompletedTestsByChildId(childId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/child/{childId}/average-score")
    public ResponseEntity<Double> getAverageScoreByChildId(@PathVariable Long childId) {
        log.info("Getting average score by child ID (Supabase): {}", childId);
        Double averageScore = childTestRecordSupabaseService.getAverageScoreByChildId(childId);
        return ResponseEntity.ok(averageScore);
    }

    @GetMapping("/child/{childId}/last-test-date")
    public ResponseEntity<LocalDateTime> getLastTestDateByChildId(@PathVariable Long childId) {
        log.info("Getting last test date by child ID (Supabase): {}", childId);
        LocalDateTime lastTestDate = childTestRecordSupabaseService.getLastTestDateByChildId(childId);
        return ResponseEntity.ok(lastTestDate);
    }
}

package com.meowcdd.controller;

import com.meowcdd.dto.TrackingQuestionDto;
import com.meowcdd.service.TrackingQuestionSupabaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracking-questions")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class TrackingQuestionSupabaseController {
    
    private final TrackingQuestionSupabaseService trackingQuestionService;
    
    /**
     * Tạo câu hỏi tracking mới
     */
    @PostMapping
    public ResponseEntity<TrackingQuestionDto> createTrackingQuestion(@RequestBody TrackingQuestionDto dto) {
        try {
            log.info("Creating new tracking question for domain: {}", dto.getDomain());
            TrackingQuestionDto created = trackingQuestionService.createTrackingQuestion(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            log.error("Error creating tracking question: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lấy tất cả câu hỏi tracking
     */
    @GetMapping
    public ResponseEntity<List<TrackingQuestionDto>> getAllTrackingQuestions() {
        try {
            log.info("Fetching all tracking questions");
            List<TrackingQuestionDto> questions = trackingQuestionService.getAllTrackingQuestions();
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            log.error("Error fetching all tracking questions: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lấy câu hỏi tracking theo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<TrackingQuestionDto> getTrackingQuestionById(@PathVariable Long id) {
        try {
            log.info("Fetching tracking question with id: {}", id);
            TrackingQuestionDto question = trackingQuestionService.getTrackingQuestionById(id);
            return ResponseEntity.ok(question);
        } catch (RuntimeException e) {
            log.error("Tracking question not found with id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error fetching tracking question with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Cập nhật câu hỏi tracking
     */
    @PutMapping("/{id}")
    public ResponseEntity<TrackingQuestionDto> updateTrackingQuestion(@PathVariable Long id, @RequestBody TrackingQuestionDto dto) {
        try {
            log.info("Updating tracking question with id: {}", id);
            TrackingQuestionDto updated = trackingQuestionService.updateTrackingQuestion(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            log.error("Tracking question not found with id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error updating tracking question with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Xóa câu hỏi tracking
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrackingQuestion(@PathVariable Long id) {
        try {
            log.info("Deleting tracking question with id: {}", id);
            trackingQuestionService.deleteTrackingQuestion(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Tracking question not found with id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error deleting tracking question with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lấy câu hỏi theo domain
     */
    @GetMapping("/domain/{domain}")
    public ResponseEntity<List<TrackingQuestionDto>> getTrackingQuestionsByDomain(@PathVariable String domain) {
        try {
            log.info("Fetching tracking questions by domain: {}", domain);
            List<TrackingQuestionDto> questions = trackingQuestionService.getTrackingQuestionsByDomain(domain);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            log.error("Error fetching tracking questions by domain {}: {}", domain, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lấy câu hỏi theo độ tuổi
     */
    @GetMapping("/age-range/{ageRange}")
    public ResponseEntity<List<TrackingQuestionDto>> getTrackingQuestionsByAgeRange(@PathVariable String ageRange) {
        try {
            log.info("Fetching tracking questions by age range: {}", ageRange);
            List<TrackingQuestionDto> questions = trackingQuestionService.getTrackingQuestionsByAgeRange(ageRange);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            log.error("Error fetching tracking questions by age range {}: {}", ageRange, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lấy câu hỏi theo domain và độ tuổi
     */
    @GetMapping("/domain/{domain}/age-range/{ageRange}")
    public ResponseEntity<List<TrackingQuestionDto>> getTrackingQuestionsByDomainAndAgeRange(
            @PathVariable String domain, 
            @PathVariable String ageRange) {
        try {
            log.info("Fetching tracking questions by domain: {} and age range: {}", domain, ageRange);
            List<TrackingQuestionDto> questions = trackingQuestionService.getTrackingQuestionsByDomainAndAgeRange(domain, ageRange);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            log.error("Error fetching tracking questions by domain {} and age range {}: {}", domain, ageRange, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lấy tất cả câu hỏi có sẵn cho một độ tuổi cụ thể
     */
    @GetMapping("/available/{ageRange}")
    public ResponseEntity<List<TrackingQuestionDto>> getAvailableQuestionsByAgeRange(@PathVariable String ageRange) {
        try {
            log.info("Fetching available questions by age range: {}", ageRange);
            List<TrackingQuestionDto> questions = trackingQuestionService.getAvailableQuestionsByAgeRange(ageRange);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            log.error("Error fetching available questions by age range {}: {}", ageRange, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

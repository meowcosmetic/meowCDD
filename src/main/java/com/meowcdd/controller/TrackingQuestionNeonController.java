package com.meowcdd.controller;

import com.meowcdd.entity.neon.TrackingQuestionNeon;
import com.meowcdd.service.TrackingQuestionNeonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/neon/tracking-questions")
@RequiredArgsConstructor
@Slf4j
public class TrackingQuestionNeonController {
    
    private final TrackingQuestionNeonService trackingQuestionNeonService;
    
    /**
     * Lấy tất cả câu hỏi tracking
     */
    @GetMapping
    public ResponseEntity<List<TrackingQuestionNeon>> getAllTrackingQuestions() {
        log.info("Getting all tracking questions (Neon)");
        List<TrackingQuestionNeon> questions = trackingQuestionNeonService.getAllTrackingQuestions();
            return ResponseEntity.ok(questions);
    }

    /**
     * Lấy tất cả câu hỏi tracking với phân trang
     */
    @GetMapping("/paginated")
    public ResponseEntity<Page<TrackingQuestionNeon>> getAllTrackingQuestionsWithPagination(Pageable pageable) {
        log.info("Getting all tracking questions with pagination (Neon)");
        Page<TrackingQuestionNeon> questions = trackingQuestionNeonService.getAllTrackingQuestionsWithPagination(pageable);
        return ResponseEntity.ok(questions);
    }
    
    /**
     * Lấy câu hỏi tracking theo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<TrackingQuestionNeon> getTrackingQuestionById(@PathVariable Long id) {
        log.info("Getting tracking question by ID (Neon): {}", id);
        Optional<TrackingQuestionNeon> question = trackingQuestionNeonService.getTrackingQuestionById(id);
        return question.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Tạo câu hỏi tracking mới
     */
    @PostMapping
    public ResponseEntity<TrackingQuestionNeon> createTrackingQuestion(@RequestBody TrackingQuestionNeon trackingQuestion) {
        log.info("Creating new tracking question (Neon)");
        TrackingQuestionNeon created = trackingQuestionNeonService.createTrackingQuestion(trackingQuestion);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    /**
     * Cập nhật câu hỏi tracking
     */
    @PutMapping("/{id}")
    public ResponseEntity<TrackingQuestionNeon> updateTrackingQuestion(@PathVariable Long id, @RequestBody TrackingQuestionNeon trackingQuestion) {
        log.info("Updating tracking question with ID (Neon): {}", id);
        Optional<TrackingQuestionNeon> updated = trackingQuestionNeonService.updateTrackingQuestion(id, trackingQuestion);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Xóa câu hỏi tracking
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrackingQuestion(@PathVariable Long id) {
        log.info("Deleting tracking question with ID (Neon): {}", id);
        boolean deleted = trackingQuestionNeonService.deleteTrackingQuestion(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    
    /**
     * Lấy câu hỏi theo category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<TrackingQuestionNeon>> getTrackingQuestionsByCategory(@PathVariable String category) {
        log.info("Getting tracking questions by category (Neon): {}", category);
        List<TrackingQuestionNeon> questions = trackingQuestionNeonService.getTrackingQuestionsByCategory(category);
        return ResponseEntity.ok(questions);
    }

    /**
     * Lấy câu hỏi theo category với phân trang
     */
    @GetMapping("/category/{category}/paginated")
    public ResponseEntity<Page<TrackingQuestionNeon>> getTrackingQuestionsByCategoryWithPagination(
            @PathVariable String category, Pageable pageable) {
        log.info("Getting tracking questions by category with pagination (Neon): {}", category);
        Page<TrackingQuestionNeon> questions = trackingQuestionNeonService.getTrackingQuestionsByCategoryWithPagination(category, pageable);
        return ResponseEntity.ok(questions);
    }
    
    /**
     * Lấy câu hỏi theo subcategory
     */
    @GetMapping("/subcategory/{subcategory}")
    public ResponseEntity<List<TrackingQuestionNeon>> getTrackingQuestionsBySubcategory(@PathVariable String subcategory) {
        log.info("Getting tracking questions by subcategory (Neon): {}", subcategory);
        List<TrackingQuestionNeon> questions = trackingQuestionNeonService.getTrackingQuestionsBySubcategory(subcategory);
        return ResponseEntity.ok(questions);
    }

    /**
     * Lấy câu hỏi theo subcategory với phân trang
     */
    @GetMapping("/subcategory/{subcategory}/paginated")
    public ResponseEntity<Page<TrackingQuestionNeon>> getTrackingQuestionsBySubcategoryWithPagination(
            @PathVariable String subcategory, Pageable pageable) {
        log.info("Getting tracking questions by subcategory with pagination (Neon): {}", subcategory);
        Page<TrackingQuestionNeon> questions = trackingQuestionNeonService.getTrackingQuestionsBySubcategoryWithPagination(subcategory, pageable);
        return ResponseEntity.ok(questions);
    }
    
    /**
     * Lấy câu hỏi theo question type
     */
    @GetMapping("/question-type/{questionType}")
    public ResponseEntity<List<TrackingQuestionNeon>> getTrackingQuestionsByQuestionType(@PathVariable String questionType) {
        log.info("Getting tracking questions by question type (Neon): {}", questionType);
        try {
            TrackingQuestionNeon.QuestionType type = TrackingQuestionNeon.QuestionType.valueOf(questionType.toUpperCase());
            List<TrackingQuestionNeon> questions = trackingQuestionNeonService.getTrackingQuestionsByQuestionType(type);
            return ResponseEntity.ok(questions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Lấy câu hỏi theo question type với phân trang
     */
    @GetMapping("/question-type/{questionType}/paginated")
    public ResponseEntity<Page<TrackingQuestionNeon>> getTrackingQuestionsByQuestionTypeWithPagination(
            @PathVariable String questionType, Pageable pageable) {
        log.info("Getting tracking questions by question type with pagination (Neon): {}", questionType);
        try {
            TrackingQuestionNeon.QuestionType type = TrackingQuestionNeon.QuestionType.valueOf(questionType.toUpperCase());
            Page<TrackingQuestionNeon> questions = trackingQuestionNeonService.getTrackingQuestionsByQuestionTypeWithPagination(type, pageable);
            return ResponseEntity.ok(questions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Lấy câu hỏi theo difficulty level
     */
    @GetMapping("/difficulty/{difficultyLevel}")
    public ResponseEntity<List<TrackingQuestionNeon>> getTrackingQuestionsByDifficultyLevel(@PathVariable String difficultyLevel) {
        log.info("Getting tracking questions by difficulty level (Neon): {}", difficultyLevel);
        try {
            TrackingQuestionNeon.DifficultyLevel level = TrackingQuestionNeon.DifficultyLevel.valueOf(difficultyLevel.toUpperCase());
            List<TrackingQuestionNeon> questions = trackingQuestionNeonService.getTrackingQuestionsByDifficultyLevel(level);
            return ResponseEntity.ok(questions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Lấy câu hỏi theo difficulty level với phân trang
     */
    @GetMapping("/difficulty/{difficultyLevel}/paginated")
    public ResponseEntity<Page<TrackingQuestionNeon>> getTrackingQuestionsByDifficultyLevelWithPagination(
            @PathVariable String difficultyLevel, Pageable pageable) {
        log.info("Getting tracking questions by difficulty level with pagination (Neon): {}", difficultyLevel);
        try {
            TrackingQuestionNeon.DifficultyLevel level = TrackingQuestionNeon.DifficultyLevel.valueOf(difficultyLevel.toUpperCase());
            Page<TrackingQuestionNeon> questions = trackingQuestionNeonService.getTrackingQuestionsByDifficultyLevelWithPagination(level, pageable);
            return ResponseEntity.ok(questions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Lấy câu hỏi theo status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TrackingQuestionNeon>> getTrackingQuestionsByStatus(@PathVariable String status) {
        log.info("Getting tracking questions by status (Neon): {}", status);
        try {
            TrackingQuestionNeon.Status statusEnum = TrackingQuestionNeon.Status.valueOf(status.toUpperCase());
            List<TrackingQuestionNeon> questions = trackingQuestionNeonService.getTrackingQuestionsByStatus(statusEnum);
            return ResponseEntity.ok(questions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Lấy câu hỏi theo status với phân trang
     */
    @GetMapping("/status/{status}/paginated")
    public ResponseEntity<Page<TrackingQuestionNeon>> getTrackingQuestionsByStatusWithPagination(
            @PathVariable String status, Pageable pageable) {
        log.info("Getting tracking questions by status with pagination (Neon): {}", status);
        try {
            TrackingQuestionNeon.Status statusEnum = TrackingQuestionNeon.Status.valueOf(status.toUpperCase());
            Page<TrackingQuestionNeon> questions = trackingQuestionNeonService.getTrackingQuestionsByStatusWithPagination(statusEnum, pageable);
            return ResponseEntity.ok(questions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Lấy câu hỏi theo độ tuổi
     */
    @GetMapping("/age/{age}")
    public ResponseEntity<List<TrackingQuestionNeon>> getTrackingQuestionsByAge(@PathVariable Integer age) {
        log.info("Getting tracking questions by age (Neon): {}", age);
        List<TrackingQuestionNeon> questions = trackingQuestionNeonService.getTrackingQuestionsByAgeRange(age);
        return ResponseEntity.ok(questions);
    }

    /**
     * Lấy câu hỏi theo độ tuổi với phân trang
     */
    @GetMapping("/age/{age}/paginated")
    public ResponseEntity<Page<TrackingQuestionNeon>> getTrackingQuestionsByAgeWithPagination(
            @PathVariable Integer age, Pageable pageable) {
        log.info("Getting tracking questions by age with pagination (Neon): {}", age);
        Page<TrackingQuestionNeon> questions = trackingQuestionNeonService.getTrackingQuestionsByAgeRangeWithPagination(age, pageable);
        return ResponseEntity.ok(questions);
    }
    
    /**
     * Lấy câu hỏi theo category và subcategory
     */
    @GetMapping("/category/{category}/subcategory/{subcategory}")
    public ResponseEntity<List<TrackingQuestionNeon>> getTrackingQuestionsByCategoryAndSubcategory(
            @PathVariable String category, @PathVariable String subcategory) {
        log.info("Getting tracking questions by category: {} and subcategory: {} (Neon)", category, subcategory);
        List<TrackingQuestionNeon> questions = trackingQuestionNeonService.getTrackingQuestionsByCategoryAndSubcategory(category, subcategory);
        return ResponseEntity.ok(questions);
    }
    
    /**
     * Lấy câu hỏi theo question type và difficulty level
     */
    @GetMapping("/question-type/{questionType}/difficulty/{difficultyLevel}")
    public ResponseEntity<List<TrackingQuestionNeon>> getTrackingQuestionsByQuestionTypeAndDifficultyLevel(
            @PathVariable String questionType, @PathVariable String difficultyLevel) {
        log.info("Getting tracking questions by question type: {} and difficulty level: {} (Neon)", questionType, difficultyLevel);
        try {
            TrackingQuestionNeon.QuestionType type = TrackingQuestionNeon.QuestionType.valueOf(questionType.toUpperCase());
            TrackingQuestionNeon.DifficultyLevel level = TrackingQuestionNeon.DifficultyLevel.valueOf(difficultyLevel.toUpperCase());
            List<TrackingQuestionNeon> questions = trackingQuestionNeonService.getTrackingQuestionsByQuestionTypeAndDifficultyLevel(type, level);
            return ResponseEntity.ok(questions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Lấy câu hỏi theo thời gian ước tính tối đa
     */
    @GetMapping("/max-time/{maxTime}")
    public ResponseEntity<List<TrackingQuestionNeon>> getTrackingQuestionsByMaxEstimatedTime(@PathVariable Integer maxTime) {
        log.info("Getting tracking questions by max estimated time (Neon): {}", maxTime);
        List<TrackingQuestionNeon> questions = trackingQuestionNeonService.getTrackingQuestionsByMaxEstimatedTime(maxTime);
        return ResponseEntity.ok(questions);
    }

    /**
     * Tìm kiếm câu hỏi theo từ khóa
     */
    @GetMapping("/search")
    public ResponseEntity<Page<TrackingQuestionNeon>> searchTrackingQuestions(@RequestParam String keyword, Pageable pageable) {
        log.info("Searching tracking questions with keyword (Neon): {}", keyword);
        Page<TrackingQuestionNeon> questions = trackingQuestionNeonService.searchTrackingQuestions(keyword, pageable);
        return ResponseEntity.ok(questions);
    }

    /**
     * Đếm số câu hỏi theo category
     */
    @GetMapping("/category/{category}/count")
    public ResponseEntity<Long> getTrackingQuestionCountByCategory(@PathVariable String category) {
        log.info("Getting tracking question count by category (Neon): {}", category);
        long count = trackingQuestionNeonService.getTrackingQuestionCountByCategory(category);
        return ResponseEntity.ok(count);
    }

    /**
     * Đếm số câu hỏi theo subcategory
     */
    @GetMapping("/subcategory/{subcategory}/count")
    public ResponseEntity<Long> getTrackingQuestionCountBySubcategory(@PathVariable String subcategory) {
        log.info("Getting tracking question count by subcategory (Neon): {}", subcategory);
        long count = trackingQuestionNeonService.getTrackingQuestionCountBySubcategory(subcategory);
        return ResponseEntity.ok(count);
    }

    /**
     * Đếm số câu hỏi theo question type
     */
    @GetMapping("/question-type/{questionType}/count")
    public ResponseEntity<Long> getTrackingQuestionCountByQuestionType(@PathVariable String questionType) {
        log.info("Getting tracking question count by question type (Neon): {}", questionType);
        try {
            TrackingQuestionNeon.QuestionType type = TrackingQuestionNeon.QuestionType.valueOf(questionType.toUpperCase());
            long count = trackingQuestionNeonService.getTrackingQuestionCountByQuestionType(type);
            return ResponseEntity.ok(count);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Đếm số câu hỏi theo difficulty level
     */
    @GetMapping("/difficulty/{difficultyLevel}/count")
    public ResponseEntity<Long> getTrackingQuestionCountByDifficultyLevel(@PathVariable String difficultyLevel) {
        log.info("Getting tracking question count by difficulty level (Neon): {}", difficultyLevel);
        try {
            TrackingQuestionNeon.DifficultyLevel level = TrackingQuestionNeon.DifficultyLevel.valueOf(difficultyLevel.toUpperCase());
            long count = trackingQuestionNeonService.getTrackingQuestionCountByDifficultyLevel(level);
            return ResponseEntity.ok(count);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Đếm số câu hỏi theo status
     */
    @GetMapping("/status/{status}/count")
    public ResponseEntity<Long> getTrackingQuestionCountByStatus(@PathVariable String status) {
        log.info("Getting tracking question count by status (Neon): {}", status);
        try {
            TrackingQuestionNeon.Status statusEnum = TrackingQuestionNeon.Status.valueOf(status.toUpperCase());
            long count = trackingQuestionNeonService.getTrackingQuestionCountByStatus(statusEnum);
            return ResponseEntity.ok(count);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Lấy thống kê theo category
     */
    @GetMapping("/stats/category")
    public ResponseEntity<List<Object[]>> getTrackingQuestionCountByCategoryGroup() {
        log.info("Getting tracking question count by category group (Neon)");
        List<Object[]> stats = trackingQuestionNeonService.getTrackingQuestionCountByCategoryGroup();
        return ResponseEntity.ok(stats);
    }

    /**
     * Lấy thống kê theo question type
     */
    @GetMapping("/stats/question-type")
    public ResponseEntity<List<Object[]>> getTrackingQuestionCountByQuestionTypeGroup() {
        log.info("Getting tracking question count by question type group (Neon)");
        List<Object[]> stats = trackingQuestionNeonService.getTrackingQuestionCountByQuestionTypeGroup();
        return ResponseEntity.ok(stats);
    }

    /**
     * Lấy thống kê theo difficulty level
     */
    @GetMapping("/stats/difficulty")
    public ResponseEntity<List<Object[]>> getTrackingQuestionCountByDifficultyLevelGroup() {
        log.info("Getting tracking question count by difficulty level group (Neon)");
        List<Object[]> stats = trackingQuestionNeonService.getTrackingQuestionCountByDifficultyLevelGroup();
        return ResponseEntity.ok(stats);
    }
}

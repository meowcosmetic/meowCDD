package com.meowcdd.service;

import com.meowcdd.entity.neon.TrackingQuestionNeon;
import com.meowcdd.repository.neon.TrackingQuestionNeonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TrackingQuestionNeonService {
    
    private final TrackingQuestionNeonRepository trackingQuestionNeonRepository;
    
    /**
     * Lấy tất cả câu hỏi tracking
     */
    public List<TrackingQuestionNeon> getAllTrackingQuestions() {
        log.info("Getting all tracking questions");
        return trackingQuestionNeonRepository.findAll();
    }

    /**
     * Lấy tất cả câu hỏi tracking với phân trang
     */
    public Page<TrackingQuestionNeon> getAllTrackingQuestionsWithPagination(Pageable pageable) {
        log.info("Getting all tracking questions with pagination");
        return trackingQuestionNeonRepository.findAll(pageable);
    }
    
    /**
     * Lấy câu hỏi tracking theo ID
     */
    public Optional<TrackingQuestionNeon> getTrackingQuestionById(Long id) {
        log.info("Getting tracking question by ID: {}", id);
        return trackingQuestionNeonRepository.findById(id);
    }
    
    /**
     * Lấy câu hỏi theo category
     */
    public List<TrackingQuestionNeon> getTrackingQuestionsByCategory(String category) {
        log.info("Getting tracking questions by category: {}", category);
        return trackingQuestionNeonRepository.findByCategory(category);
    }

    /**
     * Lấy câu hỏi theo category với phân trang
     */
    public Page<TrackingQuestionNeon> getTrackingQuestionsByCategoryWithPagination(String category, Pageable pageable) {
        log.info("Getting tracking questions by category with pagination: {}", category);
        return trackingQuestionNeonRepository.findByCategoryWithPagination(category, pageable);
    }
    
    /**
     * Lấy câu hỏi theo subcategory
     */
    public List<TrackingQuestionNeon> getTrackingQuestionsBySubcategory(String subcategory) {
        log.info("Getting tracking questions by subcategory: {}", subcategory);
        return trackingQuestionNeonRepository.findBySubcategory(subcategory);
    }

    /**
     * Lấy câu hỏi theo subcategory với phân trang
     */
    public Page<TrackingQuestionNeon> getTrackingQuestionsBySubcategoryWithPagination(String subcategory, Pageable pageable) {
        log.info("Getting tracking questions by subcategory with pagination: {}", subcategory);
        return trackingQuestionNeonRepository.findBySubcategoryWithPagination(subcategory, pageable);
    }
    
    /**
     * Lấy câu hỏi theo question type
     */
    public List<TrackingQuestionNeon> getTrackingQuestionsByQuestionType(TrackingQuestionNeon.QuestionType questionType) {
        log.info("Getting tracking questions by question type: {}", questionType);
        return trackingQuestionNeonRepository.findByQuestionType(questionType);
    }

    /**
     * Lấy câu hỏi theo question type với phân trang
     */
    public Page<TrackingQuestionNeon> getTrackingQuestionsByQuestionTypeWithPagination(TrackingQuestionNeon.QuestionType questionType, Pageable pageable) {
        log.info("Getting tracking questions by question type with pagination: {}", questionType);
        return trackingQuestionNeonRepository.findByQuestionTypeWithPagination(questionType, pageable);
    }
    
    /**
     * Lấy câu hỏi theo difficulty level
     */
    public List<TrackingQuestionNeon> getTrackingQuestionsByDifficultyLevel(TrackingQuestionNeon.DifficultyLevel difficultyLevel) {
        log.info("Getting tracking questions by difficulty level: {}", difficultyLevel);
        return trackingQuestionNeonRepository.findByDifficultyLevel(difficultyLevel);
    }

    /**
     * Lấy câu hỏi theo difficulty level với phân trang
     */
    public Page<TrackingQuestionNeon> getTrackingQuestionsByDifficultyLevelWithPagination(TrackingQuestionNeon.DifficultyLevel difficultyLevel, Pageable pageable) {
        log.info("Getting tracking questions by difficulty level with pagination: {}", difficultyLevel);
        return trackingQuestionNeonRepository.findByDifficultyLevelWithPagination(difficultyLevel, pageable);
    }
    
    /**
     * Lấy câu hỏi theo status
     */
    public List<TrackingQuestionNeon> getTrackingQuestionsByStatus(TrackingQuestionNeon.Status status) {
        log.info("Getting tracking questions by status: {}", status);
        return trackingQuestionNeonRepository.findByStatus(status);
    }

    /**
     * Lấy câu hỏi theo status với phân trang
     */
    public Page<TrackingQuestionNeon> getTrackingQuestionsByStatusWithPagination(TrackingQuestionNeon.Status status, Pageable pageable) {
        log.info("Getting tracking questions by status with pagination: {}", status);
        return trackingQuestionNeonRepository.findByStatusWithPagination(status, pageable);
    }
    
    /**
     * Lấy câu hỏi theo độ tuổi
     */
    public List<TrackingQuestionNeon> getTrackingQuestionsByAgeRange(Integer age) {
        log.info("Getting tracking questions by age: {}", age);
        return trackingQuestionNeonRepository.findByAgeRange(age);
    }

    /**
     * Lấy câu hỏi theo độ tuổi với phân trang
     */
    public Page<TrackingQuestionNeon> getTrackingQuestionsByAgeRangeWithPagination(Integer age, Pageable pageable) {
        log.info("Getting tracking questions by age with pagination: {}", age);
        return trackingQuestionNeonRepository.findByAgeRangeWithPagination(age, pageable);
    }
    
    /**
     * Lấy câu hỏi theo category và subcategory
     */
    public List<TrackingQuestionNeon> getTrackingQuestionsByCategoryAndSubcategory(String category, String subcategory) {
        log.info("Getting tracking questions by category: {} and subcategory: {}", category, subcategory);
        return trackingQuestionNeonRepository.findByCategoryAndSubcategory(category, subcategory);
    }
    
    /**
     * Lấy câu hỏi theo question type và difficulty level
     */
    public List<TrackingQuestionNeon> getTrackingQuestionsByQuestionTypeAndDifficultyLevel(TrackingQuestionNeon.QuestionType questionType, TrackingQuestionNeon.DifficultyLevel difficultyLevel) {
        log.info("Getting tracking questions by question type: {} and difficulty level: {}", questionType, difficultyLevel);
        return trackingQuestionNeonRepository.findByQuestionTypeAndDifficultyLevel(questionType, difficultyLevel);
    }
    
    /**
     * Lấy câu hỏi theo thời gian ước tính tối đa
     */
    public List<TrackingQuestionNeon> getTrackingQuestionsByMaxEstimatedTime(Integer maxTime) {
        log.info("Getting tracking questions by max estimated time: {}", maxTime);
        return trackingQuestionNeonRepository.findByMaxEstimatedTime(maxTime);
    }

    /**
     * Tìm kiếm câu hỏi theo từ khóa
     */
    public Page<TrackingQuestionNeon> searchTrackingQuestions(String keyword, Pageable pageable) {
        log.info("Searching tracking questions with keyword: {}", keyword);
        return trackingQuestionNeonRepository.searchByKeyword(keyword, pageable);
    }

    /**
     * Tạo câu hỏi tracking mới
     */
    public TrackingQuestionNeon createTrackingQuestion(TrackingQuestionNeon trackingQuestion) {
        log.info("Creating new tracking question");
        return trackingQuestionNeonRepository.save(trackingQuestion);
    }
    
    /**
     * Cập nhật một câu hỏi tracking
     */
    public Optional<TrackingQuestionNeon> updateTrackingQuestion(Long id, TrackingQuestionNeon trackingQuestion) {
        log.info("Updating tracking question with ID: {}", id);
        return trackingQuestionNeonRepository.findById(id)
                .map(existingQuestion -> {
                    existingQuestion.setQuestionText(trackingQuestion.getQuestionText());
                    existingQuestion.setQuestionType(trackingQuestion.getQuestionType());
                    existingQuestion.setCategory(trackingQuestion.getCategory());
                    existingQuestion.setSubcategory(trackingQuestion.getSubcategory());
                    existingQuestion.setAgeRangeMin(trackingQuestion.getAgeRangeMin());
                    existingQuestion.setAgeRangeMax(trackingQuestion.getAgeRangeMax());
                    existingQuestion.setOptionsJson(trackingQuestion.getOptionsJson());
                    existingQuestion.setCorrectAnswer(trackingQuestion.getCorrectAnswer());
                    existingQuestion.setScoringRules(trackingQuestion.getScoringRules());
                    existingQuestion.setDifficultyLevel(trackingQuestion.getDifficultyLevel());
                    existingQuestion.setEstimatedTimeSeconds(trackingQuestion.getEstimatedTimeSeconds());
                    existingQuestion.setInstructions(trackingQuestion.getInstructions());
                    existingQuestion.setNotes(trackingQuestion.getNotes());
                    existingQuestion.setStatus(trackingQuestion.getStatus());
                    existingQuestion.setVersion(trackingQuestion.getVersion());
                    return trackingQuestionNeonRepository.save(existingQuestion);
                });
    }
    
    /**
     * Xóa câu hỏi tracking theo ID
     */
    public boolean deleteTrackingQuestion(Long id) {
        log.info("Deleting tracking question with ID: {}", id);
        if (trackingQuestionNeonRepository.existsById(id)) {
            trackingQuestionNeonRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Đếm số câu hỏi theo category
     */
    public long getTrackingQuestionCountByCategory(String category) {
        log.info("Getting tracking question count by category: {}", category);
        return trackingQuestionNeonRepository.countByCategory(category);
    }

    /**
     * Đếm số câu hỏi theo subcategory
     */
    public long getTrackingQuestionCountBySubcategory(String subcategory) {
        log.info("Getting tracking question count by subcategory: {}", subcategory);
        return trackingQuestionNeonRepository.countBySubcategory(subcategory);
    }

    /**
     * Đếm số câu hỏi theo question type
     */
    public long getTrackingQuestionCountByQuestionType(TrackingQuestionNeon.QuestionType questionType) {
        log.info("Getting tracking question count by question type: {}", questionType);
        return trackingQuestionNeonRepository.countByQuestionType(questionType);
    }

    /**
     * Đếm số câu hỏi theo difficulty level
     */
    public long getTrackingQuestionCountByDifficultyLevel(TrackingQuestionNeon.DifficultyLevel difficultyLevel) {
        log.info("Getting tracking question count by difficulty level: {}", difficultyLevel);
        return trackingQuestionNeonRepository.countByDifficultyLevel(difficultyLevel);
    }

    /**
     * Đếm số câu hỏi theo status
     */
    public long getTrackingQuestionCountByStatus(TrackingQuestionNeon.Status status) {
        log.info("Getting tracking question count by status: {}", status);
        return trackingQuestionNeonRepository.countByStatus(status);
    }

    /**
     * Lấy thống kê theo category
     */
    public List<Object[]> getTrackingQuestionCountByCategoryGroup() {
        log.info("Getting tracking question count by category group");
        return trackingQuestionNeonRepository.countByCategoryGroup();
    }

    /**
     * Lấy thống kê theo question type
     */
    public List<Object[]> getTrackingQuestionCountByQuestionTypeGroup() {
        log.info("Getting tracking question count by question type group");
        return trackingQuestionNeonRepository.countByQuestionTypeGroup();
    }

    /**
     * Lấy thống kê theo difficulty level
     */
    public List<Object[]> getTrackingQuestionCountByDifficultyLevelGroup() {
        log.info("Getting tracking question count by difficulty level group");
        return trackingQuestionNeonRepository.countByDifficultyLevelGroup();
    }
}

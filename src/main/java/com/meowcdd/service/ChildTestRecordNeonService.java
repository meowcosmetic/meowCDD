package com.meowcdd.service;

import com.meowcdd.dto.ChildTestRecordNeonDto;
import com.meowcdd.entity.neon.ChildTestRecordNeon;
import com.meowcdd.repository.neon.ChildTestRecordNeonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChildTestRecordNeonService {

    private final ChildTestRecordNeonRepository childTestRecordNeonRepository;

    public List<ChildTestRecordNeon> getAllChildTestRecords() {
        log.info("Getting all child test records");
        return childTestRecordNeonRepository.findAll();
    }

    public Page<ChildTestRecordNeon> getAllChildTestRecordsWithPagination(Pageable pageable) {
        log.info("Getting all child test records with pagination");
        return childTestRecordNeonRepository.findAll(pageable);
    }

    public Optional<ChildTestRecordNeon> getChildTestRecordById(Long id) {
        log.info("Getting child test record by ID: {}", id);
        return childTestRecordNeonRepository.findById(id);
    }

    public List<ChildTestRecordNeon> getChildTestRecordsByChildId(String childId) {
        log.info("Getting child test records by child ID: {}", childId);
        return childTestRecordNeonRepository.findByChildId(childId);
    }

    public List<ChildTestRecordNeon> getChildTestRecordsByTestId(String testId) {
        log.info("Getting child test records by test ID: {}", testId);
        return childTestRecordNeonRepository.findByTestId(testId);
    }

    public List<ChildTestRecordNeon> getChildTestRecordsByStatus(ChildTestRecordNeon.Status status) {
        log.info("Getting child test records by status: {}", status);
        return childTestRecordNeonRepository.findByStatus(status);
    }

    public List<ChildTestRecordNeon> getChildTestRecordsByAssessor(String assessor) {
        log.info("Getting child test records by assessor: {}", assessor);
        return childTestRecordNeonRepository.findByAssessor(assessor);
    }

    public List<ChildTestRecordNeon> getChildTestRecordsByChildIdAndTestId(String childId, String testId) {
        log.info("Getting child test records by child ID: {} and test ID: {}", childId, testId);
        return childTestRecordNeonRepository.findByChildIdAndTestId(childId, testId);
    }

    public List<ChildTestRecordNeon> getChildTestRecordsByTestDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Getting child test records by test date between: {} and {}", startDate, endDate);
        return childTestRecordNeonRepository.findByTestDateBetween(startDate, endDate);
    }

    public List<ChildTestRecordNeon> getChildTestRecordsByChildIdAndTestDateBetween(String childId, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Getting child test records by child ID: {} and test date between: {} and {}", childId, startDate, endDate);
        return childTestRecordNeonRepository.findByChildIdAndTestDateBetween(childId, startDate, endDate);
    }

    public List<ChildTestRecordNeon> getChildTestRecordsByScoreBetween(Double minScore, Double maxScore) {
        log.info("Getting child test records by score between: {} and {}", minScore, maxScore);
        return childTestRecordNeonRepository.findByScoreBetween(minScore, maxScore);
    }

    public Page<ChildTestRecordNeon> getChildTestRecordsByChildIdWithPagination(String childId, Pageable pageable) {
        log.info("Getting child test records by child ID: {} with pagination", childId);
        return childTestRecordNeonRepository.findByChildIdWithPagination(childId, pageable);
    }

    public Page<ChildTestRecordNeon> getChildTestRecordsByTestIdWithPagination(String testId, Pageable pageable) {
        log.info("Getting child test records by test ID: {} with pagination", testId);
        return childTestRecordNeonRepository.findByTestIdWithPagination(testId, pageable);
    }

    public Page<ChildTestRecordNeon> getChildTestRecordsByStatusWithPagination(ChildTestRecordNeon.Status status, Pageable pageable) {
        log.info("Getting child test records by status: {} with pagination", status);
        return childTestRecordNeonRepository.findByStatusWithPagination(status, pageable);
    }

    public Page<ChildTestRecordNeon> getChildTestRecordsByAssessorWithPagination(String assessor, Pageable pageable) {
        log.info("Getting child test records by assessor: {} with pagination", assessor);
        return childTestRecordNeonRepository.findByAssessorWithPagination(assessor, pageable);
    }

    public Page<ChildTestRecordNeon> getChildTestRecordsByTestDateBetweenWithPagination(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        log.info("Getting child test records by test date between: {} and {} with pagination", startDate, endDate);
        return childTestRecordNeonRepository.findByTestDateBetweenWithPagination(startDate, endDate, pageable);
    }

    public Page<ChildTestRecordNeon> getChildTestRecordsByScoreBetweenWithPagination(Double minScore, Double maxScore, Pageable pageable) {
        log.info("Getting child test records by score between: {} and {} with pagination", minScore, maxScore);
        return childTestRecordNeonRepository.findByScoreBetweenWithPagination(minScore, maxScore, pageable);
    }

    public ChildTestRecordNeon createChildTestRecord(ChildTestRecordNeon childTestRecord) {
        log.info("Creating new child test record for child ID: {}", childTestRecord.getChildId());
        return childTestRecordNeonRepository.save(childTestRecord);
    }

    public Optional<ChildTestRecordNeon> updateChildTestRecord(Long id, ChildTestRecordNeon childTestRecord) {
        log.info("Updating child test record with ID: {}", id);
        return childTestRecordNeonRepository.findById(id)
                .map(existingRecord -> {
                    existingRecord.setChildId(childTestRecord.getChildId());
                    existingRecord.setTestId(childTestRecord.getTestId());
                    existingRecord.setTestType(childTestRecord.getTestType());
                    existingRecord.setTestDate(childTestRecord.getTestDate());
                    existingRecord.setStartTime(childTestRecord.getStartTime());
                    existingRecord.setEndTime(childTestRecord.getEndTime());
                    existingRecord.setStatus(childTestRecord.getStatus());
                    existingRecord.setTotalScore(childTestRecord.getTotalScore());
                    existingRecord.setMaxScore(childTestRecord.getMaxScore());
                    existingRecord.setPercentageScore(childTestRecord.getPercentageScore());
                    existingRecord.setResultLevel(childTestRecord.getResultLevel());
                    existingRecord.setInterpretation(childTestRecord.getInterpretation());
                    existingRecord.setQuestionAnswers(childTestRecord.getQuestionAnswers());
                    existingRecord.setCorrectAnswers(childTestRecord.getCorrectAnswers());
                    existingRecord.setTotalQuestions(childTestRecord.getTotalQuestions());
                    existingRecord.setSkippedQuestions(childTestRecord.getSkippedQuestions());
                    existingRecord.setNotes(childTestRecord.getNotes());
                    existingRecord.setEnvironment(childTestRecord.getEnvironment());
                    existingRecord.setAssessor(childTestRecord.getAssessor());
                    existingRecord.setParentPresent(childTestRecord.getParentPresent());
                    return childTestRecordNeonRepository.save(existingRecord);
                });
    }

    public boolean deleteChildTestRecord(Long id) {
        log.info("Deleting child test record with ID: {}", id);
        if (childTestRecordNeonRepository.existsById(id)) {
            childTestRecordNeonRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public long getChildTestRecordCountByChildId(String childId) {
        log.info("Getting child test record count by child ID: {}", childId);
        return childTestRecordNeonRepository.countByChildId(childId);
    }

    public long getChildTestRecordCountByTestId(String testId) {
        log.info("Getting child test record count by test ID: {}", testId);
        return childTestRecordNeonRepository.countByTestId(testId);
    }

    public long getChildTestRecordCountByStatus(ChildTestRecordNeon.Status status) {
        log.info("Getting child test record count by status: {}", status);
        return childTestRecordNeonRepository.countByStatus(status);
    }

    public long getChildTestRecordCountByAssessor(String assessor) {
        log.info("Getting child test record count by assessor: {}", assessor);
        return childTestRecordNeonRepository.countByAssessor(assessor);
    }

    public Double getAverageScoreByChildId(String childId) {
        log.info("Getting average score by child ID: {}", childId);
        return childTestRecordNeonRepository.getAverageScoreByChildId(childId);
    }

    public Double getAverageScoreByTestId(String testId) {
        log.info("Getting average score by test ID: {}", testId);
        return childTestRecordNeonRepository.getAverageScoreByTestId(testId);
    }
}

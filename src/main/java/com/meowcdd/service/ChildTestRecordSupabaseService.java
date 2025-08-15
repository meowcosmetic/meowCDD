package com.meowcdd.service;

import com.meowcdd.dto.ChildTestRecordSupabaseDto;
import com.meowcdd.entity.supabase.ChildTestRecordSupabase;
import com.meowcdd.repository.supabase.ChildTestRecordSupabaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChildTestRecordSupabaseService {

    private final ChildTestRecordSupabaseRepository childTestRecordSupabaseRepository;

    public ChildTestRecordSupabaseDto createChildTestRecord(ChildTestRecordSupabaseDto childTestRecordDto) {
        log.info("Creating child test record with external ID: {}", childTestRecordDto.getExternalId());

        if (childTestRecordSupabaseRepository.existsByExternalId(childTestRecordDto.getExternalId())) {
            throw new RuntimeException("Child test record with external ID " + childTestRecordDto.getExternalId() + " already exists");
        }

        ChildTestRecordSupabase childTestRecord = ChildTestRecordSupabase.builder()
                .externalId(childTestRecordDto.getExternalId())
                .childId(childTestRecordDto.getChildId())
                .testId(childTestRecordDto.getTestId())
                .testType(childTestRecordDto.getTestType() != null ?
                        ChildTestRecordSupabase.TestType.valueOf(childTestRecordDto.getTestType()) : null)
                .testDate(childTestRecordDto.getTestDate())
                .startTime(childTestRecordDto.getStartTime())
                .endTime(childTestRecordDto.getEndTime())
                .status(childTestRecordDto.getStatus() != null ?
                        ChildTestRecordSupabase.Status.valueOf(childTestRecordDto.getStatus()) : null)
                .totalScore(childTestRecordDto.getTotalScore())
                .maxScore(childTestRecordDto.getMaxScore())
                .interpretation(childTestRecordDto.getInterpretation())
                .questionAnswers(childTestRecordDto.getQuestionAnswers())
                .correctAnswers(childTestRecordDto.getCorrectAnswers())
                .totalQuestions(childTestRecordDto.getTotalQuestions())
                .skippedQuestions(childTestRecordDto.getSkippedQuestions())
                .notes(childTestRecordDto.getNotes())
                .environment(childTestRecordDto.getEnvironment())
                .assessor(childTestRecordDto.getAssessor())
                .parentPresent(childTestRecordDto.getParentPresent())
                .build();

        ChildTestRecordSupabase savedRecord = childTestRecordSupabaseRepository.save(childTestRecord);
        return convertToDto(savedRecord);
    }

    public ChildTestRecordSupabaseDto getChildTestRecordById(Long id) {
        log.info("Getting child test record by ID: {}", id);
        ChildTestRecordSupabase childTestRecord = childTestRecordSupabaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Child test record not found with ID: " + id));
        return convertToDto(childTestRecord);
    }

    public ChildTestRecordSupabaseDto getChildTestRecordByExternalId(String externalId) {
        log.info("Getting child test record by external ID: {}", externalId);
        ChildTestRecordSupabase childTestRecord = childTestRecordSupabaseRepository.findByExternalId(externalId)
                .orElseThrow(() -> new RuntimeException("Child test record not found with external ID: " + externalId));
        return convertToDto(childTestRecord);
    }

    public List<ChildTestRecordSupabaseDto> getAllChildTestRecords() {
        log.info("Getting all child test records");
        return childTestRecordSupabaseRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ChildTestRecordSupabaseDto> getChildTestRecordsByChildId(Long childId) {
        log.info("Getting child test records by child ID: {}", childId);
        return childTestRecordSupabaseRepository.findByChildIdOrderByTestDateDesc(childId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ChildTestRecordSupabaseDto> getChildTestRecordsByTestId(Long testId) {
        log.info("Getting child test records by test ID: {}", testId);
        return childTestRecordSupabaseRepository.findByTestId(testId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ChildTestRecordSupabaseDto> getChildTestRecordsByTestType(String testType) {
        log.info("Getting child test records by test type: {}", testType);
        return childTestRecordSupabaseRepository.findByTestType(ChildTestRecordSupabase.TestType.valueOf(testType)).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ChildTestRecordSupabaseDto> getChildTestRecordsByStatus(String status) {
        log.info("Getting child test records by status: {}", status);
        return childTestRecordSupabaseRepository.findByStatus(ChildTestRecordSupabase.Status.valueOf(status)).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ChildTestRecordSupabaseDto> getChildTestRecordsByChildIdAndTestType(Long childId, String testType) {
        log.info("Getting child test records by child ID: {} and test type: {}", childId, testType);
        return childTestRecordSupabaseRepository.findByChildIdAndTestType(childId, ChildTestRecordSupabase.TestType.valueOf(testType)).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ChildTestRecordSupabaseDto> getChildTestRecordsByChildIdAndStatus(Long childId, String status) {
        log.info("Getting child test records by child ID: {} and status: {}", childId, status);
        return childTestRecordSupabaseRepository.findByChildIdAndStatus(childId, ChildTestRecordSupabase.Status.valueOf(status)).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ChildTestRecordSupabaseDto> getChildTestRecordsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Getting child test records by date range: {} to {}", startDate, endDate);
        return childTestRecordSupabaseRepository.findByTestDateBetween(startDate, endDate).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ChildTestRecordSupabaseDto> getChildTestRecordsByResultLevel(String resultLevel) {
        log.info("Getting child test records by result level: {}", resultLevel);
        return childTestRecordSupabaseRepository.findByResultLevel(ChildTestRecordSupabase.ResultLevel.valueOf(resultLevel)).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ChildTestRecordSupabaseDto> getChildTestRecordsByPercentageScoreRange(Double minScore, Double maxScore) {
        log.info("Getting child test records by percentage score range: {} to {}", minScore, maxScore);
        return childTestRecordSupabaseRepository.findByPercentageScoreBetween(minScore, maxScore).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ChildTestRecordSupabaseDto updateChildTestRecord(Long id, ChildTestRecordSupabaseDto childTestRecordDto) {
        log.info("Updating child test record with ID: {}", id);
        ChildTestRecordSupabase existingRecord = childTestRecordSupabaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Child test record not found with ID: " + id));

        existingRecord.setExternalId(childTestRecordDto.getExternalId());
        existingRecord.setChildId(childTestRecordDto.getChildId());
        existingRecord.setTestId(childTestRecordDto.getTestId());
        if (childTestRecordDto.getTestType() != null) {
            existingRecord.setTestType(ChildTestRecordSupabase.TestType.valueOf(childTestRecordDto.getTestType()));
        }
        existingRecord.setTestDate(childTestRecordDto.getTestDate());
        existingRecord.setStartTime(childTestRecordDto.getStartTime());
        existingRecord.setEndTime(childTestRecordDto.getEndTime());
        if (childTestRecordDto.getStatus() != null) {
            existingRecord.setStatus(ChildTestRecordSupabase.Status.valueOf(childTestRecordDto.getStatus()));
        }
        existingRecord.setTotalScore(childTestRecordDto.getTotalScore());
        existingRecord.setMaxScore(childTestRecordDto.getMaxScore());
        existingRecord.setInterpretation(childTestRecordDto.getInterpretation());
        existingRecord.setQuestionAnswers(childTestRecordDto.getQuestionAnswers());
        existingRecord.setCorrectAnswers(childTestRecordDto.getCorrectAnswers());
        existingRecord.setTotalQuestions(childTestRecordDto.getTotalQuestions());
        existingRecord.setSkippedQuestions(childTestRecordDto.getSkippedQuestions());
        existingRecord.setNotes(childTestRecordDto.getNotes());
        existingRecord.setEnvironment(childTestRecordDto.getEnvironment());
        existingRecord.setAssessor(childTestRecordDto.getAssessor());
        existingRecord.setParentPresent(childTestRecordDto.getParentPresent());

        ChildTestRecordSupabase updatedRecord = childTestRecordSupabaseRepository.save(existingRecord);
        return convertToDto(updatedRecord);
    }

    public void deleteChildTestRecord(Long id) {
        log.info("Deleting child test record with ID: {}", id);
        if (!childTestRecordSupabaseRepository.existsById(id)) {
            throw new RuntimeException("Child test record not found with ID: " + id);
        }
        childTestRecordSupabaseRepository.deleteById(id);
    }

    public boolean existsByExternalId(String externalId) {
        return childTestRecordSupabaseRepository.existsByExternalId(externalId);
    }

    public boolean existsByChildIdAndTestId(Long childId, Long testId) {
        return childTestRecordSupabaseRepository.existsByChildIdAndTestId(childId, testId);
    }

    public long countCompletedTestsByChildId(Long childId) {
        return childTestRecordSupabaseRepository.countCompletedTestsByChildId(childId);
    }

    public Double getAverageScoreByChildId(Long childId) {
        return childTestRecordSupabaseRepository.getAverageScoreByChildId(childId);
    }

    public LocalDateTime getLastTestDateByChildId(Long childId) {
        return childTestRecordSupabaseRepository.getLastTestDateByChildId(childId);
    }

    private ChildTestRecordSupabaseDto convertToDto(ChildTestRecordSupabase childTestRecord) {
        return ChildTestRecordSupabaseDto.builder()
                .id(childTestRecord.getId())
                .externalId(childTestRecord.getExternalId())
                .childId(childTestRecord.getChildId())
                .testId(childTestRecord.getTestId())
                .testType(childTestRecord.getTestType() != null ? childTestRecord.getTestType().name() : null)
                .testDate(childTestRecord.getTestDate())
                .startTime(childTestRecord.getStartTime())
                .endTime(childTestRecord.getEndTime())
                .status(childTestRecord.getStatus() != null ? childTestRecord.getStatus().name() : null)
                .totalScore(childTestRecord.getTotalScore())
                .maxScore(childTestRecord.getMaxScore())
                .percentageScore(childTestRecord.getPercentageScore())
                .resultLevel(childTestRecord.getResultLevel() != null ? childTestRecord.getResultLevel().name() : null)
                .interpretation(childTestRecord.getInterpretation())
                .questionAnswers(childTestRecord.getQuestionAnswers())
                .correctAnswers(childTestRecord.getCorrectAnswers())
                .totalQuestions(childTestRecord.getTotalQuestions())
                .skippedQuestions(childTestRecord.getSkippedQuestions())
                .notes(childTestRecord.getNotes())
                .environment(childTestRecord.getEnvironment())
                .assessor(childTestRecord.getAssessor())
                .parentPresent(childTestRecord.getParentPresent())
                .createdAt(childTestRecord.getCreatedAt())
                .updatedAt(childTestRecord.getUpdatedAt())
                .build();
    }
}

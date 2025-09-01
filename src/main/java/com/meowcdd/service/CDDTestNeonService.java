package com.meowcdd.service;

import com.meowcdd.entity.neon.CDDTestNeon;
import com.meowcdd.repository.neon.CDDTestNeonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CDDTestNeonService {

    private final CDDTestNeonRepository cddTestNeonRepository;

    public List<CDDTestNeon> getAllCDDTests() {
        log.info("Getting all CDD tests");
        return cddTestNeonRepository.findAll();
    }

    public Page<CDDTestNeon> getAllCDDTestsWithPagination(Pageable pageable) {
        log.info("Getting all CDD tests with pagination");
        Page<CDDTestNeon> result = cddTestNeonRepository.findAll(pageable);
        
        // Force load full data for each entity to ensure TEXT fields are loaded
        List<CDDTestNeon> loadedTests = new ArrayList<>();
        for (CDDTestNeon test : result.getContent()) {
            // Force reload the entity to ensure all fields are loaded
            Optional<CDDTestNeon> fullTest = cddTestNeonRepository.findById(test.getId());
            if (fullTest.isPresent()) {
                loadedTests.add(fullTest.get());
            }
        }
        
        // Create a new Page with the fully loaded entities
        Page<CDDTestNeon> fullyLoadedResult = new PageImpl<>(loadedTests, pageable, result.getTotalElements());
        
        // Log detailed information about each entity
        log.info("=== SERVICE LAYER DEBUG ===");
        log.info("Total elements: {}", fullyLoadedResult.getTotalElements());
        log.info("Total pages: {}", fullyLoadedResult.getTotalPages());
        log.info("Current page: {}", fullyLoadedResult.getNumber());
        log.info("Page size: {}", fullyLoadedResult.getSize());
        
        for (CDDTestNeon test : fullyLoadedResult.getContent()) {
            log.info("--- Service Test ID: {} ---", test.getId());
            log.info("Assessment Code: {}", test.getAssessmentCode());
            log.info("Questions JSON length: {}", test.getQuestionsJson() != null ? test.getQuestionsJson().length() : 0);
            log.info("Questions JSON preview: {}", test.getQuestionsJson() != null ? test.getQuestionsJson().substring(0, Math.min(200, test.getQuestionsJson().length())) + "..." : "null");
            log.info("--- End Service Test ID: {} ---", test.getId());
        }
        log.info("=== END SERVICE LAYER DEBUG ===");
        
        return fullyLoadedResult;
    }

    public Optional<CDDTestNeon> getCDDTestById(Long id) {
        log.info("Getting CDD test by ID: {}", id);
        return cddTestNeonRepository.findById(id);
    }

    public Optional<CDDTestNeon> getCDDTestByAssessmentCode(String assessmentCode) {
        log.info("Getting CDD test by assessment code: {}", assessmentCode);
        return cddTestNeonRepository.findByAssessmentCode(assessmentCode);
    }

    public List<CDDTestNeon> getCDDTestsByStatus(CDDTestNeon.Status status) {
        log.info("Getting CDD tests by status: {}", status);
        return cddTestNeonRepository.findByStatus(status);
    }

    public List<CDDTestNeon> getCDDTestsByCategory(String category) {
        log.info("Getting CDD tests by category: {}", category);
        return cddTestNeonRepository.findByCategory(category);
    }

    public List<CDDTestNeon> getCDDTestsForAge(Integer ageMonths) {
        log.info("Getting CDD tests for age: {} months", ageMonths);
        return cddTestNeonRepository.findTestsForAge(ageMonths);
    }

    public Page<CDDTestNeon> getCDDTestsForAgeAndStatusWithPagination(Integer ageMonths, CDDTestNeon.Status status, Pageable pageable) {
        log.info("Getting CDD tests for age: {} months, status: {} with pagination", ageMonths, status);
        return cddTestNeonRepository.findTestsForAgeAndStatusWithPagination(ageMonths, status, pageable);
    }

    public Page<CDDTestNeon> getCDDTestsForAgeCategoryAndStatusWithPagination(Integer ageMonths, String category, CDDTestNeon.Status status, Pageable pageable) {
        log.info("Getting CDD tests for age: {} months, category: {}, status: {} with pagination", ageMonths, category, status);
        return cddTestNeonRepository.findTestsForAgeCategoryAndStatusWithPagination(ageMonths, category, status, pageable);
    }

    public List<CDDTestNeon> getCDDTestsByStatusAndCategory(CDDTestNeon.Status status, String category) {
        log.info("Getting CDD tests by status: {} and category: {}", status, category);
        return cddTestNeonRepository.findByStatusAndCategory(status, category);
    }

    public List<CDDTestNeon> getCDDTestsByAdministrationType(CDDTestNeon.AdministrationType adminType) {
        log.info("Getting CDD tests by administration type: {}", adminType);
        return cddTestNeonRepository.findByAdministrationType(adminType);
    }

    public List<CDDTestNeon> getCDDTestsByRequiredQualifications(CDDTestNeon.RequiredQualifications qualification) {
        log.info("Getting CDD tests by required qualifications: {}", qualification);
        return cddTestNeonRepository.findByRequiredQualifications(qualification);
    }

    public List<CDDTestNeon> getCDDTestsByMaxDuration(Integer maxDuration) {
        log.info("Getting CDD tests by max duration: {}", maxDuration);
        return cddTestNeonRepository.findByMaxDuration(maxDuration);
    }

    public Page<CDDTestNeon> getCDDTestsByStatusWithPagination(CDDTestNeon.Status status, Pageable pageable) {
        log.info("Getting CDD tests by status: {} with pagination", status);
        return cddTestNeonRepository.findByStatusWithPagination(status, pageable);
    }

    public Page<CDDTestNeon> getCDDTestsByCategoryWithPagination(String category, Pageable pageable) {
        log.info("Getting CDD tests by category: {} with pagination", category);
        return cddTestNeonRepository.findByCategoryWithPagination(category, pageable);
    }

    public Page<CDDTestNeon> getCDDTestsByCategoryAndStatusWithPagination(String category, CDDTestNeon.Status status, Pageable pageable) {
        log.info("Getting CDD tests by category: {} and status: {} with pagination", category, status);
        return cddTestNeonRepository.findByCategoryAndStatusWithPagination(category, status, pageable);
    }

    public Page<CDDTestNeon> searchCDDTests(String keyword, Pageable pageable) {
        log.info("Searching CDD tests with keyword: {}", keyword);
        return cddTestNeonRepository.searchByKeyword(keyword, pageable);
    }

    public CDDTestNeon createCDDTest(CDDTestNeon cddTest) {
        log.info("Creating new CDD test: {}", cddTest.getAssessmentCode());
        if (cddTestNeonRepository.existsByAssessmentCode(cddTest.getAssessmentCode())) {
            throw new IllegalArgumentException("CDD test with assessment code " + cddTest.getAssessmentCode() + " already exists");
        }
        return cddTestNeonRepository.save(cddTest);
    }

    public Optional<CDDTestNeon> updateCDDTest(Long id, CDDTestNeon cddTest) {
        log.info("Updating CDD test with ID: {}", id);
        return cddTestNeonRepository.findById(id)
                .map(existingTest -> {
                    existingTest.setAssessmentCode(cddTest.getAssessmentCode());
                    existingTest.setCategory(cddTest.getCategory());
                    existingTest.setVersion(cddTest.getVersion());
                    existingTest.setStatus(cddTest.getStatus());
                    existingTest.setAdministrationType(cddTest.getAdministrationType());
                    existingTest.setRequiredQualifications(cddTest.getRequiredQualifications());
                    existingTest.setMinAgeMonths(cddTest.getMinAgeMonths());
                    existingTest.setMaxAgeMonths(cddTest.getMaxAgeMonths());
                    existingTest.setEstimatedDuration(cddTest.getEstimatedDuration());
                    existingTest.setNamesJson(cddTest.getNamesJson());
                    existingTest.setDescriptionsJson(cddTest.getDescriptionsJson());
                    existingTest.setInstructionsJson(cddTest.getInstructionsJson());
                    existingTest.setQuestionsJson(cddTest.getQuestionsJson());
                    existingTest.setScoringCriteriaJson(cddTest.getScoringCriteriaJson());
                    existingTest.setRequiredMaterialsJson(cddTest.getRequiredMaterialsJson());
                    existingTest.setNotesJson(cddTest.getNotesJson());
                    return cddTestNeonRepository.save(existingTest);
                });
    }

    public boolean deleteCDDTest(Long id) {
        log.info("Deleting CDD test with ID: {}", id);
        if (cddTestNeonRepository.existsById(id)) {
            cddTestNeonRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public long getCDDTestCountByStatus(CDDTestNeon.Status status) {
        log.info("Getting CDD test count by status: {}", status);
        return cddTestNeonRepository.countByStatus(status);
    }

    public List<Object[]> getCDDTestCountByCategory() {
        log.info("Getting CDD test count by category");
        return cddTestNeonRepository.countByCategory();
    }
    
    // Test method to get raw data from database
    public Object[] getRawTestData(Long id) {
        log.info("Getting raw test data for ID: {}", id);
        Object[] rawData = cddTestNeonRepository.getRawTestData(id);
        if (rawData != null) {
            log.info("Raw data - ID: {}, Assessment Code: {}, Questions JSON length: {}", 
                    rawData[0], rawData[1], 
                    rawData[2] != null ? ((String) rawData[2]).length() : 0);
            log.info("Raw Questions JSON: {}", rawData[2]);
        }
        return rawData;
    }
}

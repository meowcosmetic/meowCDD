package com.meowcdd.repository.mongo;

import com.meowcdd.document.AssessmentTest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssessmentTestRepository extends MongoRepository<AssessmentTest, String> {

    // Find by test code
    Optional<AssessmentTest> findByTestCode(String testCode);
    
    // Find by category
    List<AssessmentTest> findByCategory(String category);
    
    // Find by target age group
    List<AssessmentTest> findByTargetAgeGroup(String targetAgeGroup);
    
    // Find by difficulty level
    List<AssessmentTest> findByDifficultyLevel(String difficultyLevel);
    
    // Find by administration type
    List<AssessmentTest> findByAdministrationType(String administrationType);
    
    // Find by status
    List<AssessmentTest> findByStatus(String status);
    
    // Find by name in specific language
    @Query("{'names.?0': ?1}")
    Optional<AssessmentTest> findByNameInLanguage(String languageCode, String name);
    
    // Find by name containing text in any language
    @Query("{'$or': [{'names': {$regex: ?0, $options: 'i'}}]}")
    List<AssessmentTest> findByNameContainingIgnoreCase(String name);
    
    // Find by description containing text in any language
    @Query("{'$or': [{'descriptions': {$regex: ?0, $options: 'i'}}]}")
    List<AssessmentTest> findByDescriptionContainingIgnoreCase(String description);
    
    // Find tests that have content in specific language
    @Query("{'$or': [{'names.?0': {$exists: true}}, {'descriptions.?0': {$exists: true}}, {'instructions.?0': {$exists: true}}]}")
    List<AssessmentTest> findByLanguageAvailable(String languageCode);
    
    // Find tests by category and age group
    List<AssessmentTest> findByCategoryAndTargetAgeGroup(String category, String targetAgeGroup);
    
    // Find tests by category and difficulty level
    List<AssessmentTest> findByCategoryAndDifficultyLevel(String category, String difficultyLevel);
    
    // Find tests by administration type and status
    List<AssessmentTest> findByAdministrationTypeAndStatus(String administrationType, String status);
    
    // Find tests with estimated duration less than or equal to specified minutes
    List<AssessmentTest> findByEstimatedDurationLessThanEqual(Integer maxDuration);
    
    // Find tests with estimated duration between specified minutes
    List<AssessmentTest> findByEstimatedDurationBetween(Integer minDuration, Integer maxDuration);
    
    // Find tests by version
    List<AssessmentTest> findByVersion(String version);
    
    // Find tests by last updated by
    List<AssessmentTest> findByLastUpdatedBy(String lastUpdatedBy);
    
    // Find active tests
    List<AssessmentTest> findByStatusOrderByTestCodeAsc(String status);
    
    // Find tests by multiple categories
    @Query("{'category': {$in: ?0}}")
    List<AssessmentTest> findByCategories(List<String> categories);
    
    // Find tests by multiple age groups
    @Query("{'targetAgeGroup': {$in: ?0}}")
    List<AssessmentTest> findByAgeGroups(List<String> ageGroups);
    
    // Find tests that match multiple criteria
    @Query("{'category': ?0, 'targetAgeGroup': ?1, 'difficultyLevel': ?2, 'status': ?3}")
    List<AssessmentTest> findByCategoryAndAgeGroupAndDifficultyAndStatus(
            String category, String targetAgeGroup, String difficultyLevel, String status);
}


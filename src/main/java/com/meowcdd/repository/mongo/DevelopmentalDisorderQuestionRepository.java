package com.meowcdd.repository.mongo;

import com.meowcdd.document.DevelopmentalDisorderQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DevelopmentalDisorderQuestionRepository extends MongoRepository<DevelopmentalDisorderQuestion, String> {

    // Find by name in specific language
    @Query("{'names.?0': ?1}")
    Optional<DevelopmentalDisorderQuestion> findByNameInLanguage(String languageCode, String name);
    
    // Find by name containing text in any language
    @Query("{'$or': [{'names': {$regex: ?0, $options: 'i'}}]}")
    List<DevelopmentalDisorderQuestion> findByNameContainingIgnoreCase(String name);
    
    // Find by symptoms containing text in any language
    @Query("{'$or': [{'mainSymptoms': {$regex: ?0, $options: 'i'}}]}")
    List<DevelopmentalDisorderQuestion> findBySymptomsContainingIgnoreCase(String symptoms);
    
    // Find by screening questions containing text in any language
    @Query("{'$or': [{'screeningQuestions': {$regex: ?0, $options: 'i'}}]}")
    List<DevelopmentalDisorderQuestion> findByScreeningQuestionsContainingIgnoreCase(String questions);
    
    // Find by age range in months
    List<DevelopmentalDisorderQuestion> findByDetectionAgeMinMonthsBetween(Integer minMonths, Integer maxMonths);
    
    // Find by age range in years
    List<DevelopmentalDisorderQuestion> findByDetectionAgeMinYearsBetween(Integer minYears, Integer maxYears);
    
    // Find questions that have content in specific language
    @Query("{'$or': [{'names.?0': {$exists: true}}, {'mainSymptoms.?0': {$exists: true}}, {'screeningQuestions.?0': {$exists: true}}]}")
    List<DevelopmentalDisorderQuestion> findByLanguageAvailable(String languageCode);
    
    // Find questions that have names in specific language
    @Query("{'names.?0': {$exists: true}}")
    List<DevelopmentalDisorderQuestion> findByNamesInLanguage(String languageCode);
}

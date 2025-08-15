package com.meowcdd.repository.mongo;

import com.meowcdd.document.DevelopmentDisorder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DevelopmentDisorderRepository extends MongoRepository<DevelopmentDisorder, String> {
    
    List<DevelopmentDisorder> findByChildExternalId(String childExternalId);
    
    Optional<DevelopmentDisorder> findByChildExternalIdAndDisorderType(String childExternalId, String disorderType);
    
    List<DevelopmentDisorder> findByDisorderType(String disorderType);
    
    List<DevelopmentDisorder> findBySeverity(String severity);
    
    List<DevelopmentDisorder> findByDiagnosedBy(String diagnosedBy);
    
    @Query("{'diagnosisDate': {$gte: ?0, $lte: ?1}}")
    List<DevelopmentDisorder> findByDiagnosisDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("{'childExternalId': ?0, 'currentStatus': ?1}")
    List<DevelopmentDisorder> findByChildExternalIdAndCurrentStatus(String childExternalId, String currentStatus);
    
    @Query("{'symptoms.severity': ?0}")
    List<DevelopmentDisorder> findBySymptomSeverity(String severity);
    
    @Query("{'treatments.status': ?0}")
    List<DevelopmentDisorder> findByTreatmentStatus(String status);
    
    @Query("{'assessments.assessmentDate': {$gte: ?0}}")
    List<DevelopmentDisorder> findByAssessmentDateAfter(LocalDateTime date);
    
    boolean existsByChildExternalId(String childExternalId);
}

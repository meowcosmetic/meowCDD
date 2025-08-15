package com.meowcdd.repository.mongo;

import com.meowcdd.document.CDDTestResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CDDTestResultRepository extends MongoRepository<CDDTestResult, String> {
    List<CDDTestResult> findByChildId(String childId);
    List<CDDTestResult> findByAssessmentId(String assessmentId);
    List<CDDTestResult> findByAssessmentCode(String assessmentCode);
    List<CDDTestResult> findByAdministratorId(String administratorId);
    List<CDDTestResult> findByStatus(String status);
    List<CDDTestResult> findByRiskLevel(String riskLevel);
    List<CDDTestResult> findByChildIdAndAssessmentCode(String childId, String assessmentCode);
    List<CDDTestResult> findByStartTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<CDDTestResult> findByChildIdAndStartTimeBetween(String childId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("{'childId': ?0, 'status': 'COMPLETED'}")
    List<CDDTestResult> findLatestByChildId(String childId);

    long countByRiskLevel(String riskLevel);
    long countByChildId(String childId);

    @Query("{'childId': ?0, 'status': 'COMPLETED'}")
    List<CDDTestResult> findTopByChildIdOrderByTotalScoreDesc(String childId);
}

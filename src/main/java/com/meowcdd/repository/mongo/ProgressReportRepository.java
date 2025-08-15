package com.meowcdd.repository.mongo;

import com.meowcdd.document.ProgressReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressReportRepository extends MongoRepository<ProgressReport, String> {
    
    List<ProgressReport> findByChildExternalId(String childExternalId);
    
    List<ProgressReport> findByChildExternalIdOrderByReportDateDesc(String childExternalId);
    
    List<ProgressReport> findByReportType(String reportType);
    
    List<ProgressReport> findByReporter(String reporter);
    
    @Query("{'reportDate': {$gte: ?0, $lte: ?1}}")
    List<ProgressReport> findByReportDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("{'childExternalId': ?0, 'reportDate': {$gte: ?1}}")
    List<ProgressReport> findByChildExternalIdAndReportDateAfter(String childExternalId, LocalDateTime date);
    
    @Query("{'overallProgress': ?0}")
    List<ProgressReport> findByOverallProgress(String progress);
    
    @Query("{'childExternalId': ?0, 'reportType': ?1}")
    List<ProgressReport> findByChildExternalIdAndReportType(String childExternalId, String reportType);
    
    @Query("{'milestones.category': ?0}")
    List<ProgressReport> findByMilestoneCategory(String category);
    
    @Query("{'challenges.severity': ?0}")
    List<ProgressReport> findByChallengeSeverity(String severity);
    
    @Query("{'goals.status': ?0}")
    List<ProgressReport> findByGoalStatus(String status);
    
    Optional<ProgressReport> findFirstByChildExternalIdOrderByReportDateDesc(String childExternalId);
    
    boolean existsByChildExternalId(String childExternalId);
}

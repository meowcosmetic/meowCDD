package com.meowcdd.repository.mongo;

import com.meowcdd.document.CDDTest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CDDTestRepository extends MongoRepository<CDDTest, String> {
    Optional<CDDTest> findByAssessmentCode(String assessmentCode);
    List<CDDTest> findByStatus(String status);
    List<CDDTest> findByCategory(String category);
    List<CDDTest> findByTargetAgeGroup(String targetAgeGroup);

    @Query("{'minAgeMonths': {$lte: ?0}, 'maxAgeMonths': {$gte: ?0}, 'status': 'ACTIVE'}")
    List<CDDTest> findByAgeMonths(Integer ageMonths);

    List<CDDTest> findByCategoryAndStatus(String category, String status);
    List<CDDTest> findByAdministrationType(String administrationType);
    boolean existsByAssessmentCode(String assessmentCode);
}

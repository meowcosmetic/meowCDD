package com.meowcdd.document;

import com.meowcdd.document.base.BaseDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "assessment_tests")
public class AssessmentTest extends BaseDocument {

    @Id
    private String id;

    // Multilingual content
    private Map<String, String> names; // Language code -> Test name
    private Map<String, String> descriptions; // Language code -> Test description
    private Map<String, String> instructions; // Language code -> Test instructions
    
    // Test metadata
    private String testCode; // Unique test identifier (e.g., "ADOS-2", "M-CHAT", "CARS-2")
    private String category; // Category of test (e.g., "AUTISM_SCREENING", "LANGUAGE_ASSESSMENT", "COGNITIVE_TEST")
    private String targetAgeGroup; // Target age group (e.g., "18-36_MONTHS", "3-7_YEARS", "ALL_AGES")
    private Integer estimatedDuration; // Estimated duration in minutes
    private String difficultyLevel; // EASY, MODERATE, DIFFICULT
    
    // Test structure
    private List<TestSection> sections; // Test sections/questions
    private Map<String, Object> scoringCriteria; // Scoring criteria and interpretation
    private Map<String, Object> cutoffScores; // Cutoff scores for different severity levels
    
    // Administration details
    private String administrationType; // INDIVIDUAL, GROUP, PARENT_REPORT, OBSERVATION
    private String requiredQualifications; // Required qualifications for administrators
    private List<String> requiredMaterials; // List of required materials
    private Map<String, String> administrationNotes; // Language code -> Administration notes
    
    // Validation and reliability
    private String validity; // Information about test validity
    private String reliability; // Information about test reliability
    private String normativeData; // Information about normative data
    
    // Status and versioning
    private String status; // ACTIVE, INACTIVE, DRAFT, DEPRECATED
    private String version; // Test version
    private String lastUpdatedBy; // Who last updated the test
    
    // Internal data structure for test sections
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TestSection {
        private String sectionId;
        private Map<String, String> sectionNames; // Language code -> Section name
        private Map<String, String> sectionDescriptions; // Language code -> Section description
        private List<TestQuestion> questions;
        private Integer timeLimit; // Time limit in minutes (null if no limit)
        private String scoringMethod; // SCORING_METHOD (e.g., "LIKERT_SCALE", "YES_NO", "MULTIPLE_CHOICE")
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TestQuestion {
        private String questionId;
        private Map<String, String> questionTexts; // Language code -> Question text
        private Map<String, String> questionInstructions; // Language code -> Question instructions
        private String questionType; // QUESTION_TYPE (e.g., "MULTIPLE_CHOICE", "LIKERT_SCALE", "YES_NO", "OPEN_ENDED", "OBSERVATION")
        private List<QuestionOption> options; // For multiple choice questions
        private Map<String, Object> scoringRules; // Scoring rules for this question
        private String category; // Question category (e.g., "COMMUNICATION", "SOCIAL_INTERACTION", "REPETITIVE_BEHAVIORS")
        private Integer weight; // Question weight in scoring
        private Boolean required; // Whether this question is required
        private Map<String, String> hints; // Language code -> Hints for administrators
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class QuestionOption {
        private String optionId;
        private Map<String, String> optionTexts; // Language code -> Option text
        private Integer score; // Score for this option
        private String description; // Additional description for the option
    }
}


package com.meowcdd.entity.neon;

import com.meowcdd.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tracking_questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class TrackingQuestionNeon extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
    private String questionText;
    
    @Column(name = "question_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;
    
    @Column(name = "category")
    private String category;
    
    @Column(name = "subcategory")
    private String subcategory;
    
    @Column(name = "age_range_min")
    private Integer ageRangeMin;
    
    @Column(name = "age_range_max")
    private Integer ageRangeMax;
    
    @Column(name = "options_json", columnDefinition = "TEXT")
    private String optionsJson;
    
    @Column(name = "correct_answer")
    private String correctAnswer;
    
    @Column(name = "scoring_rules", columnDefinition = "TEXT")
    private String scoringRules;
    
    @Column(name = "difficulty_level")
    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficultyLevel;
    
    @Column(name = "estimated_time_seconds")
    private Integer estimatedTimeSeconds;
    
    @Column(name = "instructions", columnDefinition = "TEXT")
    private String instructions;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    
    @Column(name = "version")
    private String version;
    
    public enum QuestionType {
        MULTIPLE_CHOICE, TRUE_FALSE, OPEN_ENDED, RATING_SCALE, CHECKLIST
    }
    
    public enum DifficultyLevel {
        EASY, MEDIUM, HARD, EXPERT
    }
    
    public enum Status {
        DRAFT, ACTIVE, INACTIVE, ARCHIVED
    }
}

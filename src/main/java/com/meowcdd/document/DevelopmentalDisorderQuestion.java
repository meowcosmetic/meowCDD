package com.meowcdd.document;

import com.meowcdd.document.base.BaseDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "developmental_disorder_questions")
public class DevelopmentalDisorderQuestion extends BaseDocument {

    @Id
    private String id;

    // Multilingual content using Map<LanguageCode, Content>
    private Map<String, String> names; // Language code -> Name
    private Map<String, String> mainSymptoms; // Language code -> Main symptoms
    private Map<String, String> screeningQuestions; // Language code -> Screening questions
    
    private Integer detectionAgeMinMonths;
    private Integer detectionAgeMaxMonths;
    private Integer detectionAgeMinYears;
    private Integer detectionAgeMaxYears;
}

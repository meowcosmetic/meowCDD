package com.meowcdd.entity.supabase;

import com.meowcdd.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "tracking_questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class TrackingQuestion extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "category", columnDefinition = "JSON")
    private String category; // JSON: { "vi": "Giao tiếp", "en": "Communication" }
    
    @Column(name = "domain", nullable = false)
    private String domain; // Ví dụ: "communication", "social_interaction", "behavior_emotion", "cognition", "independence"
    
    @Column(name = "age_range", nullable = false)
    private String ageRange; // Độ tuổi áp dụng, ví dụ "2-6"
    
    @Column(name = "frequency", nullable = false)
    private String frequency; // Tần suất, ví dụ "daily"
    
    @Column(name = "context", columnDefinition = "JSON")
    private String context; // JSON array: ["home", "school"]
    
    @Column(name = "question", columnDefinition = "JSON")
    private String question; // JSON: { "vi": "...", "en": "..." }
    
    @Column(name = "options", columnDefinition = "JSON")
    private String options; // JSON array: [{ "text": { "vi": "...", "en": "..." }, "score": 0 }]
    
    @Column(name = "note", columnDefinition = "TEXT")
    private String note; // Ghi chú phụ huynh/giáo viên
    
    // Các enum cho domain
    public enum Domain {
        COMMUNICATION("communication"),
        SOCIAL_INTERACTION("social_interaction"),
        BEHAVIOR_EMOTION("behavior_emotion"),
        COGNITION("cognition"),
        INDEPENDENCE("independence");
        
        private final String value;
        
        Domain(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    // Các enum cho frequency
    public enum Frequency {
        DAILY("daily"),
        WEEKLY("weekly"),
        MONTHLY("monthly");
        
        private final String value;
        
        Frequency(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
}

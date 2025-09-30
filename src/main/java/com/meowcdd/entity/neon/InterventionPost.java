package com.meowcdd.entity.neon;

import com.meowcdd.config.converter.JsonStringConverter;
import com.meowcdd.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@Table(name = "intervention_posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class InterventionPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    @Convert(converter = JsonStringConverter.class)
    private Map<String, Object> content; // JSON object chứa nội dung bài post

    @Column(name = "post_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Column(name = "difficulty_level")
    private Integer difficultyLevel; // 1-5 (dễ đến khó)

    @Column(name = "target_age_min_months")
    private Integer targetAgeMinMonths;

    @Column(name = "target_age_max_months")
    private Integer targetAgeMaxMonths;

    @Column(name = "estimated_duration_minutes")
    private Integer estimatedDurationMinutes; // Thời gian ước tính thực hiện

    @Column(name = "tags", length = 1000)
    private String tags; // Các tag phân cách bằng dấu phẩy

    @Column(name = "is_published", nullable = false)
    @Builder.Default
    private Boolean isPublished = false;

    @Column(name = "author", length = 100)
    private String author;

    @Column(name = "version", length = 20)
    private String version; // Phiên bản của bài post

    // Liên kết với mục tiêu can thiệp
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criteria_id")
    private DevelopmentalItemCriteria criteria;

    // Liên kết với chương trình can thiệp (nếu có)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private DevelopmentalProgram program;

    public enum PostType {
        INTERVENTION_METHOD,    // Phương pháp can thiệp
        CHECKLIST,             // Checklist
        GUIDELINE,             // Hướng dẫn
        EXAMPLE,               // Ví dụ cụ thể
        TIP,                   // Mẹo thực hành
        TROUBLESHOOTING,       // Xử lý tình huống
        CONCLUSION             // Kết luận
    }
}

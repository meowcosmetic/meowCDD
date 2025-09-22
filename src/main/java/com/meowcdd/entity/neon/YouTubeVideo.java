package com.meowcdd.entity.neon;

import com.meowcdd.entity.base.BaseEntity;
import com.meowcdd.config.converter.JsonbConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Entity lưu metadata video YouTube (không lưu nội dung video)
 */
@Entity
@Table(name = "youtube_videos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class YouTubeVideo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", nullable = false, length = 512)
    private String url;

    // Tiêu đề song ngữ lưu JSON {"vi": "...", "en": "..."}
    @Convert(converter = JsonbConverter.class)
    @Column(name = "title", columnDefinition = "TEXT")
    private Object title;

    // Mô tả song ngữ lưu JSON {"vi": "...", "en": "..."}
    @Convert(converter = JsonbConverter.class)
    @Column(name = "description", columnDefinition = "TEXT")
    private Object description;

    // Định dạng hỗ trợ (áp dụng chung như Book)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supported_format_id")
    private SupportedFormat supportedFormat;

    // Phân loại theo lĩnh vực phát triển
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "youtube_video_developmental_domains",
        joinColumns = @JoinColumn(name = "video_id"),
        inverseJoinColumns = @JoinColumn(name = "developmental_domain_id")
    )
    private Set<DevelopmentalDomain> developmentalDomains;

    // Từ khóa tìm kiếm đơn giản (CSV)
    @Column(name = "keywords")
    private String keywords;

    // Tag linh hoạt dưới dạng JSON (array/map)
    @Convert(converter = JsonbConverter.class)
    @Column(name = "tags", columnDefinition = "TEXT")
    private Object tags;

    // Ngôn ngữ chính của nội dung video (vi, en, ...)
    @Column(name = "language", length = 16)
    private String language;

    // Cờ hiển thị
    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "is_featured", nullable = false)
    @Builder.Default
    private Boolean isFeatured = false;

    @Column(name = "priority")
    private Integer priority;

    // Gợi ý độ tuổi
    @Column(name = "min_age")
    private Integer minAge;

    @Column(name = "max_age")
    private Integer maxAge;

    @Column(name = "age_group", length = 64)
    private String ageGroup;

    // Thông tin YouTube bổ sung (tùy chọn)
    @Column(name = "content_rating", length = 64)
    private String contentRating;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;
}

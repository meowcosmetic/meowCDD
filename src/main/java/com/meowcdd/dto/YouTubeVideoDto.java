package com.meowcdd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YouTubeVideoDto {

    private Long id;

    private String url;

    // JSON bilingual fields: {"vi": "..", "en": ".."}
    private Map<String, Object> title;
    private Map<String, Object> description;

    private Long supportedFormatId;
    private String supportedFormatName;

    private Set<UUID> developmentalDomainIds;
    private Set<String> developmentalDomainNames;

    private String keywords;

    private Object tags;

    private String language;

    private Boolean isActive;
    private Boolean isFeatured;
    private Integer priority;

    private Integer minAge;
    private Integer maxAge;
    private String ageGroup;

    private String contentRating;
    private LocalDateTime publishedAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

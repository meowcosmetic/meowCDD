package com.meowcdd.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CDDTestCategoryDto {
    private Long id;
    private String code;
    private Map<String, Object> displayedName;
    private Map<String, Object> description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}



package com.meowcdd.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DevelopmentalProgramDto {
    private Long id;
    // name/description là JSON object
    private Map<String, Object> name;
    private Map<String, Object> description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}



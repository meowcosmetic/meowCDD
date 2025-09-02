package com.meowcdd.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DevelopmentalItemCriteriaDto {
    private Long id;
    private Long itemId;
    private String itemCode;
    private String code;
    private String description; // JSON string
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}



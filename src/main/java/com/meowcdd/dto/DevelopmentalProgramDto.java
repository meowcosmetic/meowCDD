package com.meowcdd.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DevelopmentalProgramDto {
    private Long id;
    private String code;
    // name/description lưu JSON dưới dạng String
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}



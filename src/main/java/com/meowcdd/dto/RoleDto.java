package com.meowcdd.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto {
    private UUID roleId;
    private String roleName;
    private Map<String, Object> description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package com.meowcdd.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDetailDto {
    private UUID detailId;
    private UUID roleId;
    private String roleName;
    private Map<String, Object> educationRequirement;
    private Map<String, Object> mainTasks;
    private Map<String, Object> requiredSkills;
    private Map<String, Object> certificationsRequired;
    private Map<String, Object> experienceRequirement;
    private Map<String, Object> notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

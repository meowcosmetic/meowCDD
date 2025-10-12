package com.meowcdd.dto;

import com.meowcdd.entity.neon.Collaborator;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollaboratorDto {
    private UUID collaboratorId;
    private UUID userId;
    private UUID roleId;
    private String roleName;
    private String specialization;
    private Integer experienceYears;
    private Map<String, Object> certification;
    private String organization;
    private Map<String, Object> availability;
    private Collaborator.Status status;
    private Map<String, Object> notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package com.meowcdd.controller;

import com.meowcdd.dto.CollaboratorSpecializationDto;
import com.meowcdd.entity.neon.CollaboratorSpecialization;
import com.meowcdd.service.CollaboratorSpecializationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping({"/neon/collaborator-specializations", "/supabase/collaborator-specializations"})
@RequiredArgsConstructor
@Slf4j
public class CollaboratorSpecializationController {

    private final CollaboratorSpecializationService collaboratorSpecializationService;

    @PostMapping
    public ResponseEntity<CollaboratorSpecialization> createSpecialization(@Valid @RequestBody CollaboratorSpecializationDto dto) {
        log.info("Creating collaborator specialization (Neon): {}", dto.getSpecializationName());
        CollaboratorSpecialization createdSpecialization = collaboratorSpecializationService.createSpecialization(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSpecialization);
    }

    @GetMapping("/{specializationId}")
    public ResponseEntity<CollaboratorSpecialization> getSpecializationById(@PathVariable UUID specializationId) {
        log.info("Getting collaborator specialization by ID (Neon): {}", specializationId);
        Optional<CollaboratorSpecialization> specialization = collaboratorSpecializationService.getSpecializationById(specializationId);
        return specialization.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CollaboratorSpecialization>> getAllSpecializations() {
        log.info("Getting all collaborator specializations (Neon)");
        List<CollaboratorSpecialization> specializations = collaboratorSpecializationService.getAllSpecializations();
        return ResponseEntity.ok(specializations);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<CollaboratorSpecialization>> getAllSpecializationsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting all collaborator specializations with pagination (Neon)");
        Pageable pageable = PageRequest.of(page, size);
        Page<CollaboratorSpecialization> specializations = collaboratorSpecializationService.getAllSpecializationsWithPagination(pageable);
        return ResponseEntity.ok(specializations);
    }

    @GetMapping("/collaborator/{collaboratorId}")
    public ResponseEntity<List<CollaboratorSpecialization>> getSpecializationsByCollaboratorId(@PathVariable UUID collaboratorId) {
        log.info("Getting specializations for collaborator ID (Neon): {}", collaboratorId);
        List<CollaboratorSpecialization> specializations = collaboratorSpecializationService.getSpecializationsByCollaboratorId(collaboratorId);
        return ResponseEntity.ok(specializations);
    }

    @GetMapping("/collaborator/{collaboratorId}/primary")
    public ResponseEntity<List<CollaboratorSpecialization>> getPrimarySpecializationsByCollaboratorId(@PathVariable UUID collaboratorId) {
        log.info("Getting primary specializations for collaborator ID (Neon): {}", collaboratorId);
        List<CollaboratorSpecialization> specializations = collaboratorSpecializationService.getPrimarySpecializationsByCollaboratorId(collaboratorId);
        return ResponseEntity.ok(specializations);
    }

    @GetMapping("/name/{specializationName}")
    public ResponseEntity<List<CollaboratorSpecialization>> getSpecializationsBySpecializationName(@PathVariable String specializationName) {
        log.info("Getting specializations by name (Neon): {}", specializationName);
        List<CollaboratorSpecialization> specializations = collaboratorSpecializationService.getSpecializationsBySpecializationName(specializationName);
        return ResponseEntity.ok(specializations);
    }

    @GetMapping("/type/{specializationType}")
    public ResponseEntity<List<CollaboratorSpecialization>> getSpecializationsBySpecializationType(@PathVariable String specializationType) {
        log.info("Getting specializations by type (Neon): {}", specializationType);
        List<CollaboratorSpecialization> specializations = collaboratorSpecializationService.getSpecializationsBySpecializationType(specializationType);
        return ResponseEntity.ok(specializations);
    }

    @GetMapping("/proficiency/{proficiencyLevel}")
    public ResponseEntity<List<CollaboratorSpecialization>> getSpecializationsByProficiencyLevel(@PathVariable CollaboratorSpecialization.ProficiencyLevel proficiencyLevel) {
        log.info("Getting specializations by proficiency level (Neon): {}", proficiencyLevel);
        List<CollaboratorSpecialization> specializations = collaboratorSpecializationService.getSpecializationsByProficiencyLevel(proficiencyLevel);
        return ResponseEntity.ok(specializations);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<CollaboratorSpecialization>> getSpecializationsByStatus(@PathVariable CollaboratorSpecialization.Status status) {
        log.info("Getting specializations by status (Neon): {}", status);
        List<CollaboratorSpecialization> specializations = collaboratorSpecializationService.getSpecializationsByStatus(status);
        return ResponseEntity.ok(specializations);
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getDistinctSpecializationNames() {
        log.info("Getting distinct specialization names (Neon)");
        List<String> names = collaboratorSpecializationService.getDistinctSpecializationNames();
        return ResponseEntity.ok(names);
    }

    @GetMapping("/types")
    public ResponseEntity<List<String>> getDistinctSpecializationTypes() {
        log.info("Getting distinct specialization types (Neon)");
        List<String> types = collaboratorSpecializationService.getDistinctSpecializationTypes();
        return ResponseEntity.ok(types);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CollaboratorSpecialization>> searchSpecializations(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description) {
        log.info("Searching specializations (Neon) by name: {} or description: {}", name, description);
        List<CollaboratorSpecialization> specializations = collaboratorSpecializationService.searchSpecializations(name, description);
        return ResponseEntity.ok(specializations);
    }

    @PutMapping("/{specializationId}")
    public ResponseEntity<CollaboratorSpecialization> updateSpecialization(
            @PathVariable UUID specializationId,
            @Valid @RequestBody CollaboratorSpecializationDto dto) {
        log.info("Updating collaborator specialization (Neon): {}", specializationId);
        CollaboratorSpecialization updatedSpecialization = collaboratorSpecializationService.updateSpecialization(specializationId, dto);
        return ResponseEntity.ok(updatedSpecialization);
    }

    @DeleteMapping("/{specializationId}")
    public ResponseEntity<Void> deleteSpecialization(@PathVariable UUID specializationId) {
        log.info("Deleting collaborator specialization (Neon): {}", specializationId);
        collaboratorSpecializationService.deleteSpecialization(specializationId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/collaborator/{collaboratorId}")
    public ResponseEntity<Void> deleteSpecializationsByCollaboratorId(@PathVariable UUID collaboratorId) {
        log.info("Deleting all specializations for collaborator (Neon): {}", collaboratorId);
        collaboratorSpecializationService.deleteSpecializationsByCollaboratorId(collaboratorId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/collaborator/{collaboratorId}/dto")
    public ResponseEntity<List<CollaboratorSpecializationDto>> getSpecializationsByCollaboratorIdAsDto(@PathVariable UUID collaboratorId) {
        log.info("Getting specializations as DTO for collaborator ID (Neon): {}", collaboratorId);
        List<CollaboratorSpecializationDto> specializations = collaboratorSpecializationService.getSpecializationsByCollaboratorIdAsDto(collaboratorId);
        return ResponseEntity.ok(specializations);
    }
}

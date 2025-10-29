package com.meowcdd.controller;

import com.meowcdd.dto.SpecializationDto;
import com.meowcdd.entity.neon.Specialization;
import com.meowcdd.service.SpecializationService;
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
@RequestMapping({"/neon/specializations", "/supabase/specializations"})
@RequiredArgsConstructor
@Slf4j
public class SpecializationController {

    private final SpecializationService specializationService;

    @PostMapping
    public ResponseEntity<Specialization> createSpecialization(@Valid @RequestBody SpecializationDto dto) {
        log.info("Creating specialization (Neon): {}", dto.getSpecializationName());
        Specialization createdSpecialization = specializationService.createSpecialization(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSpecialization);
    }

    @GetMapping("/{specializationId}")
    public ResponseEntity<Specialization> getSpecializationById(@PathVariable UUID specializationId) {
        log.info("Getting specialization by ID (Neon): {}", specializationId);
        Optional<Specialization> specialization = specializationService.getSpecializationById(specializationId);
        return specialization.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{specializationName}")
    public ResponseEntity<Specialization> getSpecializationByName(@PathVariable String specializationName) {
        log.info("Getting specialization by name (Neon): {}", specializationName);
        Optional<Specialization> specialization = specializationService.getSpecializationByName(specializationName);
        return specialization.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Specialization>> getAllSpecializations() {
        log.info("Getting all specializations (Neon)");
        List<Specialization> specializations = specializationService.getAllSpecializations();
        return ResponseEntity.ok(specializations);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Specialization>> getAllSpecializationsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting all specializations with pagination (Neon)");
        Pageable pageable = PageRequest.of(page, size);
        Page<Specialization> specializations = specializationService.getAllSpecializationsWithPagination(pageable);
        return ResponseEntity.ok(specializations);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Specialization>> getActiveSpecializations() {
        log.info("Getting active specializations (Neon)");
        List<Specialization> specializations = specializationService.getActiveSpecializations();
        return ResponseEntity.ok(specializations);
    }

    @GetMapping("/type/{specializationType}")
    public ResponseEntity<List<Specialization>> getSpecializationsByType(@PathVariable String specializationType) {
        log.info("Getting specializations by type (Neon): {}", specializationType);
        List<Specialization> specializations = specializationService.getSpecializationsByType(specializationType);
        return ResponseEntity.ok(specializations);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Specialization>> getSpecializationsByCategory(@PathVariable String category) {
        log.info("Getting specializations by category (Neon): {}", category);
        List<Specialization> specializations = specializationService.getSpecializationsByCategory(category);
        return ResponseEntity.ok(specializations);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Specialization>> getSpecializationsByStatus(@PathVariable Specialization.Status status) {
        log.info("Getting specializations by status (Neon): {}", status);
        List<Specialization> specializations = specializationService.getSpecializationsByStatus(status);
        return ResponseEntity.ok(specializations);
    }

    @GetMapping("/types")
    public ResponseEntity<List<String>> getDistinctSpecializationTypes() {
        log.info("Getting distinct specialization types (Neon)");
        List<String> types = specializationService.getDistinctSpecializationTypes();
        return ResponseEntity.ok(types);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getDistinctCategories() {
        log.info("Getting distinct categories (Neon)");
        List<String> categories = specializationService.getDistinctCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Specialization>> searchSpecializations(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description) {
        log.info("Searching specializations (Neon) by name: {} or description: {}", name, description);
        List<Specialization> specializations = specializationService.searchSpecializations(name, description);
        return ResponseEntity.ok(specializations);
    }

    @GetMapping("/min-experience/{experienceYears}")
    public ResponseEntity<List<Specialization>> getSpecializationsByMinExperience(@PathVariable Integer experienceYears) {
        log.info("Getting specializations by min experience (Neon): {}", experienceYears);
        List<Specialization> specializations = specializationService.getSpecializationsByMinExperience(experienceYears);
        return ResponseEntity.ok(specializations);
    }

    @PutMapping("/{specializationId}")
    public ResponseEntity<Specialization> updateSpecialization(
            @PathVariable UUID specializationId,
            @Valid @RequestBody SpecializationDto dto) {
        log.info("Updating specialization (Neon): {}", specializationId);
        Specialization updatedSpecialization = specializationService.updateSpecialization(specializationId, dto);
        return ResponseEntity.ok(updatedSpecialization);
    }

    @DeleteMapping("/{specializationId}")
    public ResponseEntity<Void> deleteSpecialization(@PathVariable UUID specializationId) {
        log.info("Deleting specialization (Neon): {}", specializationId);
        specializationService.deleteSpecialization(specializationId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{specializationId}/deactivate")
    public ResponseEntity<Specialization> deactivateSpecialization(@PathVariable UUID specializationId) {
        log.info("Deactivating specialization (Neon): {}", specializationId);
        specializationService.deactivateSpecialization(specializationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/dto")
    public ResponseEntity<List<SpecializationDto>> getAllSpecializationsAsDto() {
        log.info("Getting all specializations as DTO (Neon)");
        List<SpecializationDto> specializations = specializationService.getAllSpecializationsAsDto();
        return ResponseEntity.ok(specializations);
    }

    @GetMapping("/active/dto")
    public ResponseEntity<List<SpecializationDto>> getActiveSpecializationsAsDto() {
        log.info("Getting active specializations as DTO (Neon)");
        List<SpecializationDto> specializations = specializationService.getActiveSpecializationsAsDto();
        return ResponseEntity.ok(specializations);
    }
}

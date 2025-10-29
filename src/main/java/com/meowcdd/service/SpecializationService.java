package com.meowcdd.service;

import com.meowcdd.dto.SpecializationDto;
import com.meowcdd.entity.neon.Specialization;
import com.meowcdd.repository.neon.SpecializationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SpecializationService {

    private final SpecializationRepository specializationRepository;

    public Specialization createSpecialization(SpecializationDto dto) {
        log.info("Creating specialization: {}", dto.getSpecializationName());
        Specialization specialization = dto.toEntity();
        return specializationRepository.save(specialization);
    }

    public Optional<Specialization> getSpecializationById(UUID specializationId) {
        log.info("Getting specialization by ID: {}", specializationId);
        return specializationRepository.findById(specializationId);
    }

    public Optional<Specialization> getSpecializationByName(String specializationName) {
        log.info("Getting specialization by name: {}", specializationName);
        return specializationRepository.findBySpecializationName(specializationName);
    }

    public List<Specialization> getAllSpecializations() {
        log.info("Getting all specializations");
        return specializationRepository.findAll();
    }

    public Page<Specialization> getAllSpecializationsWithPagination(Pageable pageable) {
        log.info("Getting all specializations with pagination");
        return specializationRepository.findAll(pageable);
    }

    public List<Specialization> getActiveSpecializations() {
        log.info("Getting active specializations");
        return specializationRepository.findActiveSpecializations();
    }

    public List<Specialization> getSpecializationsByType(String specializationType) {
        log.info("Getting specializations by type: {}", specializationType);
        return specializationRepository.findBySpecializationType(specializationType);
    }

    public List<Specialization> getSpecializationsByCategory(String category) {
        log.info("Getting specializations by category: {}", category);
        return specializationRepository.findByCategory(category);
    }

    public List<Specialization> getSpecializationsByStatus(Specialization.Status status) {
        log.info("Getting specializations by status: {}", status);
        return specializationRepository.findByStatus(status);
    }

    public List<String> getDistinctSpecializationTypes() {
        log.info("Getting distinct specialization types");
        return specializationRepository.findDistinctSpecializationTypes();
    }

    public List<String> getDistinctCategories() {
        log.info("Getting distinct categories");
        return specializationRepository.findDistinctCategories();
    }

    public List<Specialization> searchSpecializations(String name, String description) {
        log.info("Searching specializations by name: {} or description: {}", name, description);
        return specializationRepository.searchBySpecializationNameOrDescription(name, description);
    }

    public List<Specialization> getSpecializationsByMinExperience(Integer experienceYears) {
        log.info("Getting specializations by min experience: {}", experienceYears);
        return specializationRepository.findSpecializationsByMinExperience(experienceYears);
    }

    public Specialization updateSpecialization(UUID specializationId, SpecializationDto dto) {
        log.info("Updating specialization: {}", specializationId);
        
        Specialization existingSpecialization = specializationRepository.findById(specializationId)
                .orElseThrow(() -> new RuntimeException("Specialization not found with ID: " + specializationId));
        
        // Update fields
        existingSpecialization.setSpecializationName(dto.getSpecializationName());
        existingSpecialization.setSpecializationType(dto.getSpecializationType());
        existingSpecialization.setDescription(dto.getDescription());
        existingSpecialization.setCategory(dto.getCategory());
        existingSpecialization.setRequiredCertifications(dto.getRequiredCertifications());
        existingSpecialization.setTypicalSkills(dto.getTypicalSkills());
        existingSpecialization.setMinExperienceYears(dto.getMinExperienceYears());
        existingSpecialization.setIsActive(dto.getIsActive());
        existingSpecialization.setStatus(dto.getStatus());
        
        return specializationRepository.save(existingSpecialization);
    }

    public void deleteSpecialization(UUID specializationId) {
        log.info("Deleting specialization: {}", specializationId);
        specializationRepository.deleteById(specializationId);
    }

    public void deactivateSpecialization(UUID specializationId) {
        log.info("Deactivating specialization: {}", specializationId);
        Specialization specialization = specializationRepository.findById(specializationId)
                .orElseThrow(() -> new RuntimeException("Specialization not found with ID: " + specializationId));
        specialization.setIsActive(false);
        specialization.setStatus(Specialization.Status.INACTIVE);
        specializationRepository.save(specialization);
    }

    public List<SpecializationDto> getAllSpecializationsAsDto() {
        log.info("Getting all specializations as DTO");
        List<Specialization> specializations = getAllSpecializations();
        return specializations.stream()
                .map(SpecializationDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<SpecializationDto> getActiveSpecializationsAsDto() {
        log.info("Getting active specializations as DTO");
        List<Specialization> specializations = getActiveSpecializations();
        return specializations.stream()
                .map(SpecializationDto::fromEntity)
                .collect(Collectors.toList());
    }
}

package com.meowcdd.service;

import com.meowcdd.dto.CollaboratorSpecializationDto;
import com.meowcdd.entity.neon.Collaborator;
import com.meowcdd.entity.neon.CollaboratorSpecialization;
import com.meowcdd.entity.neon.Specialization;
import com.meowcdd.repository.neon.CollaboratorNeonRepository;
import com.meowcdd.repository.neon.CollaboratorSpecializationRepository;
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
public class CollaboratorSpecializationService {

    private final CollaboratorSpecializationRepository collaboratorSpecializationRepository;
    private final CollaboratorNeonRepository collaboratorNeonRepository;
    private final SpecializationRepository specializationRepository;
    private final SpecializationService specializationService;

    public CollaboratorSpecialization createSpecialization(CollaboratorSpecializationDto dto) {
        log.info("Creating collaborator specialization for collaborator: {} and specialization: {}", 
                dto.getCollaboratorId(), dto.getSpecializationId());
        
        Collaborator collaborator = collaboratorNeonRepository.findById(dto.getCollaboratorId())
                .orElseThrow(() -> new RuntimeException("Collaborator not found with ID: " + dto.getCollaboratorId()));
        
        Specialization specialization = specializationRepository.findById(dto.getSpecializationId())
                .orElseThrow(() -> new RuntimeException("Specialization not found with ID: " + dto.getSpecializationId()));
        
        CollaboratorSpecialization collaboratorSpecialization = dto.toEntity();
        collaboratorSpecialization.setCollaborator(collaborator);
        collaboratorSpecialization.setSpecialization(specialization);
        
        return collaboratorSpecializationRepository.save(collaboratorSpecialization);
    }

    public Optional<CollaboratorSpecialization> getSpecializationById(UUID specializationId) {
        log.info("Getting collaborator specialization by ID: {}", specializationId);
        return collaboratorSpecializationRepository.findById(specializationId);
    }

    public List<CollaboratorSpecialization> getAllSpecializations() {
        log.info("Getting all collaborator specializations");
        return collaboratorSpecializationRepository.findAll();
    }

    public Page<CollaboratorSpecialization> getAllSpecializationsWithPagination(Pageable pageable) {
        log.info("Getting all collaborator specializations with pagination");
        return collaboratorSpecializationRepository.findAll(pageable);
    }

    public List<CollaboratorSpecialization> getSpecializationsByCollaboratorId(UUID collaboratorId) {
        log.info("Getting specializations for collaborator ID: {}", collaboratorId);
        return collaboratorSpecializationRepository.findByCollaborator_CollaboratorId(collaboratorId);
    }

    public List<CollaboratorSpecialization> getPrimarySpecializationsByCollaboratorId(UUID collaboratorId) {
        log.info("Getting primary specializations for collaborator ID: {}", collaboratorId);
        return collaboratorSpecializationRepository.findPrimarySpecializationsByCollaboratorId(collaboratorId);
    }

    public List<CollaboratorSpecialization> getSpecializationsBySpecializationName(String specializationName) {
        log.info("Getting specializations by name: {}", specializationName);
        return collaboratorSpecializationRepository.findBySpecialization_SpecializationName(specializationName);
    }

    public List<CollaboratorSpecialization> getSpecializationsBySpecializationType(String specializationType) {
        log.info("Getting specializations by type: {}", specializationType);
        return collaboratorSpecializationRepository.findBySpecialization_SpecializationType(specializationType);
    }

    public List<CollaboratorSpecialization> getSpecializationsByProficiencyLevel(CollaboratorSpecialization.ProficiencyLevel proficiencyLevel) {
        log.info("Getting specializations by proficiency level: {}", proficiencyLevel);
        return collaboratorSpecializationRepository.findByProficiencyLevel(proficiencyLevel);
    }

    public List<CollaboratorSpecialization> getSpecializationsByStatus(CollaboratorSpecialization.Status status) {
        log.info("Getting specializations by status: {}", status);
        return collaboratorSpecializationRepository.findByStatus(status);
    }

    public List<String> getDistinctSpecializationNames() {
        log.info("Getting distinct specialization names");
        return specializationService.getDistinctSpecializationTypes();
    }

    public List<String> getDistinctSpecializationTypes() {
        log.info("Getting distinct specialization types");
        return specializationService.getDistinctSpecializationTypes();
    }

    public List<CollaboratorSpecialization> searchSpecializations(String name, String notes) {
        log.info("Searching specializations by name: {} or notes: {}", name, notes);
        return collaboratorSpecializationRepository.searchBySpecializationNameOrNotes(name, notes);
    }

    public CollaboratorSpecialization updateSpecialization(UUID specializationId, CollaboratorSpecializationDto dto) {
        log.info("Updating collaborator specialization: {}", specializationId);
        
        CollaboratorSpecialization existingSpecialization = collaboratorSpecializationRepository.findById(specializationId)
                .orElseThrow(() -> new RuntimeException("Specialization not found with ID: " + specializationId));
        
        // Update fields
        existingSpecialization.setYearsOfExperience(dto.getYearsOfExperience());
        existingSpecialization.setCertifications(dto.getCertifications());
        existingSpecialization.setSkills(dto.getSkills());
        existingSpecialization.setIsPrimary(dto.getIsPrimary());
        existingSpecialization.setProficiencyLevel(dto.getProficiencyLevel());
        existingSpecialization.setNotes(dto.getNotes());
        existingSpecialization.setStatus(dto.getStatus());
        
        return collaboratorSpecializationRepository.save(existingSpecialization);
    }

    public void deleteSpecialization(UUID specializationId) {
        log.info("Deleting collaborator specialization: {}", specializationId);
        collaboratorSpecializationRepository.deleteById(specializationId);
    }

    public void deleteSpecializationsByCollaboratorId(UUID collaboratorId) {
        log.info("Deleting all specializations for collaborator: {}", collaboratorId);
        List<CollaboratorSpecialization> specializations = getSpecializationsByCollaboratorId(collaboratorId);
        collaboratorSpecializationRepository.deleteAll(specializations);
    }

    public List<CollaboratorSpecializationDto> getSpecializationsByCollaboratorIdAsDto(UUID collaboratorId) {
        log.info("Getting specializations as DTO for collaborator ID: {}", collaboratorId);
        List<CollaboratorSpecialization> specializations = getSpecializationsByCollaboratorId(collaboratorId);
        return specializations.stream()
                .map(CollaboratorSpecializationDto::fromEntity)
                .collect(Collectors.toList());
    }
}

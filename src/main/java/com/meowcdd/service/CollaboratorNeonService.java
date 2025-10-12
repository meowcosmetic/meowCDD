package com.meowcdd.service;

import com.meowcdd.dto.CollaboratorDto;
import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.entity.neon.Collaborator;
import com.meowcdd.entity.neon.Role;
import com.meowcdd.repository.neon.CollaboratorNeonRepository;
import com.meowcdd.repository.neon.RoleNeonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CollaboratorNeonService {

    private final CollaboratorNeonRepository repository;
    private final RoleNeonRepository roleRepository;

    public CollaboratorDto create(CollaboratorDto dto) {
        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + dto.getRoleId()));
        
        Collaborator entity = convertToEntity(dto, role);
        Collaborator saved = repository.save(entity);
        return convertToDto(saved);
    }

    public CollaboratorDto update(UUID collaboratorId, CollaboratorDto dto) {
        Collaborator existing = repository.findById(collaboratorId)
                .orElseThrow(() -> new EntityNotFoundException("Collaborator not found: " + collaboratorId));
        
        if (dto.getRoleId() != null && !existing.getRole().getRoleId().equals(dto.getRoleId())) {
            Role role = roleRepository.findById(dto.getRoleId())
                    .orElseThrow(() -> new EntityNotFoundException("Role not found: " + dto.getRoleId()));
            existing.setRole(role);
        }
        
        if (dto.getUserId() != null) {
            existing.setUserId(dto.getUserId());
        }
        if (dto.getSpecialization() != null) {
            existing.setSpecialization(dto.getSpecialization());
        }
        if (dto.getExperienceYears() != null) {
            existing.setExperienceYears(dto.getExperienceYears());
        }
        if (dto.getCertification() != null) {
            existing.setCertification(dto.getCertification());
        }
        if (dto.getOrganization() != null) {
            existing.setOrganization(dto.getOrganization());
        }
        if (dto.getAvailability() != null) {
            existing.setAvailability(dto.getAvailability());
        }
        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
        }
        if (dto.getNotes() != null) {
            existing.setNotes(dto.getNotes());
        }
        
        Collaborator saved = repository.save(existing);
        return convertToDto(saved);
    }

    @Transactional(readOnly = true)
    public CollaboratorDto getById(UUID collaboratorId) {
        Collaborator entity = repository.findById(collaboratorId)
                .orElseThrow(() -> new EntityNotFoundException("Collaborator not found: " + collaboratorId));
        return convertToDto(entity);
    }

    public void delete(UUID collaboratorId) {
        if (!repository.existsById(collaboratorId)) {
            throw new EntityNotFoundException("Collaborator not found: " + collaboratorId);
        }
        repository.deleteById(collaboratorId);
    }

    @Transactional(readOnly = true)
    public PageResponseDto<CollaboratorDto> getAll(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Collaborator> p = repository.findAll(pageable);
        return PageResponseDto.<CollaboratorDto>builder()
                .content(p.getContent().stream().map(this::convertToDto).toList())
                .pageNumber(p.getNumber())
                .pageSize(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .isLast(p.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public PageResponseDto<CollaboratorDto> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Collaborator> p = repository.search(keyword, pageable);
        return PageResponseDto.<CollaboratorDto>builder()
                .content(p.getContent().stream().map(this::convertToDto).toList())
                .pageNumber(p.getNumber())
                .pageSize(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .isLast(p.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public List<CollaboratorDto> getByUserId(UUID userId) {
        return repository.findByUserId(userId)
                .stream().map(this::convertToDto).toList();
    }

    @Transactional(readOnly = true)
    public List<CollaboratorDto> getByRoleId(UUID roleId) {
        return repository.findByRole_RoleId(roleId)
                .stream().map(this::convertToDto).toList();
    }

    @Transactional(readOnly = true)
    public List<CollaboratorDto> getByStatus(Collaborator.Status status) {
        return repository.findByStatus(status)
                .stream().map(this::convertToDto).toList();
    }

    private Collaborator convertToEntity(CollaboratorDto dto, Role role) {
        return Collaborator.builder()
                .collaboratorId(dto.getCollaboratorId())
                .userId(dto.getUserId())
                .role(role)
                .specialization(dto.getSpecialization())
                .experienceYears(dto.getExperienceYears())
                .certification(dto.getCertification())
                .organization(dto.getOrganization())
                .availability(dto.getAvailability())
                .status(dto.getStatus())
                .notes(dto.getNotes())
                .build();
    }

    private CollaboratorDto convertToDto(Collaborator entity) {
        return CollaboratorDto.builder()
                .collaboratorId(entity.getCollaboratorId())
                .userId(entity.getUserId())
                .roleId(entity.getRole().getRoleId())
                .roleName(entity.getRole().getRoleName())
                .specialization(entity.getSpecialization())
                .experienceYears(entity.getExperienceYears())
                .certification(entity.getCertification())
                .organization(entity.getOrganization())
                .availability(entity.getAvailability())
                .status(entity.getStatus())
                .notes(entity.getNotes())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

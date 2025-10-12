package com.meowcdd.service;

import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.dto.RoleDetailDto;
import com.meowcdd.entity.neon.Role;
import com.meowcdd.entity.neon.RoleDetail;
import com.meowcdd.repository.neon.RoleDetailNeonRepository;
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
public class RoleDetailNeonService {

    private final RoleDetailNeonRepository repository;
    private final RoleNeonRepository roleRepository;

    public RoleDetailDto create(RoleDetailDto dto) {
        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + dto.getRoleId()));
        
        RoleDetail entity = convertToEntity(dto, role);
        RoleDetail saved = repository.save(entity);
        return convertToDto(saved);
    }

    public RoleDetailDto update(UUID detailId, RoleDetailDto dto) {
        RoleDetail existing = repository.findById(detailId)
                .orElseThrow(() -> new EntityNotFoundException("Role detail not found: " + detailId));
        
        if (dto.getRoleId() != null && !existing.getRole().getRoleId().equals(dto.getRoleId())) {
            Role role = roleRepository.findById(dto.getRoleId())
                    .orElseThrow(() -> new EntityNotFoundException("Role not found: " + dto.getRoleId()));
            existing.setRole(role);
        }
        
        if (dto.getEducationRequirement() != null) {
            existing.setEducationRequirement(dto.getEducationRequirement());
        }
        if (dto.getMainTasks() != null) {
            existing.setMainTasks(dto.getMainTasks());
        }
        if (dto.getRequiredSkills() != null) {
            existing.setRequiredSkills(dto.getRequiredSkills());
        }
        if (dto.getCertificationsRequired() != null) {
            existing.setCertificationsRequired(dto.getCertificationsRequired());
        }
        if (dto.getExperienceRequirement() != null) {
            existing.setExperienceRequirement(dto.getExperienceRequirement());
        }
        if (dto.getNotes() != null) {
            existing.setNotes(dto.getNotes());
        }
        
        RoleDetail saved = repository.save(existing);
        return convertToDto(saved);
    }

    @Transactional(readOnly = true)
    public RoleDetailDto getById(UUID detailId) {
        RoleDetail entity = repository.findById(detailId)
                .orElseThrow(() -> new EntityNotFoundException("Role detail not found: " + detailId));
        return convertToDto(entity);
    }

    public void delete(UUID detailId) {
        if (!repository.existsById(detailId)) {
            throw new EntityNotFoundException("Role detail not found: " + detailId);
        }
        repository.deleteById(detailId);
    }

    @Transactional(readOnly = true)
    public PageResponseDto<RoleDetailDto> getAll(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<RoleDetail> p = repository.findAll(pageable);
        return PageResponseDto.<RoleDetailDto>builder()
                .content(p.getContent().stream().map(this::convertToDto).toList())
                .pageNumber(p.getNumber())
                .pageSize(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .isLast(p.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public PageResponseDto<RoleDetailDto> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RoleDetail> p = repository.search(keyword, pageable);
        return PageResponseDto.<RoleDetailDto>builder()
                .content(p.getContent().stream().map(this::convertToDto).toList())
                .pageNumber(p.getNumber())
                .pageSize(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .isLast(p.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public List<RoleDetailDto> getByRoleId(UUID roleId) {
        return repository.findByRole_RoleId(roleId)
                .stream().map(this::convertToDto).toList();
    }

    private RoleDetail convertToEntity(RoleDetailDto dto, Role role) {
        return RoleDetail.builder()
                .detailId(dto.getDetailId())
                .role(role)
                .educationRequirement(dto.getEducationRequirement())
                .mainTasks(dto.getMainTasks())
                .requiredSkills(dto.getRequiredSkills())
                .certificationsRequired(dto.getCertificationsRequired())
                .experienceRequirement(dto.getExperienceRequirement())
                .notes(dto.getNotes())
                .build();
    }

    private RoleDetailDto convertToDto(RoleDetail entity) {
        return RoleDetailDto.builder()
                .detailId(entity.getDetailId())
                .roleId(entity.getRole().getRoleId())
                .roleName(entity.getRole().getRoleName())
                .educationRequirement(entity.getEducationRequirement())
                .mainTasks(entity.getMainTasks())
                .requiredSkills(entity.getRequiredSkills())
                .certificationsRequired(entity.getCertificationsRequired())
                .experienceRequirement(entity.getExperienceRequirement())
                .notes(entity.getNotes())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

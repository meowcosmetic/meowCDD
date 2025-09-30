package com.meowcdd.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meowcdd.dto.InterventionMethodDto;
import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.entity.neon.InterventionMethod;
import com.meowcdd.entity.neon.InterventionMethodGroup;
import com.meowcdd.repository.neon.InterventionMethodRepository;
import com.meowcdd.repository.neon.InterventionMethodGroupRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InterventionMethodService {

    private final InterventionMethodRepository repository;
    private final InterventionMethodGroupRepository groupRepository;
    private final ObjectMapper objectMapper;

    public InterventionMethodDto create(InterventionMethodDto dto) {
        if (dto.getCode() == null || dto.getCode().isBlank()) {
            throw new IllegalArgumentException("code is required");
        }
        if (dto.getGroupId() == null) {
            throw new IllegalArgumentException("groupId is required");
        }
        if (repository.existsByCodeActive(dto.getCode())) {
            throw new IllegalArgumentException("code already exists: " + dto.getCode());
        }
        
        // Validate group exists
        InterventionMethodGroup group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new EntityNotFoundException("Intervention method group not found with id: " + dto.getGroupId()));
        
        InterventionMethod entity = convertToEntity(dto);
        entity.setGroup(group);
        InterventionMethod saved = repository.save(entity);
        return convertToDto(saved);
    }

    public InterventionMethodDto update(Long id, InterventionMethodDto dto) {
        InterventionMethod existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Intervention method not found with id: " + id));
        
        if (dto.getCode() != null && !dto.getCode().equals(existing.getCode())) {
            if (repository.existsByCodeActive(dto.getCode())) {
                throw new IllegalArgumentException("code already exists: " + dto.getCode());
            }
            existing.setCode(dto.getCode());
        }
        if (dto.getDisplayedName() != null) {
            try {
                existing.setDisplayedName(objectMapper.writeValueAsString(dto.getDisplayedName()));
            } catch (Exception ex) {
                throw new IllegalArgumentException("Failed to serialize displayedName to JSON", ex);
            }
        }
        if (dto.getDescription() != null) {
            try {
                existing.setDescription(objectMapper.writeValueAsString(dto.getDescription()));
            } catch (Exception ex) {
                throw new IllegalArgumentException("Failed to serialize description to JSON", ex);
            }
        }
        if (dto.getMinAgeMonths() != null) existing.setMinAgeMonths(dto.getMinAgeMonths());
        if (dto.getMaxAgeMonths() != null) existing.setMaxAgeMonths(dto.getMaxAgeMonths());
        
        InterventionMethod saved = repository.save(existing);
        return convertToDto(saved);
    }

    @Transactional(readOnly = true)
    public InterventionMethodDto getById(Long id) {
        InterventionMethod entity = repository.findByIdActive(id)
                .orElseThrow(() -> new EntityNotFoundException("Intervention method not found with id: " + id));
        return convertToDto(entity);
    }

    @Transactional(readOnly = true)
    public InterventionMethodDto getByCode(String code) {
        InterventionMethod entity = repository.findByCodeActive(code)
                .orElseThrow(() -> new EntityNotFoundException("Intervention method not found with code: " + code));
        return convertToDto(entity);
    }

    public void delete(Long id) {
        InterventionMethod entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Intervention method not found with id: " + id));
        entity.setDeletedAt(LocalDateTime.now());
        repository.save(entity);
    }

    public void restore(Long id) {
        InterventionMethod entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Intervention method not found with id: " + id));
        entity.setDeletedAt(null);
        repository.save(entity);
    }

    @Transactional(readOnly = true)
    public PageResponseDto<InterventionMethodDto> getAll(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<InterventionMethod> p = repository.findAllActive(pageable);
        return PageResponseDto.<InterventionMethodDto>builder()
                .content(p.getContent().stream().map(this::convertToDto).toList())
                .pageNumber(p.getNumber())
                .pageSize(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .isLast(p.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public List<InterventionMethodDto> getAll() {
        return repository.findAllActive().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InterventionMethodDto> findByGroupId(Long groupId) {
        return repository.findAllActive().stream()
                .filter(method -> method.getGroup() != null && method.getGroup().getId().equals(groupId))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PageResponseDto<InterventionMethodDto> findByGroupId(Long groupId, int page, int size) {
        List<InterventionMethod> allMethods = repository.findAllActive();
        List<InterventionMethod> filteredMethods = allMethods.stream()
                .filter(method -> method.getGroup() != null && method.getGroup().getId().equals(groupId))
                .collect(Collectors.toList());
        
        // Manual pagination
        int start = page * size;
        int end = Math.min((start + size), filteredMethods.size());
        
        List<InterventionMethod> pagedMethods;
        if (start >= filteredMethods.size() || filteredMethods.isEmpty()) {
            pagedMethods = new ArrayList<>();
        } else {
            pagedMethods = filteredMethods.subList(start, end);
        }
        
        List<InterventionMethodDto> content = pagedMethods.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        
        return PageResponseDto.<InterventionMethodDto>builder()
                .content(content)
                .pageNumber(page)
                .pageSize(size)
                .totalElements((long) filteredMethods.size())
                .totalPages((int) Math.ceil((double) filteredMethods.size() / size))
                .isLast(page >= (int) Math.ceil((double) filteredMethods.size() / size) - 1)
                .build();
    }

    private InterventionMethodDto convertToDto(InterventionMethod e) {
        InterventionMethodDto dto = new InterventionMethodDto();
        dto.setId(e.getId());
        dto.setCode(e.getCode());
        
        // Convert String to JsonNode
        try {
            if (e.getDisplayedName() != null && !e.getDisplayedName().trim().isEmpty()) {
                dto.setDisplayedName(objectMapper.readTree(e.getDisplayedName()));
            }
            if (e.getDescription() != null && !e.getDescription().trim().isEmpty()) {
                dto.setDescription(objectMapper.readTree(e.getDescription()));
            }
        } catch (Exception ex) {
            log.warn("Failed to parse JSON from database for method {}: {}", e.getCode(), ex.getMessage());
            // Set as null if parsing fails
            dto.setDisplayedName(null);
            dto.setDescription(null);
        }
        
        dto.setGroupId(e.getGroup() != null ? e.getGroup().getId() : null);
        dto.setMinAgeMonths(e.getMinAgeMonths());
        dto.setMaxAgeMonths(e.getMaxAgeMonths());
        dto.setCreatedAt(e.getCreatedAt());
        dto.setUpdatedAt(e.getUpdatedAt());
        return dto;
    }

    private InterventionMethod convertToEntity(InterventionMethodDto d) {
        InterventionMethod entity = new InterventionMethod();
        entity.setId(d.getId());
        entity.setCode(d.getCode());
        // Convert JsonNode to String
        try {
            if (d.getDisplayedName() != null) {
                entity.setDisplayedName(objectMapper.writeValueAsString(d.getDisplayedName()));
            }
            if (d.getDescription() != null) {
                entity.setDescription(objectMapper.writeValueAsString(d.getDescription()));
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("Failed to serialize JSON to string", ex);
        }
        
        entity.setMinAgeMonths(d.getMinAgeMonths());
        entity.setMaxAgeMonths(d.getMaxAgeMonths());
        return entity;
    }
}


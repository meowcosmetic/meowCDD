package com.meowcdd.service;

import com.meowcdd.dto.InterventionMethodDto;
import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.entity.neon.InterventionMethod;
import com.meowcdd.repository.neon.InterventionMethodRepository;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InterventionMethodService {

    private final InterventionMethodRepository repository;

    public InterventionMethodDto create(InterventionMethodDto dto) {
        if (dto.getCode() == null || dto.getCode().isBlank()) {
            throw new IllegalArgumentException("code is required");
        }
        if (repository.existsByCode(dto.getCode())) {
            throw new IllegalArgumentException("code already exists: " + dto.getCode());
        }
        InterventionMethod entity = convertToEntity(dto);
        InterventionMethod saved = repository.save(entity);
        return convertToDto(saved);
    }

    public InterventionMethodDto update(Long id, InterventionMethodDto dto) {
        InterventionMethod existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Intervention method not found with id: " + id));
        
        if (dto.getCode() != null && !dto.getCode().equals(existing.getCode())) {
            if (repository.existsByCode(dto.getCode())) {
                throw new IllegalArgumentException("code already exists: " + dto.getCode());
            }
            existing.setCode(dto.getCode());
        }
        if (dto.getDisplayedName() != null) existing.setDisplayedName(dto.getDisplayedName());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        if (dto.getMinAgeMonths() != null) existing.setMinAgeMonths(dto.getMinAgeMonths());
        if (dto.getMaxAgeMonths() != null) existing.setMaxAgeMonths(dto.getMaxAgeMonths());
        
        InterventionMethod saved = repository.save(existing);
        return convertToDto(saved);
    }

    @Transactional(readOnly = true)
    public InterventionMethodDto getById(Long id) {
        InterventionMethod entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Intervention method not found with id: " + id));
        return convertToDto(entity);
    }

    @Transactional(readOnly = true)
    public InterventionMethodDto getByCode(String code) {
        InterventionMethod entity = repository.findByCode(code)
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
    public PageResponseDto<InterventionMethodDto> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InterventionMethod> p = repository.search(keyword, pageable);
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
        return repository.findByDeletedAtIsNull().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InterventionMethodDto> findByAgeRange(Integer minAge, Integer maxAge) {
        return repository.findByAgeRange(minAge, maxAge).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private InterventionMethodDto convertToDto(InterventionMethod e) {
        return InterventionMethodDto.builder()
                .id(e.getId())
                .code(e.getCode())
                .displayedName(e.getDisplayedName())
                .description(e.getDescription())
                .minAgeMonths(e.getMinAgeMonths())
                .maxAgeMonths(e.getMaxAgeMonths())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }

    private InterventionMethod convertToEntity(InterventionMethodDto d) {
        return InterventionMethod.builder()
                .id(d.getId())
                .code(d.getCode())
                .displayedName(d.getDisplayedName())
                .description(d.getDescription())
                .minAgeMonths(d.getMinAgeMonths())
                .maxAgeMonths(d.getMaxAgeMonths())
                .build();
    }
}


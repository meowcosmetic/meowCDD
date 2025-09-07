package com.meowcdd.service;

import com.meowcdd.dto.CDDTestCategoryDto;
import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.entity.neon.CDDTestCategory;
import com.meowcdd.repository.neon.CDDTestCategoryNeonRepository;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CDDTestCategoryNeonService {

    private final CDDTestCategoryNeonRepository repository;

    public CDDTestCategoryDto create(CDDTestCategoryDto dto) {
        if (dto.getCode() == null || dto.getCode().isBlank()) {
            throw new IllegalArgumentException("code is required");
        }
        if (repository.existsByCode(dto.getCode())) {
            throw new IllegalArgumentException("code already exists: " + dto.getCode());
        }
        CDDTestCategory entity = convertToEntity(dto);
        CDDTestCategory saved = repository.save(entity);
        return convertToDto(saved);
    }

    public CDDTestCategoryDto update(Long id, CDDTestCategoryDto dto) {
        CDDTestCategory existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        if (dto.getCode() != null) existing.setCode(dto.getCode());
        if (dto.getDisplayedName() != null) existing.setDisplayedName(dto.getDisplayedName());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        CDDTestCategory saved = repository.save(existing);
        return convertToDto(saved);
    }

    @Transactional(readOnly = true)
    public CDDTestCategoryDto getById(Long id) {
        CDDTestCategory entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        return convertToDto(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Category not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public PageResponseDto<CDDTestCategoryDto> getAll(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CDDTestCategory> p = repository.findAll(pageable);
        return PageResponseDto.<CDDTestCategoryDto>builder()
                .content(p.getContent().stream().map(this::convertToDto).toList())
                .pageNumber(p.getNumber())
                .pageSize(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .isLast(p.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public PageResponseDto<CDDTestCategoryDto> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CDDTestCategory> p = repository.search(keyword, pageable);
        return PageResponseDto.<CDDTestCategoryDto>builder()
                .content(p.getContent().stream().map(this::convertToDto).toList())
                .pageNumber(p.getNumber())
                .pageSize(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .isLast(p.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public List<CDDTestCategoryDto> getAll() {
        return repository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private CDDTestCategoryDto convertToDto(CDDTestCategory e) {
        return CDDTestCategoryDto.builder()
                .id(e.getId())
                .code(e.getCode())
                .displayedName(e.getDisplayedName())
                .description(e.getDescription())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }

    private CDDTestCategory convertToEntity(CDDTestCategoryDto d) {
        return CDDTestCategory.builder()
                .id(d.getId())
                .code(d.getCode())
                .displayedName(d.getDisplayedName())
                .description(d.getDescription())
                .build();
    }
}



package com.meowcdd.service;

import com.meowcdd.dto.DevelopmentalProgramDto;
import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.entity.neon.DevelopmentalProgram;
import com.meowcdd.repository.neon.DevelopmentalProgramNeonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DevelopmentalProgramNeonService {

    private final DevelopmentalProgramNeonRepository repository;

    public DevelopmentalProgramDto create(DevelopmentalProgramDto dto) {
        if (repository.existsByCode(dto.getCode())) {
            throw new IllegalArgumentException("Program code already exists: " + dto.getCode());
        }
        DevelopmentalProgram entity = convertToEntity(dto);
        DevelopmentalProgram saved = repository.save(entity);
        return convertToDto(saved);
    }

    public DevelopmentalProgramDto update(Long id, DevelopmentalProgramDto dto) {
        DevelopmentalProgram existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Program not found with id: " + id));
        if (dto.getCode() != null && !dto.getCode().equals(existing.getCode())) {
            if (repository.existsByCode(dto.getCode())) {
                throw new IllegalArgumentException("Program code already exists: " + dto.getCode());
            }
            existing.setCode(dto.getCode());
        }
        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        DevelopmentalProgram saved = repository.save(existing);
        return convertToDto(saved);
    }

    @Transactional(readOnly = true)
    public DevelopmentalProgramDto getById(Long id) {
        DevelopmentalProgram entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Program not found with id: " + id));
        return convertToDto(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Program not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public PageResponseDto<DevelopmentalProgramDto> getAll(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<DevelopmentalProgram> p = repository.findAll(pageable);
        return PageResponseDto.<DevelopmentalProgramDto>builder()
                .content(p.getContent().stream().map(this::convertToDto).toList())
                .pageNumber(p.getNumber())
                .pageSize(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .isLast(p.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public PageResponseDto<DevelopmentalProgramDto> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DevelopmentalProgram> p = repository.search(keyword, pageable);
        return PageResponseDto.<DevelopmentalProgramDto>builder()
                .content(p.getContent().stream().map(this::convertToDto).toList())
                .pageNumber(p.getNumber())
                .pageSize(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .isLast(p.isLast())
                .build();
    }

    private DevelopmentalProgramDto convertToDto(DevelopmentalProgram e) {
        return DevelopmentalProgramDto.builder()
                .id(e.getId())
                .code(e.getCode())
                .name(e.getName())
                .description(e.getDescription())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }

    private DevelopmentalProgram convertToEntity(DevelopmentalProgramDto d) {
        return DevelopmentalProgram.builder()
                .id(d.getId())
                .code(d.getCode())
                .name(d.getName())
                .description(d.getDescription())
                .build();
    }
}



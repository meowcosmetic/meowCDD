package com.meowcdd.service;

import com.meowcdd.dto.DevelopmentalItemCriteriaDto;
import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.entity.neon.DevelopmentalDomainItem;
import com.meowcdd.entity.neon.DevelopmentalItemCriteria;
import com.meowcdd.repository.neon.DevelopmentalDomainItemNeonRepository;
import com.meowcdd.repository.neon.DevelopmentalItemCriteriaNeonRepository;
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

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DevelopmentalItemCriteriaNeonService {

    private final DevelopmentalItemCriteriaNeonRepository repository;
    private final DevelopmentalDomainItemNeonRepository itemRepository;

    public DevelopmentalItemCriteriaDto create(DevelopmentalItemCriteriaDto dto) {
        DevelopmentalDomainItem item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new EntityNotFoundException("Domain item not found: " + dto.getItemId()));
        DevelopmentalItemCriteria entity = convertToEntity(dto, item);
        DevelopmentalItemCriteria saved = repository.save(entity);
        return convertToDto(saved);
    }

    public DevelopmentalItemCriteriaDto update(Long id, DevelopmentalItemCriteriaDto dto) {
        DevelopmentalItemCriteria existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Criteria not found: " + id));
        if (dto.getItemId() != null && (existing.getItem() == null || !existing.getItem().getId().equals(dto.getItemId()))) {
            DevelopmentalDomainItem item = itemRepository.findById(dto.getItemId())
                    .orElseThrow(() -> new EntityNotFoundException("Domain item not found: " + dto.getItemId()));
            existing.setItem(item);
        }
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        if (dto.getMinAgeMonths() != null) existing.setMinAgeMonths(dto.getMinAgeMonths());
        if (dto.getMaxAgeMonths() != null) existing.setMaxAgeMonths(dto.getMaxAgeMonths());
        if (dto.getLevel() != null) existing.setLevel(dto.getLevel());
        DevelopmentalItemCriteria saved = repository.save(existing);
        return convertToDto(saved);
    }

    @Transactional(readOnly = true)
    public DevelopmentalItemCriteriaDto getById(Long id) {
        DevelopmentalItemCriteria entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Criteria not found: " + id));
        return convertToDto(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Criteria not found: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public PageResponseDto<DevelopmentalItemCriteriaDto> getAll(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<DevelopmentalItemCriteria> p = repository.findAll(pageable);
        return PageResponseDto.<DevelopmentalItemCriteriaDto>builder()
                .content(p.getContent().stream().map(this::convertToDto).toList())
                .pageNumber(p.getNumber())
                .pageSize(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .isLast(p.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public PageResponseDto<DevelopmentalItemCriteriaDto> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DevelopmentalItemCriteria> p = repository.search(keyword, pageable);
        return PageResponseDto.<DevelopmentalItemCriteriaDto>builder()
                .content(p.getContent().stream().map(this::convertToDto).toList())
                .pageNumber(p.getNumber())
                .pageSize(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .isLast(p.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public List<DevelopmentalItemCriteriaDto> getByItem(Long itemId) {
        return repository.findByItem_Id(itemId).stream().map(this::convertToDto).toList();
    }

    private DevelopmentalItemCriteria convertToEntity(DevelopmentalItemCriteriaDto d, DevelopmentalDomainItem item) {
        return DevelopmentalItemCriteria.builder()
                .id(d.getId())
                .item(item)
                .description(d.getDescription())
                .minAgeMonths(d.getMinAgeMonths())
                .maxAgeMonths(d.getMaxAgeMonths())
                .level(d.getLevel())
                .build();
    }

    private DevelopmentalItemCriteriaDto convertToDto(DevelopmentalItemCriteria e) {
        return DevelopmentalItemCriteriaDto.builder()
                .id(e.getId())
                .itemId(e.getItem() != null ? e.getItem().getId() : null)
                .description(e.getDescription())
                .minAgeMonths(e.getMinAgeMonths())
                .maxAgeMonths(e.getMaxAgeMonths())
                .level(e.getLevel())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }
}



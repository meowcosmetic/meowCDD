package com.meowcdd.service;

import com.meowcdd.dto.DevelopmentalDomainItemDto;
import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.entity.neon.DevelopmentalDomain;
import com.meowcdd.entity.neon.DevelopmentalDomainItem;
import com.meowcdd.repository.neon.DevelopmentalDomainItemNeonRepository;
import com.meowcdd.repository.neon.DevelopmentalDomainNeonRepository;
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
public class DevelopmentalDomainItemNeonService {

    private final DevelopmentalDomainItemNeonRepository repository;
    private final DevelopmentalDomainNeonRepository domainRepository;

    public DevelopmentalDomainItemDto create(DevelopmentalDomainItemDto dto) {
        DevelopmentalDomain domain = domainRepository.findById(dto.getDomainId())
                .orElseThrow(() -> new EntityNotFoundException("Domain not found: " + dto.getDomainId()));
        DevelopmentalDomainItem entity = convertToEntity(dto, domain);
        DevelopmentalDomainItem saved = repository.save(entity);
        return convertToDto(saved);
    }

    public DevelopmentalDomainItemDto update(Long id, DevelopmentalDomainItemDto dto) {
        DevelopmentalDomainItem existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
        if (dto.getDomainId() != null && (existing.getDomain() == null || !existing.getDomain().getId().equals(dto.getDomainId()))) {
            DevelopmentalDomain domain = domainRepository.findById(dto.getDomainId())
                    .orElseThrow(() -> new EntityNotFoundException("Domain not found: " + dto.getDomainId()));
            existing.setDomain(domain);
        }
        if (dto.getTitle() != null) existing.setTitle(dto.getTitle());
        DevelopmentalDomainItem saved = repository.save(existing);
        return convertToDto(saved);
    }

    @Transactional(readOnly = true)
    public DevelopmentalDomainItemDto getById(Long id) {
        DevelopmentalDomainItem entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
        return convertToDto(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Item not found: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public PageResponseDto<DevelopmentalDomainItemDto> getAll(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<DevelopmentalDomainItem> p = repository.findAll(pageable);
        return PageResponseDto.<DevelopmentalDomainItemDto>builder()
                .content(p.getContent().stream().map(this::convertToDto).toList())
                .pageNumber(p.getNumber())
                .pageSize(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .isLast(p.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public PageResponseDto<DevelopmentalDomainItemDto> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DevelopmentalDomainItem> p = repository.search(keyword, pageable);
        return PageResponseDto.<DevelopmentalDomainItemDto>builder()
                .content(p.getContent().stream().map(this::convertToDto).toList())
                .pageNumber(p.getNumber())
                .pageSize(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .isLast(p.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public List<DevelopmentalDomainItemDto> getByDomain(UUID domainId) {
        return repository.findByDomain_Id(domainId).stream().map(this::convertToDto).toList();
    }

    private DevelopmentalDomainItem convertToEntity(DevelopmentalDomainItemDto d, DevelopmentalDomain domain) {
        return DevelopmentalDomainItem.builder()
                .id(d.getId())
                .domain(domain)
                .title(d.getTitle())
                .build();
    }

    private DevelopmentalDomainItemDto convertToDto(DevelopmentalDomainItem e) {
        return DevelopmentalDomainItemDto.builder()
                .id(e.getId())
                .domainId(e.getDomain() != null ? e.getDomain().getId() : null)
                .domainName(e.getDomain() != null ? e.getDomain().getName() : null)
                .title(e.getTitle())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }
}



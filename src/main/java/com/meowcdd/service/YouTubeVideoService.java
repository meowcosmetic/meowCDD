package com.meowcdd.service;

import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.dto.YouTubeVideoDto;
import com.meowcdd.entity.neon.DevelopmentalDomain;
import com.meowcdd.entity.neon.SupportedFormat;
import com.meowcdd.entity.neon.YouTubeVideo;
import com.meowcdd.repository.neon.DevelopmentalDomainNeonRepository;
import com.meowcdd.repository.neon.SupportedFormatNeonRepository;
import com.meowcdd.repository.neon.YouTubeVideoNeonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class YouTubeVideoService {

    private final YouTubeVideoNeonRepository videoRepository;
    private final SupportedFormatNeonRepository supportedFormatRepository;
    private final DevelopmentalDomainNeonRepository developmentalDomainRepository;

    public YouTubeVideoDto create(YouTubeVideoDto dto) {
        log.info("Creating YouTube video: {}", dto.getUrl());

        SupportedFormat supportedFormat = null;
        if (dto.getSupportedFormatId() != null) {
            supportedFormat = supportedFormatRepository.findById(dto.getSupportedFormatId())
                .orElseThrow(() -> new EntityNotFoundException("Supported format not found with id: " + dto.getSupportedFormatId()));
        }

        Set<DevelopmentalDomain> domains = new HashSet<>();
        if (dto.getDevelopmentalDomainIds() != null && !dto.getDevelopmentalDomainIds().isEmpty()) {
            domains = dto.getDevelopmentalDomainIds().stream()
                .map(id -> developmentalDomainRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Developmental domain not found with id: " + id)))
                .collect(Collectors.toSet());
        }

        YouTubeVideo entity = toEntity(dto);
        entity.setSupportedFormat(supportedFormat);
        entity.setDevelopmentalDomains(domains);

        YouTubeVideo saved = videoRepository.save(entity);
        return toDto(saved);
    }

    public YouTubeVideoDto update(Long id, YouTubeVideoDto dto) {
        log.info("Updating YouTube video id: {}", id);

        YouTubeVideo entity = videoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("YouTube video not found with id: " + id));

        if (dto.getSupportedFormatId() != null && (entity.getSupportedFormat() == null || !entity.getSupportedFormat().getId().equals(dto.getSupportedFormatId()))) {
            SupportedFormat supportedFormat = supportedFormatRepository.findById(dto.getSupportedFormatId())
                .orElseThrow(() -> new EntityNotFoundException("Supported format not found with id: " + dto.getSupportedFormatId()));
            entity.setSupportedFormat(supportedFormat);
        }

        if (dto.getDevelopmentalDomainIds() != null) {
            Set<DevelopmentalDomain> domains = dto.getDevelopmentalDomainIds().stream()
                .map(domainId -> developmentalDomainRepository.findById(domainId)
                    .orElseThrow(() -> new EntityNotFoundException("Developmental domain not found with id: " + domainId)))
                .collect(Collectors.toSet());
            entity.setDevelopmentalDomains(domains);
        }

        applyPartialUpdate(entity, dto);

        YouTubeVideo updated = videoRepository.save(entity);
        return toDto(updated);
    }

    @Transactional(readOnly = true)
    public YouTubeVideoDto getById(Long id) {
        YouTubeVideo entity = videoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("YouTube video not found with id: " + id));
        return toDto(entity);
    }

    public void delete(Long id) {
        if (!videoRepository.existsById(id)) {
            throw new EntityNotFoundException("YouTube video not found with id: " + id);
        }
        videoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public PageResponseDto<YouTubeVideoDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<YouTubeVideo> p = videoRepository.findAll(pageable);
        List<YouTubeVideoDto> items = p.getContent().stream().map(this::toDto).collect(Collectors.toList());
        return PageResponseDto.<YouTubeVideoDto>builder()
            .content(items)
            .pageNumber(p.getNumber())
            .pageSize(p.getSize())
            .totalElements(p.getTotalElements())
            .totalPages(p.getTotalPages())
            .isLast(p.isLast())
            .build();
    }

    @Transactional(readOnly = true)
    public List<YouTubeVideoDto> getByFormat(Long formatId) {
        return videoRepository.findBySupportedFormatId(formatId).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<YouTubeVideoDto> getByDomain(UUID domainId) {
        return videoRepository.findByDevelopmentalDomainId(domainId).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PageResponseDto<YouTubeVideoDto> searchByKeyword(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<YouTubeVideo> p = videoRepository.searchByKeyword(keyword, pageable);
        List<YouTubeVideoDto> items = p.getContent().stream().map(this::toDto).collect(Collectors.toList());
        return PageResponseDto.<YouTubeVideoDto>builder()
            .content(items)
            .pageNumber(p.getNumber())
            .pageSize(p.getSize())
            .totalElements(p.getTotalElements())
            .totalPages(p.getTotalPages())
            .isLast(p.isLast())
            .build();
    }

    // Helpers
    private YouTubeVideo toEntity(YouTubeVideoDto dto) {
        YouTubeVideo entity = new YouTubeVideo();
        entity.setId(dto.getId());
        entity.setUrl(dto.getUrl());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setKeywords(dto.getKeywords());
        entity.setTags(dto.getTags());
        entity.setLanguage(dto.getLanguage());
        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : Boolean.TRUE);
        entity.setIsFeatured(dto.getIsFeatured() != null ? dto.getIsFeatured() : Boolean.FALSE);
        entity.setPriority(dto.getPriority());
        entity.setMinAge(dto.getMinAge());
        entity.setMaxAge(dto.getMaxAge());
        entity.setAgeGroup(dto.getAgeGroup());
        entity.setContentRating(dto.getContentRating());
        entity.setPublishedAt(dto.getPublishedAt());
        return entity;
    }

    private YouTubeVideoDto toDto(YouTubeVideo entity) {
        return YouTubeVideoDto.builder()
            .id(entity.getId())
            .url(entity.getUrl())
            .title((java.util.Map<String, Object>) entity.getTitle())
            .description((java.util.Map<String, Object>) entity.getDescription())
            .supportedFormatId(entity.getSupportedFormat() != null ? entity.getSupportedFormat().getId() : null)
            .supportedFormatName(entity.getSupportedFormat() != null ? entity.getSupportedFormat().getFormatName() : null)
            .developmentalDomainIds(entity.getDevelopmentalDomains() != null ? entity.getDevelopmentalDomains().stream().map(DevelopmentalDomain::getId).collect(Collectors.toSet()) : null)
            .developmentalDomainNames(entity.getDevelopmentalDomains() != null ? entity.getDevelopmentalDomains().stream().map(DevelopmentalDomain::getName).collect(Collectors.toSet()) : null)
            .keywords(entity.getKeywords())
            .tags(entity.getTags())
            .language(entity.getLanguage())
            .isActive(entity.getIsActive())
            .isFeatured(entity.getIsFeatured())
            .priority(entity.getPriority())
            .minAge(entity.getMinAge())
            .maxAge(entity.getMaxAge())
            .ageGroup(entity.getAgeGroup())
            .contentRating(entity.getContentRating())
            .publishedAt(entity.getPublishedAt())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }

    private void applyPartialUpdate(YouTubeVideo entity, YouTubeVideoDto dto) {
        if (dto.getUrl() != null) entity.setUrl(dto.getUrl());
        if (dto.getTitle() != null) entity.setTitle(dto.getTitle());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
        if (dto.getKeywords() != null) entity.setKeywords(dto.getKeywords());
        if (dto.getTags() != null) entity.setTags(dto.getTags());
        if (dto.getLanguage() != null) entity.setLanguage(dto.getLanguage());
        if (dto.getIsActive() != null) entity.setIsActive(dto.getIsActive());
        if (dto.getIsFeatured() != null) entity.setIsFeatured(dto.getIsFeatured());
        if (dto.getPriority() != null) entity.setPriority(dto.getPriority());
        if (dto.getMinAge() != null) entity.setMinAge(dto.getMinAge());
        if (dto.getMaxAge() != null) entity.setMaxAge(dto.getMaxAge());
        if (dto.getAgeGroup() != null) entity.setAgeGroup(dto.getAgeGroup());
        if (dto.getContentRating() != null) entity.setContentRating(dto.getContentRating());
        if (dto.getPublishedAt() != null) entity.setPublishedAt(dto.getPublishedAt());
    }
}

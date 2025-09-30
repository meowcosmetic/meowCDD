package com.meowcdd.service;

import com.meowcdd.dto.InterventionPostDto;
import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.entity.neon.DevelopmentalItemCriteria;
import com.meowcdd.entity.neon.DevelopmentalProgram;
import com.meowcdd.entity.neon.InterventionPost;
import com.meowcdd.repository.neon.DevelopmentalItemCriteriaNeonRepository;
import com.meowcdd.repository.neon.DevelopmentalProgramNeonRepository;
import com.meowcdd.repository.neon.InterventionPostNeonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InterventionPostNeonService {

    private final InterventionPostNeonRepository repository;
    private final DevelopmentalItemCriteriaNeonRepository criteriaRepository;
    private final DevelopmentalProgramNeonRepository programRepository;

    @Transactional(readOnly = true)
    public List<InterventionPostDto> getAll() {
        return repository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public InterventionPostDto getById(Long id) {
        InterventionPost post = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("InterventionPost not found with id: " + id));
        return convertToDto(post);
    }

    @Transactional(readOnly = true)
    public PageResponseDto<InterventionPostDto> getAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InterventionPost> p = repository.findAll(pageable);
        return PageResponseDto.<InterventionPostDto>builder()
                .content(p.getContent().stream().map(this::convertToDto).toList())
                .pageNumber(p.getNumber())
                .pageSize(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .isLast(p.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public PageResponseDto<InterventionPostDto> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InterventionPost> p = repository.search(keyword, pageable);
        return PageResponseDto.<InterventionPostDto>builder()
                .content(p.getContent().stream().map(this::convertToDto).toList())
                .pageNumber(p.getNumber())
                .pageSize(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .isLast(p.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public List<InterventionPostDto> getByCriteria(Long criteriaId) {
        return repository.findByCriteria_Id(criteriaId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InterventionPostDto> getByCriteriaOrderByDifficulty(Long criteriaId) {
        return repository.findByCriteriaOrderByDifficulty(criteriaId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InterventionPostDto> getByProgram(Long programId) {
        return repository.findByProgram_Id(programId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InterventionPostDto> getByPostType(InterventionPost.PostType postType) {
        return repository.findByPostType(postType).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InterventionPostDto> getByTargetAge(Integer age) {
        return repository.findByTargetAge(age, null, null).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PageResponseDto<InterventionPostDto> getByFilters(Long criteriaId, 
                                                           InterventionPost.PostType postType, 
                                                           Boolean isPublished, 
                                                           int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InterventionPost> p = repository.findByFilters(criteriaId, postType, isPublished, pageable);
        return PageResponseDto.<InterventionPostDto>builder()
                .content(p.getContent().stream().map(this::convertToDto).toList())
                .pageNumber(p.getNumber())
                .pageSize(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .isLast(p.isLast())
                .build();
    }

    public InterventionPostDto create(InterventionPostDto dto) {
        InterventionPost post = convertToEntity(dto);
        post = repository.save(post);
        log.info("Created InterventionPost with id: {}", post.getId());
        return convertToDto(post);
    }

    public InterventionPostDto update(Long id, InterventionPostDto dto) {
        InterventionPost existingPost = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("InterventionPost not found with id: " + id));
        
        updateEntityFromDto(existingPost, dto);
        existingPost = repository.save(existingPost);
        log.info("Updated InterventionPost with id: {}", id);
        return convertToDto(existingPost);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("InterventionPost not found with id: " + id);
        }
        repository.deleteById(id);
        log.info("Deleted InterventionPost with id: {}", id);
    }

    public InterventionPostDto publish(Long id) {
        InterventionPost post = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("InterventionPost not found with id: " + id));
        post.setIsPublished(true);
        post = repository.save(post);
        log.info("Published InterventionPost with id: {}", id);
        return convertToDto(post);
    }

    public InterventionPostDto unpublish(Long id) {
        InterventionPost post = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("InterventionPost not found with id: " + id));
        post.setIsPublished(false);
        post = repository.save(post);
        log.info("Unpublished InterventionPost with id: {}", id);
        return convertToDto(post);
    }

    private InterventionPost convertToEntity(InterventionPostDto dto) {
        DevelopmentalItemCriteria criteria = null;
        if (dto.getCriteriaId() != null) {
            criteria = criteriaRepository.findById(dto.getCriteriaId())
                    .orElse(null);
        }

        DevelopmentalProgram program = null;
        if (dto.getProgramId() != null) {
            program = programRepository.findById(dto.getProgramId())
                    .orElse(null);
        }

        return InterventionPost.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .postType(dto.getPostType())
                .difficultyLevel(dto.getDifficultyLevel())
                .targetAgeMinMonths(dto.getTargetAgeMinMonths())
                .targetAgeMaxMonths(dto.getTargetAgeMaxMonths())
                .estimatedDurationMinutes(dto.getEstimatedDurationMinutes())
                .tags(dto.getTags())
                .isPublished(dto.getIsPublished())
                .author(dto.getAuthor())
                .version(dto.getVersion())
                .criteria(criteria)
                .program(program)
                .build();
    }

    private void updateEntityFromDto(InterventionPost entity, InterventionPostDto dto) {
        if (dto.getTitle() != null) entity.setTitle(dto.getTitle());
        if (dto.getContent() != null) entity.setContent(dto.getContent());
        if (dto.getPostType() != null) entity.setPostType(dto.getPostType());
        if (dto.getDifficultyLevel() != null) entity.setDifficultyLevel(dto.getDifficultyLevel());
        if (dto.getTargetAgeMinMonths() != null) entity.setTargetAgeMinMonths(dto.getTargetAgeMinMonths());
        if (dto.getTargetAgeMaxMonths() != null) entity.setTargetAgeMaxMonths(dto.getTargetAgeMaxMonths());
        if (dto.getEstimatedDurationMinutes() != null) entity.setEstimatedDurationMinutes(dto.getEstimatedDurationMinutes());
        if (dto.getTags() != null) entity.setTags(dto.getTags());
        if (dto.getIsPublished() != null) entity.setIsPublished(dto.getIsPublished());
        if (dto.getAuthor() != null) entity.setAuthor(dto.getAuthor());
        if (dto.getVersion() != null) entity.setVersion(dto.getVersion());
        
        if (dto.getCriteriaId() != null) {
            DevelopmentalItemCriteria criteria = criteriaRepository.findById(dto.getCriteriaId()).orElse(null);
            entity.setCriteria(criteria);
        }
        
        if (dto.getProgramId() != null) {
            DevelopmentalProgram program = programRepository.findById(dto.getProgramId()).orElse(null);
            entity.setProgram(program);
        }
    }

    private InterventionPostDto convertToDto(InterventionPost entity) {
        return InterventionPostDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .postType(entity.getPostType())
                .difficultyLevel(entity.getDifficultyLevel())
                .targetAgeMinMonths(entity.getTargetAgeMinMonths())
                .targetAgeMaxMonths(entity.getTargetAgeMaxMonths())
                .estimatedDurationMinutes(entity.getEstimatedDurationMinutes())
                .tags(entity.getTags())
                .isPublished(entity.getIsPublished())
                .author(entity.getAuthor())
                .version(entity.getVersion())
                .criteriaId(entity.getCriteria() != null ? entity.getCriteria().getId() : null)
                .criteriaDescription(entity.getCriteria() != null ? 
                    entity.getCriteria().getDescription().toString() : null)
                .programId(entity.getProgram() != null ? entity.getProgram().getId() : null)
                .programName(entity.getProgram() != null ? entity.getProgram().getName().toString() : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

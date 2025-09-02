package com.meowcdd.service;

import com.meowcdd.dto.DevelopmentalAssessmentResultDto;
import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.entity.neon.ChildNeon;
import com.meowcdd.entity.neon.DevelopmentalAssessmentResult;
import com.meowcdd.entity.neon.DevelopmentalItemCriteria;
import com.meowcdd.entity.neon.DevelopmentalProgram;
import com.meowcdd.repository.neon.DevelopmentalAssessmentResultNeonRepository;
import com.meowcdd.repository.neon.DevelopmentalItemCriteriaNeonRepository;
import com.meowcdd.repository.neon.DevelopmentalProgramNeonRepository;
import com.meowcdd.repository.neon.ChildNeonRepository;
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
public class DevelopmentalAssessmentResultNeonService {

    private final DevelopmentalAssessmentResultNeonRepository repository;
    private final ChildNeonRepository childRepository;
    private final DevelopmentalItemCriteriaNeonRepository criteriaRepository;
    private final DevelopmentalProgramNeonRepository programRepository;

    public DevelopmentalAssessmentResultDto create(DevelopmentalAssessmentResultDto dto) {
        ChildNeon child = childRepository.findById(dto.getChildId())
                .orElseThrow(() -> new EntityNotFoundException("Child not found: " + dto.getChildId()));
        DevelopmentalItemCriteria criteria = criteriaRepository.findById(dto.getCriteriaId())
                .orElseThrow(() -> new EntityNotFoundException("Criteria not found: " + dto.getCriteriaId()));
        DevelopmentalProgram program = programRepository.findById(dto.getProgramId())
                .orElseThrow(() -> new EntityNotFoundException("Program not found: " + dto.getProgramId()));

        DevelopmentalAssessmentResult entity = convertToEntity(dto, child, criteria, program);
        DevelopmentalAssessmentResult saved = repository.save(entity);
        return convertToDto(saved);
    }

    public DevelopmentalAssessmentResultDto update(Long id, DevelopmentalAssessmentResultDto dto) {
        DevelopmentalAssessmentResult existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Assessment result not found: " + id));

        if (dto.getChildId() != null && (existing.getChild() == null || !existing.getChild().getId().equals(dto.getChildId()))) {
            ChildNeon child = childRepository.findById(dto.getChildId())
                    .orElseThrow(() -> new EntityNotFoundException("Child not found: " + dto.getChildId()));
            existing.setChild(child);
        }
        if (dto.getCriteriaId() != null && (existing.getCriteria() == null || !existing.getCriteria().getId().equals(dto.getCriteriaId()))) {
            DevelopmentalItemCriteria criteria = criteriaRepository.findById(dto.getCriteriaId())
                    .orElseThrow(() -> new EntityNotFoundException("Criteria not found: " + dto.getCriteriaId()));
            existing.setCriteria(criteria);
        }
        if (dto.getProgramId() != null && (existing.getProgram() == null || !existing.getProgram().getId().equals(dto.getProgramId()))) {
            DevelopmentalProgram program = programRepository.findById(dto.getProgramId())
                    .orElseThrow(() -> new EntityNotFoundException("Program not found: " + dto.getProgramId()));
            existing.setProgram(program);
        }

        if (dto.getStatus() != null) existing.setStatus(DevelopmentalAssessmentResult.Status.valueOf(dto.getStatus()));
        if (dto.getNotes() != null) existing.setNotes(dto.getNotes());
        if (dto.getAssessedAt() != null) existing.setAssessedAt(dto.getAssessedAt());
        if (dto.getAssessor() != null) existing.setAssessor(dto.getAssessor());

        DevelopmentalAssessmentResult saved = repository.save(existing);
        return convertToDto(saved);
    }

    @Transactional(readOnly = true)
    public DevelopmentalAssessmentResultDto getById(Long id) {
        DevelopmentalAssessmentResult entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Assessment result not found: " + id));
        return convertToDto(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Assessment result not found: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public PageResponseDto<DevelopmentalAssessmentResultDto> getAll(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<DevelopmentalAssessmentResult> p = repository.findAll(pageable);
        return PageResponseDto.<DevelopmentalAssessmentResultDto>builder()
                .content(p.getContent().stream().map(this::convertToDto).toList())
                .pageNumber(p.getNumber())
                .pageSize(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .isLast(p.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public PageResponseDto<DevelopmentalAssessmentResultDto> getByChild(Long childId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DevelopmentalAssessmentResult> p = repository.findByChild_Id(childId, pageable);
        return PageResponseDto.<DevelopmentalAssessmentResultDto>builder()
                .content(p.getContent().stream().map(this::convertToDto).toList())
                .pageNumber(p.getNumber())
                .pageSize(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .isLast(p.isLast())
                .build();
    }

    private DevelopmentalAssessmentResult convertToEntity(DevelopmentalAssessmentResultDto d, ChildNeon child, DevelopmentalItemCriteria criteria, DevelopmentalProgram program) {
        return DevelopmentalAssessmentResult.builder()
                .id(d.getId())
                .child(child)
                .criteria(criteria)
                .program(program)
                .status(d.getStatus() != null ? DevelopmentalAssessmentResult.Status.valueOf(d.getStatus()) : DevelopmentalAssessmentResult.Status.IN_PROGRESS)
                .notes(d.getNotes())
                .assessedAt(d.getAssessedAt())
                .assessor(d.getAssessor())
                .build();
    }

    private DevelopmentalAssessmentResultDto convertToDto(DevelopmentalAssessmentResult e) {
        return DevelopmentalAssessmentResultDto.builder()
                .id(e.getId())
                .childId(e.getChild() != null ? e.getChild().getId() : null)
                .criteriaId(e.getCriteria() != null ? e.getCriteria().getId() : null)
                .programId(e.getProgram() != null ? e.getProgram().getId() : null)
                .status(e.getStatus() != null ? e.getStatus().name() : null)
                .notes(e.getNotes())
                .assessedAt(e.getAssessedAt())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .assessor(e.getAssessor())
                .build();
    }
}



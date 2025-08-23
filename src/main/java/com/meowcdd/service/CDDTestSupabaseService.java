package com.meowcdd.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meowcdd.dto.CDDTestDto;
import com.meowcdd.entity.supabase.CDDTestSupabase;
import com.meowcdd.entity.supabase.RequiredQualifications;
import com.meowcdd.repository.supabase.CDDTestSupabaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.meowcdd.dto.PageResponseDto;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CDDTestSupabaseService {

    private final CDDTestSupabaseRepository repository;
    private final ObjectMapper objectMapper;

    public CDDTestDto createTest(CDDTestDto dto) {
        if (repository.existsByAssessmentCode(dto.getAssessmentCode())) {
            throw new IllegalArgumentException("Test code already exists: " + dto.getAssessmentCode());
        }

        CDDTestSupabase entity = convertToEntity(dto);
        CDDTestSupabase saved = repository.save(entity);
        return convertToDto(saved);
    }

    public CDDTestDto updateTest(Long id, CDDTestDto dto) {
        CDDTestSupabase existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Test not found with id: " + id));

        // Update fields
        existing.setAssessmentCode(dto.getAssessmentCode());
        existing.setCategory(dto.getCategory());
        existing.setMinAgeMonths(dto.getMinAgeMonths());
        existing.setMaxAgeMonths(dto.getMaxAgeMonths());
        existing.setStatus(CDDTestSupabase.Status.valueOf(dto.getStatus()));
        existing.setVersion(dto.getVersion());
        existing.setEstimatedDuration(dto.getEstimatedDuration());
        existing.setAdministrationType(CDDTestSupabase.AdministrationType.valueOf(dto.getAdministrationType()));
        existing.setRequiredQualifications(RequiredQualifications.valueOf(dto.getRequiredQualifications()));

        // Convert complex objects to JSON
        try {
            existing.setNamesJson(objectMapper.writeValueAsString(dto.getNames()));
            existing.setDescriptionsJson(objectMapper.writeValueAsString(dto.getDescriptions()));
            existing.setInstructionsJson(objectMapper.writeValueAsString(dto.getInstructions()));
            existing.setRequiredMaterialsJson(objectMapper.writeValueAsString(dto.getRequiredMaterials()));
            existing.setNotesJson(objectMapper.writeValueAsString(dto.getNotes()));
            existing.setQuestionsJson(objectMapper.writeValueAsString(dto.getQuestions()));
            existing.setScoringCriteriaJson(objectMapper.writeValueAsString(dto.getScoringCriteria()));
        } catch (JsonProcessingException e) {
            log.error("Error converting to JSON", e);
            throw new IllegalArgumentException("Error processing test data");
        }

        CDDTestSupabase updated = repository.save(existing);
        return convertToDto(updated);
    }

    public Optional<CDDTestDto> getTestById(Long id) {
        return repository.findById(id).map(this::convertToDto);
    }

    public Optional<CDDTestDto> getTestByCode(String code) {
        return repository.findByAssessmentCode(code).map(this::convertToDto);
    }

    public List<CDDTestDto> getAllTests() {
        return repository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<CDDTestDto> getTestsByStatus(String status) {
        CDDTestSupabase.Status enumStatus = CDDTestSupabase.Status.valueOf(status.toUpperCase());
        return repository.findByStatus(enumStatus).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<CDDTestDto> getTestsByCategory(String category) {
        return repository.findByCategory(category).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<CDDTestDto> getTestsByAgeMonths(Integer ageMonths) {
        return repository.findByAgeMonths(ageMonths).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<CDDTestDto> getTestsByAgeMonthsAndStatus(Integer ageMonths, String status) {
        CDDTestSupabase.Status enumStatus = CDDTestSupabase.Status.valueOf(status.toUpperCase());
        return repository.findByAgeMonthsAndStatus(ageMonths, enumStatus).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<CDDTestDto> getTestsByAgeMonthsAndCategory(Integer ageMonths, String category) {
        return repository.findByAgeMonthsAndCategory(ageMonths, category).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<CDDTestDto> getTestsByAgeMonthsAndStatusAndCategory(Integer ageMonths, String status, String category) {
        CDDTestSupabase.Status enumStatus = CDDTestSupabase.Status.valueOf(status.toUpperCase());
        return repository.findByAgeMonthsAndStatusAndCategory(ageMonths, enumStatus, category).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public PageResponseDto<CDDTestDto> getTestsByAgeMonthsWithPagination(Integer ageMonths, int page, int size) {
        List<CDDTestDto> allTests = getTestsByAgeMonths(ageMonths);
        return paginateResults(allTests, page, size);
    }
    
    public PageResponseDto<CDDTestDto> getTestsByAgeMonthsAndStatusWithPagination(Integer ageMonths, String status, int page, int size) {
        List<CDDTestDto> allTests = getTestsByAgeMonthsAndStatus(ageMonths, status);
        return paginateResults(allTests, page, size);
    }
    
    public PageResponseDto<CDDTestDto> getTestsByAgeMonthsAndCategoryWithPagination(Integer ageMonths, String category, int page, int size) {
        List<CDDTestDto> allTests = getTestsByAgeMonthsAndCategory(ageMonths, category);
        return paginateResults(allTests, page, size);
    }
    
    public PageResponseDto<CDDTestDto> getTestsByAgeMonthsAndStatusAndCategoryWithPagination(Integer ageMonths, String status, String category, int page, int size) {
        List<CDDTestDto> allTests = getTestsByAgeMonthsAndStatusAndCategory(ageMonths, status, category);
        return paginateResults(allTests, page, size);
    }
    
    private PageResponseDto<CDDTestDto> paginateResults(List<CDDTestDto> allTests, int page, int size) {
        int totalElements = allTests.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        
        // Validate page number
        if (page < 0) page = 0;
        if (page >= totalPages && totalPages > 0) page = totalPages - 1;
        
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalElements);
        
        List<CDDTestDto> pageContent = allTests.subList(startIndex, endIndex);
        
        return PageResponseDto.of(pageContent, page, size, totalElements);
    }

    public void deleteTest(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Test not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private CDDTestSupabase convertToEntity(CDDTestDto dto) {
        CDDTestSupabase entity = CDDTestSupabase.builder()
                .assessmentCode(dto.getAssessmentCode())
                .category(dto.getCategory())
                .minAgeMonths(dto.getMinAgeMonths())
                .maxAgeMonths(dto.getMaxAgeMonths())
                .status(CDDTestSupabase.Status.valueOf(dto.getStatus()))
                .version(dto.getVersion())
                .estimatedDuration(dto.getEstimatedDuration())
                .administrationType(CDDTestSupabase.AdministrationType.valueOf(dto.getAdministrationType()))
                .requiredQualifications(RequiredQualifications.valueOf(dto.getRequiredQualifications()))
                .build();

        try {
            entity.setNamesJson(objectMapper.writeValueAsString(dto.getNames()));
            entity.setDescriptionsJson(objectMapper.writeValueAsString(dto.getDescriptions()));
            entity.setInstructionsJson(objectMapper.writeValueAsString(dto.getInstructions()));
            entity.setRequiredMaterialsJson(objectMapper.writeValueAsString(dto.getRequiredMaterials()));
            entity.setNotesJson(objectMapper.writeValueAsString(dto.getNotes()));
            entity.setQuestionsJson(objectMapper.writeValueAsString(dto.getQuestions()));
            entity.setScoringCriteriaJson(objectMapper.writeValueAsString(dto.getScoringCriteria()));
        } catch (JsonProcessingException e) {
            log.error("Error converting to JSON", e);
            throw new IllegalArgumentException("Error processing test data");
        }

        return entity;
    }

    private CDDTestDto convertToDto(CDDTestSupabase entity) {
        CDDTestDto dto = CDDTestDto.builder()
                .id(entity.getId().toString())
                .assessmentCode(entity.getAssessmentCode())
                .category(entity.getCategory())
                .minAgeMonths(entity.getMinAgeMonths())
                .maxAgeMonths(entity.getMaxAgeMonths())
                .status(entity.getStatus().name())
                .version(entity.getVersion())
                .estimatedDuration(entity.getEstimatedDuration())
                .administrationType(entity.getAdministrationType().name())
                .requiredQualifications(entity.getRequiredQualifications().name())
                .build();

        try {
            if (entity.getNamesJson() != null) {
                dto.setNames(objectMapper.readValue(entity.getNamesJson(), 
                    new TypeReference<Map<String, String>>() {}));
            }
            if (entity.getDescriptionsJson() != null) {
                dto.setDescriptions(objectMapper.readValue(entity.getDescriptionsJson(), 
                    new TypeReference<Map<String, String>>() {}));
            }
            if (entity.getInstructionsJson() != null) {
                dto.setInstructions(objectMapper.readValue(entity.getInstructionsJson(), 
                    new TypeReference<Map<String, String>>() {}));
            }
            if (entity.getRequiredMaterialsJson() != null) {
                dto.setRequiredMaterials(objectMapper.readValue(entity.getRequiredMaterialsJson(), 
                    new TypeReference<List<String>>() {}));
            }
            if (entity.getNotesJson() != null) {
                dto.setNotes(objectMapper.readValue(entity.getNotesJson(), 
                    new TypeReference<Map<String, String>>() {}));
            }
            if (entity.getQuestionsJson() != null) {
                dto.setQuestions(objectMapper.readValue(entity.getQuestionsJson(), 
                    new TypeReference<List<CDDTestDto.YesNoQuestionDto>>() {}));
            }
            if (entity.getScoringCriteriaJson() != null) {
                dto.setScoringCriteria(objectMapper.readValue(entity.getScoringCriteriaJson(), 
                    new TypeReference<CDDTestDto.ScoringCriteriaDto>() {}));
            }
        } catch (JsonProcessingException e) {
            log.error("Error converting from JSON", e);
        }

        return dto;
    }
}

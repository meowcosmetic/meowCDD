package com.meowcdd.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meowcdd.dto.CDDTestDto;
import com.meowcdd.entity.supabase.CDDTestSupabase;
import com.meowcdd.repository.supabase.CDDTestSupabaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CDDTestSupabaseService {

    private final CDDTestSupabaseRepository repository;
    private final ObjectMapper objectMapper;

    public CDDTestDto createTest(CDDTestDto dto) {
        if (repository.existsByCode(dto.getAssessmentCode())) {
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
        existing.setCode(dto.getAssessmentCode());
        existing.setName(dto.getNames() != null ? dto.getNames().get("vi") : "Test");
        existing.setDescription(dto.getDescriptions() != null ? dto.getDescriptions().get("vi") : "");
        existing.setCategory(dto.getCategory());
        existing.setMinAgeMonths(dto.getMinAgeMonths());
        existing.setMaxAgeMonths(dto.getMaxAgeMonths());
        existing.setStatus(CDDTestSupabase.Status.valueOf(dto.getStatus()));
        existing.setTotalQuestions(dto.getScoringCriteria() != null ? dto.getScoringCriteria().getTotalQuestions() : 0);
        existing.setPassingScore(0); // Default value
        existing.setMaxScore(dto.getScoringCriteria() != null ? dto.getScoringCriteria().getTotalQuestions() : 0);
        existing.setInstructions(dto.getInstructions() != null ? dto.getInstructions().get("vi") : "");
        existing.setNotes(dto.getNotes() != null ? dto.getNotes().get("vi") : "");

        // Convert complex objects to JSON
        try {
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
        return repository.findByCode(code).map(this::convertToDto);
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

    public void deleteTest(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Test not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private CDDTestSupabase convertToEntity(CDDTestDto dto) {
        CDDTestSupabase entity = CDDTestSupabase.builder()
                .code(dto.getAssessmentCode())
                .name(dto.getNames() != null ? dto.getNames().get("vi") : "Test")
                .description(dto.getDescriptions() != null ? dto.getDescriptions().get("vi") : "")
                .category(dto.getCategory())
                .minAgeMonths(dto.getMinAgeMonths())
                .maxAgeMonths(dto.getMaxAgeMonths())
                .status(CDDTestSupabase.Status.valueOf(dto.getStatus()))
                .totalQuestions(dto.getScoringCriteria() != null ? dto.getScoringCriteria().getTotalQuestions() : 0)
                .passingScore(0) // Default value
                .maxScore(dto.getScoringCriteria() != null ? dto.getScoringCriteria().getTotalQuestions() : 0)
                .instructions(dto.getInstructions() != null ? dto.getInstructions().get("vi") : "")
                .notes(dto.getNotes() != null ? dto.getNotes().get("vi") : "")
                .build();

        try {
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
                .assessmentCode(entity.getCode())
                .names(Map.of("vi", entity.getName()))
                .descriptions(Map.of("vi", entity.getDescription()))
                .category(entity.getCategory())
                .minAgeMonths(entity.getMinAgeMonths())
                .maxAgeMonths(entity.getMaxAgeMonths())
                .status(entity.getStatus().name())
                .instructions(Map.of("vi", entity.getInstructions()))
                .notes(Map.of("vi", entity.getNotes()))
                .build();

        try {
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

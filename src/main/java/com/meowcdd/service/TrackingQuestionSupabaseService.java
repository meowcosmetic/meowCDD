package com.meowcdd.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meowcdd.dto.TrackingQuestionDto;
import com.meowcdd.entity.supabase.TrackingQuestion;
import com.meowcdd.repository.supabase.TrackingQuestionSupabaseRepository;
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
public class TrackingQuestionSupabaseService {
    
    private final TrackingQuestionSupabaseRepository trackingQuestionRepository;
    private final ObjectMapper objectMapper;
    
    /**
     * Lưu một câu hỏi tracking mới
     */
    public TrackingQuestionDto createTrackingQuestion(TrackingQuestionDto dto) {
        try {
            TrackingQuestion entity = convertToEntity(dto);
            TrackingQuestion savedEntity = trackingQuestionRepository.save(entity);
            return convertToDto(savedEntity);
        } catch (Exception e) {
            log.error("Error creating tracking question: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create tracking question", e);
        }
    }
    
    /**
     * Cập nhật một câu hỏi tracking
     */
    public TrackingQuestionDto updateTrackingQuestion(Long id, TrackingQuestionDto dto) {
        try {
            Optional<TrackingQuestion> existingOpt = trackingQuestionRepository.findById(id);
            if (existingOpt.isEmpty()) {
                throw new RuntimeException("Tracking question not found with id: " + id);
            }
            
            TrackingQuestion existing = existingOpt.get();
            updateEntityFromDto(existing, dto);
            TrackingQuestion updatedEntity = trackingQuestionRepository.save(existing);
            return convertToDto(updatedEntity);
        } catch (Exception e) {
            log.error("Error updating tracking question with id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to update tracking question", e);
        }
    }
    
    /**
     * Lấy tất cả câu hỏi tracking
     */
    public List<TrackingQuestionDto> getAllTrackingQuestions() {
        try {
            List<TrackingQuestion> entities = trackingQuestionRepository.findAll();
            return entities.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching all tracking questions: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch tracking questions", e);
        }
    }
    
    /**
     * Lấy câu hỏi tracking theo ID
     */
    public TrackingQuestionDto getTrackingQuestionById(Long id) {
        try {
            Optional<TrackingQuestion> entityOpt = trackingQuestionRepository.findById(id);
            if (entityOpt.isEmpty()) {
                throw new RuntimeException("Tracking question not found with id: " + id);
            }
            return convertToDto(entityOpt.get());
        } catch (Exception e) {
            log.error("Error fetching tracking question with id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch tracking question", e);
        }
    }
    
    /**
     * Xóa câu hỏi tracking theo ID
     */
    public void deleteTrackingQuestion(Long id) {
        try {
            if (!trackingQuestionRepository.existsById(id)) {
                throw new RuntimeException("Tracking question not found with id: " + id);
            }
            trackingQuestionRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting tracking question with id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete tracking question", e);
        }
    }
    
    /**
     * Lấy câu hỏi theo domain
     */
    public List<TrackingQuestionDto> getTrackingQuestionsByDomain(String domain) {
        try {
            List<TrackingQuestion> entities = trackingQuestionRepository.findByDomain(domain);
            return entities.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching tracking questions by domain {}: {}", domain, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch tracking questions by domain", e);
        }
    }
    
    /**
     * Lấy câu hỏi theo độ tuổi
     */
    public List<TrackingQuestionDto> getTrackingQuestionsByAgeRange(String ageRange) {
        try {
            List<TrackingQuestion> entities = trackingQuestionRepository.findByAgeRange(ageRange);
            return entities.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching tracking questions by age range {}: {}", ageRange, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch tracking questions by age range", e);
        }
    }
    
    /**
     * Lấy câu hỏi theo domain và độ tuổi
     */
    public List<TrackingQuestionDto> getTrackingQuestionsByDomainAndAgeRange(String domain, String ageRange) {
        try {
            List<TrackingQuestion> entities = trackingQuestionRepository.findByDomainAndAgeRange(domain, ageRange);
            return entities.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching tracking questions by domain {} and age range {}: {}", domain, ageRange, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch tracking questions by domain and age range", e);
        }
    }
    
    /**
     * Lấy tất cả câu hỏi có sẵn cho một độ tuổi cụ thể
     */
    public List<TrackingQuestionDto> getAvailableQuestionsByAgeRange(String ageRange) {
        try {
            List<TrackingQuestion> entities = trackingQuestionRepository.findAvailableQuestionsByAgeRange(ageRange);
            return entities.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching available questions by age range {}: {}", ageRange, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch available questions by age range", e);
        }
    }
    
    /**
     * Chuyển đổi DTO thành Entity
     */
    private TrackingQuestion convertToEntity(TrackingQuestionDto dto) throws JsonProcessingException {
        return TrackingQuestion.builder()
                .id(dto.getId())
                .category(objectMapper.writeValueAsString(dto.getCategory()))
                .domain(dto.getDomain())
                .ageRange(dto.getAgeRange())
                .frequency(dto.getFrequency())
                .context(objectMapper.writeValueAsString(dto.getContext()))
                .question(objectMapper.writeValueAsString(dto.getQuestion()))
                .options(objectMapper.writeValueAsString(dto.getOptions()))
                .note(dto.getNote())
                .build();
    }
    
    /**
     * Chuyển đổi Entity thành DTO
     */
    private TrackingQuestionDto convertToDto(TrackingQuestion entity) {
        try {
            return TrackingQuestionDto.builder()
                    .id(entity.getId())
                    .category(objectMapper.readValue(entity.getCategory(), new TypeReference<Map<String, String>>() {}))
                    .domain(entity.getDomain())
                    .ageRange(entity.getAgeRange())
                    .frequency(entity.getFrequency())
                    .context(objectMapper.readValue(entity.getContext(), new TypeReference<List<String>>() {}))
                    .question(objectMapper.readValue(entity.getQuestion(), new TypeReference<Map<String, String>>() {}))
                    .options(objectMapper.readValue(entity.getOptions(), new TypeReference<List<TrackingQuestionDto.QuestionOptionDto>>() {}))
                    .note(entity.getNote())
                    .createdAt(entity.getCreatedAt())
                    .updatedAt(entity.getUpdatedAt())
                    .build();
        } catch (JsonProcessingException e) {
            log.error("Error converting entity to DTO: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to convert entity to DTO", e);
        }
    }
    
    /**
     * Cập nhật Entity từ DTO
     */
    private void updateEntityFromDto(TrackingQuestion entity, TrackingQuestionDto dto) throws JsonProcessingException {
        if (dto.getCategory() != null) {
            entity.setCategory(objectMapper.writeValueAsString(dto.getCategory()));
        }
        if (dto.getDomain() != null) {
            entity.setDomain(dto.getDomain());
        }
        if (dto.getAgeRange() != null) {
            entity.setAgeRange(dto.getAgeRange());
        }
        if (dto.getFrequency() != null) {
            entity.setFrequency(dto.getFrequency());
        }
        if (dto.getContext() != null) {
            entity.setContext(objectMapper.writeValueAsString(dto.getContext()));
        }
        if (dto.getQuestion() != null) {
            entity.setQuestion(objectMapper.writeValueAsString(dto.getQuestion()));
        }
        if (dto.getOptions() != null) {
            entity.setOptions(objectMapper.writeValueAsString(dto.getOptions()));
        }
        if (dto.getNote() != null) {
            entity.setNote(dto.getNote());
        }
    }
}

package com.meowcdd.service;

import com.meowcdd.dto.SupportedFormatDto;
import com.meowcdd.entity.neon.SupportedFormat;
import com.meowcdd.repository.neon.SupportedFormatNeonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service cho entity SupportedFormat
 * Chứa business logic để quản lý các định dạng file được hỗ trợ
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SupportedFormatNeonService {
    
    private final SupportedFormatNeonRepository supportedFormatRepository;
    
    /**
     * Chuyển đổi Entity sang DTO
     * @param entity SupportedFormat entity
     * @return SupportedFormatDto
     */
    private SupportedFormatDto convertToDto(SupportedFormat entity) {
        return SupportedFormatDto.builder()
                .id(entity.getId())
                .formatName(entity.getFormatName())
                .fileExtension(entity.getFileExtension())
                .mimeType(entity.getMimeType())
                .category(entity.getCategory().name())
                .isActive(entity.getIsActive())
                .maxFileSize(entity.getMaxFileSize())
                .description(entity.getDescription())
                .versionInfo(entity.getVersionInfo())
                .processingPriority(entity.getProcessingPriority())
                .iconUrl(entity.getIconUrl())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
    
    /**
     * Chuyển đổi DTO sang Entity
     * @param dto SupportedFormatDto
     * @return SupportedFormat entity
     */
    private SupportedFormat convertToEntity(SupportedFormatDto dto) {
        return SupportedFormat.builder()
                .id(dto.getId())
                .formatName(dto.getFormatName())
                .fileExtension(dto.getFileExtension())
                .mimeType(dto.getMimeType())
                .category(SupportedFormat.FormatCategory.valueOf(dto.getCategory()))
                .isActive(dto.getIsActive())
                .maxFileSize(dto.getMaxFileSize())
                .description(dto.getDescription())
                .versionInfo(dto.getVersionInfo())
                .processingPriority(dto.getProcessingPriority())
                .iconUrl(dto.getIconUrl())
                .build();
    }
    
    /**
     * Lấy tất cả định dạng được hỗ trợ
     * @return List tất cả định dạng
     */
    @Transactional(readOnly = true)
    public List<SupportedFormatDto> getAllFormats() {
        log.info("Lấy tất cả định dạng được hỗ trợ");
        return supportedFormatRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Lấy tất cả định dạng đang hoạt động
     * @return List các định dạng có isActive = true
     */
    @Transactional(readOnly = true)
    public List<SupportedFormatDto> getActiveFormats() {
        log.info("Lấy tất cả định dạng đang hoạt động");
        return supportedFormatRepository.findByIsActiveTrue()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Lấy định dạng theo ID
     * @param id ID của định dạng
     * @return Optional chứa SupportedFormatDto nếu tìm thấy
     */
    @Transactional(readOnly = true)
    public Optional<SupportedFormatDto> getFormatById(Long id) {
        log.info("Tìm định dạng theo ID: {}", id);
        return supportedFormatRepository.findById(id)
                .map(this::convertToDto);
    }
    
    /**
     * Lấy định dạng theo tên
     * @param formatName tên định dạng
     * @return Optional chứa SupportedFormatDto nếu tìm thấy
     */
    @Transactional(readOnly = true)
    public Optional<SupportedFormatDto> getFormatByName(String formatName) {
        log.info("Tìm định dạng theo tên: {}", formatName);
        return supportedFormatRepository.findByFormatName(formatName)
                .map(this::convertToDto);
    }
    
    /**
     * Lấy định dạng theo phần mở rộng file
     * @param fileExtension phần mở rộng file (VD: .pdf, .jpg)
     * @return Optional chứa SupportedFormatDto nếu tìm thấy
     */
    @Transactional(readOnly = true)
    public Optional<SupportedFormatDto> getFormatByExtension(String fileExtension) {
        log.info("Tìm định dạng theo phần mở rộng: {}", fileExtension);
        return supportedFormatRepository.findByFileExtension(fileExtension)
                .map(this::convertToDto);
    }
    
    /**
     * Lấy định dạng theo MIME type
     * @param mimeType MIME type cần tìm
     * @return Optional chứa SupportedFormatDto nếu tìm thấy
     */
    @Transactional(readOnly = true)
    public Optional<SupportedFormatDto> getFormatByMimeType(String mimeType) {
        log.info("Tìm định dạng theo MIME type: {}", mimeType);
        return supportedFormatRepository.findByMimeType(mimeType)
                .map(this::convertToDto);
    }
    
    /**
     * Lấy danh sách định dạng theo category
     * @param category loại định dạng
     * @return List các định dạng thuộc category
     */
    @Transactional(readOnly = true)
    public List<SupportedFormatDto> getFormatsByCategory(String category) {
        log.info("Lấy định dạng theo category: {}", category);
        try {
            SupportedFormat.FormatCategory formatCategory = SupportedFormat.FormatCategory.valueOf(category.toUpperCase());
            return supportedFormatRepository.findByCategory(formatCategory)
                    .stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            log.error("Category không hợp lệ: {}", category);
            return List.of();
        }
    }
    
    /**
     * Lấy danh sách định dạng đang hoạt động theo category
     * @param category loại định dạng
     * @return List các định dạng đang hoạt động thuộc category
     */
    @Transactional(readOnly = true)
    public List<SupportedFormatDto> getActiveFormatsByCategory(String category) {
        log.info("Lấy định dạng đang hoạt động theo category: {}", category);
        try {
            SupportedFormat.FormatCategory formatCategory = SupportedFormat.FormatCategory.valueOf(category.toUpperCase());
            return supportedFormatRepository.findByCategoryAndIsActiveTrue(formatCategory)
                    .stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            log.error("Category không hợp lệ: {}", category);
            return List.of();
        }
    }
    
    /**
     * Tìm kiếm định dạng theo tên (contains)
     * @param formatName tên định dạng cần tìm (có thể là một phần)
     * @return List các định dạng có tên chứa từ khóa
     */
    @Transactional(readOnly = true)
    public List<SupportedFormatDto> searchFormatsByName(String formatName) {
        log.info("Tìm kiếm định dạng theo tên: {}", formatName);
        return supportedFormatRepository.findByFormatNameContainingIgnoreCase(formatName)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Kiểm tra xem định dạng có được hỗ trợ không
     * @param fileExtension phần mở rộng file
     * @return true nếu được hỗ trợ và đang hoạt động
     */
    @Transactional(readOnly = true)
    public boolean isFormatSupported(String fileExtension) {
        log.info("Kiểm tra định dạng có được hỗ trợ: {}", fileExtension);
        Optional<SupportedFormat> format = supportedFormatRepository.findByFileExtension(fileExtension);
        return format.isPresent() && format.get().getIsActive();
    }
    
    /**
     * Tạo định dạng mới
     * @param formatDto thông tin định dạng cần tạo
     * @return SupportedFormatDto đã tạo
     */
    public SupportedFormatDto createFormat(SupportedFormatDto formatDto) {
        log.info("Tạo định dạng mới: {}", formatDto.getFormatName());
        
        // Kiểm tra tên định dạng đã tồn tại chưa
        if (supportedFormatRepository.existsByFormatName(formatDto.getFormatName())) {
            throw new IllegalArgumentException("Định dạng với tên này đã tồn tại: " + formatDto.getFormatName());
        }
        
        // Kiểm tra phần mở rộng đã tồn tại chưa
        if (supportedFormatRepository.existsByFileExtension(formatDto.getFileExtension())) {
            throw new IllegalArgumentException("Định dạng với phần mở rộng này đã tồn tại: " + formatDto.getFileExtension());
        }
        
        SupportedFormat entity = convertToEntity(formatDto);
        SupportedFormat savedEntity = supportedFormatRepository.save(entity);
        log.info("Đã tạo định dạng thành công với ID: {}", savedEntity.getId());
        
        return convertToDto(savedEntity);
    }
    
    /**
     * Cập nhật định dạng
     * @param id ID của định dạng cần cập nhật
     * @param formatDto thông tin mới
     * @return SupportedFormatDto đã cập nhật
     */
    public SupportedFormatDto updateFormat(Long id, SupportedFormatDto formatDto) {
        log.info("Cập nhật định dạng với ID: {}", id);
        
        SupportedFormat existingFormat = supportedFormatRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy định dạng với ID: " + id));
        
        // Cập nhật các trường
        existingFormat.setFormatName(formatDto.getFormatName());
        existingFormat.setFileExtension(formatDto.getFileExtension());
        existingFormat.setMimeType(formatDto.getMimeType());
        existingFormat.setCategory(SupportedFormat.FormatCategory.valueOf(formatDto.getCategory()));
        existingFormat.setIsActive(formatDto.getIsActive());
        existingFormat.setMaxFileSize(formatDto.getMaxFileSize());
        existingFormat.setDescription(formatDto.getDescription());
        existingFormat.setVersionInfo(formatDto.getVersionInfo());
        existingFormat.setProcessingPriority(formatDto.getProcessingPriority());
        existingFormat.setIconUrl(formatDto.getIconUrl());
        
        SupportedFormat updatedEntity = supportedFormatRepository.save(existingFormat);
        log.info("Đã cập nhật định dạng thành công");
        
        return convertToDto(updatedEntity);
    }
    
    /**
     * Xóa định dạng (soft delete - set isActive = false)
     * @param id ID của định dạng cần xóa
     */
    public void deactivateFormat(Long id) {
        log.info("Vô hiệu hóa định dạng với ID: {}", id);
        
        SupportedFormat format = supportedFormatRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy định dạng với ID: " + id));
        
        format.setIsActive(false);
        supportedFormatRepository.save(format);
        log.info("Đã vô hiệu hóa định dạng thành công");
    }
    
    /**
     * Kích hoạt lại định dạng (set isActive = true)
     * @param id ID của định dạng cần kích hoạt
     */
    public void activateFormat(Long id) {
        log.info("Kích hoạt định dạng với ID: {}", id);
        
        SupportedFormat format = supportedFormatRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy định dạng với ID: " + id));
        
        format.setIsActive(true);
        supportedFormatRepository.save(format);
        log.info("Đã kích hoạt định dạng thành công");
    }
    
    /**
     * Xóa hoàn toàn định dạng khỏi database
     * @param id ID của định dạng cần xóa
     */
    public void deleteFormat(Long id) {
        log.info("Xóa hoàn toàn định dạng với ID: {}", id);
        
        if (!supportedFormatRepository.existsById(id)) {
            throw new IllegalArgumentException("Không tìm thấy định dạng với ID: " + id);
        }
        
        supportedFormatRepository.deleteById(id);
        log.info("Đã xóa định dạng thành công");
    }
}

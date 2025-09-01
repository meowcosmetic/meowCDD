package com.meowcdd.service;

import com.meowcdd.dto.DevelopmentalDomainDto;
import com.meowcdd.entity.neon.DevelopmentalDomain;
import com.meowcdd.repository.neon.DevelopmentalDomainNeonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service cho entity DevelopmentalDomain
 * Chứa business logic để quản lý các lĩnh vực phát triển của trẻ em
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DevelopmentalDomainNeonService {
    
    private final DevelopmentalDomainNeonRepository developmentalDomainRepository;
    
    /**
     * Chuyển đổi Entity sang DTO
     * @param entity DevelopmentalDomain entity
     * @return DevelopmentalDomainDto
     */
    private DevelopmentalDomainDto convertToDto(DevelopmentalDomain entity) {
        // Convert String to Object for displayedName and description
        Object displayedNameObj = null;
        Object descriptionObj = null;
        
        if (entity.getDisplayedName() != null) {
            try {
                // Try to parse as JSON object
                displayedNameObj = new com.fasterxml.jackson.databind.ObjectMapper()
                    .readValue(entity.getDisplayedName(), Object.class);
            } catch (Exception e) {
                // If not valid JSON, return as string
                displayedNameObj = entity.getDisplayedName();
            }
        }
        
        if (entity.getDescription() != null) {
            try {
                // Try to parse as JSON object
                descriptionObj = new com.fasterxml.jackson.databind.ObjectMapper()
                    .readValue(entity.getDescription(), Object.class);
            } catch (Exception e) {
                // If not valid JSON, return as string
                descriptionObj = entity.getDescription();
            }
        }
        
        return DevelopmentalDomainDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .displayedName(displayedNameObj)
                .description(descriptionObj)
                .category(entity.getCategory())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
    
    /**
     * Chuyển đổi Object thành String (hỗ trợ JSON object và string)
     * @param obj Object cần chuyển đổi
     * @return String representation
     */
    private String convertObjectToString(Object obj) {
        if (obj == null) {
            return null;
        }
        
        if (obj instanceof String) {
            return (String) obj;
        } else {
            // Convert object to JSON string
            try {
                return new com.fasterxml.jackson.databind.ObjectMapper()
                    .writeValueAsString(obj);
            } catch (Exception e) {
                log.warn("Could not convert object to JSON string: {}", e.getMessage());
                return obj.toString();
            }
        }
    }
    
    /**
     * Chuyển đổi DTO sang Entity
     * @param dto DevelopmentalDomainDto
     * @return DevelopmentalDomain entity
     */
    private DevelopmentalDomain convertToEntity(DevelopmentalDomainDto dto) {
        return DevelopmentalDomain.builder()
                .id(dto.getId())
                .name(dto.getName())
                .displayedName(convertObjectToString(dto.getDisplayedName()))
                .description(convertObjectToString(dto.getDescription()))
                .category(dto.getCategory())
                .build();
    }
    
    /**
     * Lấy tất cả lĩnh vực phát triển
     * @return List tất cả lĩnh vực phát triển
     */
    @Transactional(readOnly = true)
    public List<DevelopmentalDomainDto> getAllDomains() {
        log.info("Lấy tất cả lĩnh vực phát triển");
        return developmentalDomainRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Lấy tất cả lĩnh vực phát triển được sắp xếp theo tên
     * @return List các lĩnh vực phát triển sắp xếp theo tên A-Z
     */
    @Transactional(readOnly = true)
    public List<DevelopmentalDomainDto> getAllDomainsOrderByName() {
        log.info("Lấy tất cả lĩnh vực phát triển sắp xếp theo tên");
        return developmentalDomainRepository.findAllByOrderByNameAsc()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Lấy tất cả lĩnh vực phát triển được sắp xếp theo tên hiển thị
     * @return List các lĩnh vực phát triển sắp xếp theo tên hiển thị A-Z
     */
    @Transactional(readOnly = true)
    public List<DevelopmentalDomainDto> getAllDomainsOrderByDisplayedName() {
        log.info("Lấy tất cả lĩnh vực phát triển sắp xếp theo tên hiển thị");
        return developmentalDomainRepository.findAllByOrderByDisplayedNameAsc()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Lấy lĩnh vực phát triển theo ID
     * @param id ID của lĩnh vực phát triển
     * @return Optional chứa DevelopmentalDomainDto nếu tìm thấy
     */
    @Transactional(readOnly = true)
    public Optional<DevelopmentalDomainDto> getDomainById(UUID id) {
        log.info("Tìm lĩnh vực phát triển theo ID: {}", id);
        return developmentalDomainRepository.findById(id)
                .map(this::convertToDto);
    }
    
    /**
     * Lấy lĩnh vực phát triển theo tên
     * @param name tên lĩnh vực phát triển
     * @return Optional chứa DevelopmentalDomainDto nếu tìm thấy
     */
    @Transactional(readOnly = true)
    public Optional<DevelopmentalDomainDto> getDomainByName(String name) {
        log.info("Tìm lĩnh vực phát triển theo tên: {}", name);
        return developmentalDomainRepository.findByName(name)
                .map(this::convertToDto);
    }
    
    /**
     * Lấy lĩnh vực phát triển theo tên hiển thị
     * @param displayedName tên hiển thị của lĩnh vực phát triển
     * @return Optional chứa DevelopmentalDomainDto nếu tìm thấy
     */
    @Transactional(readOnly = true)
    public Optional<DevelopmentalDomainDto> getDomainByDisplayedName(String displayedName) {
        log.info("Tìm lĩnh vực phát triển theo tên hiển thị: {}", displayedName);
        return developmentalDomainRepository.findByDisplayedName(displayedName)
                .map(this::convertToDto);
    }
    
    /**
     * Lấy danh sách lĩnh vực phát triển theo category
     * @param category loại lĩnh vực phát triển (String)
     * @return List các lĩnh vực phát triển thuộc category
     */
    @Transactional(readOnly = true)
    public List<DevelopmentalDomainDto> getDomainsByCategory(String category) {
        log.info("Lấy lĩnh vực phát triển theo category: {}", category);
        return developmentalDomainRepository.findByCategoryOrderByNameAsc(category)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Lấy danh sách lĩnh vực phát triển theo category (sắp xếp theo tên hiển thị)
     * @param category loại lĩnh vực phát triển (String)
     * @return List các lĩnh vực phát triển thuộc category
     */
    @Transactional(readOnly = true)
    public List<DevelopmentalDomainDto> getDomainsByCategoryOrderByDisplayedName(String category) {
        log.info("Lấy lĩnh vực phát triển theo category (sắp xếp theo tên hiển thị): {}", category);
        return developmentalDomainRepository.findByCategoryOrderByDisplayedNameAsc(category)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Tìm kiếm lĩnh vực phát triển theo tên (contains)
     * @param name tên lĩnh vực cần tìm (có thể là một phần)
     * @return List các lĩnh vực có tên chứa từ khóa
     */
    @Transactional(readOnly = true)
    public List<DevelopmentalDomainDto> searchDomainsByName(String name) {
        log.info("Tìm kiếm lĩnh vực phát triển theo tên: {}", name);
        return developmentalDomainRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Tìm kiếm lĩnh vực phát triển theo tên hiển thị (contains)
     * @param displayedName tên hiển thị cần tìm (có thể là một phần)
     * @return List các lĩnh vực có tên hiển thị chứa từ khóa
     */
    @Transactional(readOnly = true)
    public List<DevelopmentalDomainDto> searchDomainsByDisplayedName(String displayedName) {
        log.info("Tìm kiếm lĩnh vực phát triển theo tên hiển thị: {}", displayedName);
        return developmentalDomainRepository.findByDisplayedNameContainingIgnoreCase(displayedName)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Tìm kiếm lĩnh vực phát triển theo từ khóa (tên hoặc mô tả)
     * @param keyword từ khóa cần tìm
     * @return List các lĩnh vực có tên hoặc mô tả chứa từ khóa
     */
    @Transactional(readOnly = true)
    public List<DevelopmentalDomainDto> searchDomainsByKeyword(String keyword) {
        log.info("Tìm kiếm lĩnh vực phát triển theo từ khóa: {}", keyword);
        return developmentalDomainRepository.searchByKeyword(keyword)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Tìm kiếm lĩnh vực phát triển theo từ khóa mở rộng (tên, tên hiển thị hoặc mô tả)
     * @param keyword từ khóa cần tìm
     * @return List các lĩnh vực có tên, tên hiển thị hoặc mô tả chứa từ khóa
     */
    @Transactional(readOnly = true)
    public List<DevelopmentalDomainDto> searchDomainsByKeywordExtended(String keyword) {
        log.info("Tìm kiếm lĩnh vực phát triển theo từ khóa mở rộng: {}", keyword);
        return developmentalDomainRepository.searchByKeywordExtended(keyword)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Lấy danh sách tất cả categories đang được sử dụng
     * @return List các category đang có dữ liệu (String)
     */
    @Transactional(readOnly = true)
    public List<String> getDistinctCategories() {
        log.info("Lấy danh sách tất cả categories");
        return developmentalDomainRepository.findDistinctCategories();
    }
    
    /**
     * Đếm số lượng lĩnh vực phát triển theo category
     * @param category loại lĩnh vực (String)
     * @return số lượng lĩnh vực thuộc category đó
     */
    @Transactional(readOnly = true)
    public long countDomainsByCategory(String category) {
        log.info("Đếm số lượng lĩnh vực phát triển theo category: {}", category);
        return developmentalDomainRepository.countByCategory(category);
    }
    
    /**
     * Tạo lĩnh vực phát triển mới
     * @param domainDto thông tin lĩnh vực phát triển cần tạo
     * @return DevelopmentalDomainDto đã tạo
     */
    public DevelopmentalDomainDto createDomain(DevelopmentalDomainDto domainDto) {
        log.info("Tạo lĩnh vực phát triển mới: {}", domainDto.getName());
        
        // Kiểm tra tên lĩnh vực đã tồn tại chưa
        if (developmentalDomainRepository.existsByName(domainDto.getName())) {
            throw new IllegalArgumentException("Lĩnh vực phát triển với tên này đã tồn tại: " + domainDto.getName());
        }
        
        // Kiểm tra tên hiển thị đã tồn tại chưa
        String displayedNameStr = convertObjectToString(domainDto.getDisplayedName());
        if (developmentalDomainRepository.existsByDisplayedName(displayedNameStr)) {
            throw new IllegalArgumentException("Lĩnh vực phát triển với tên hiển thị này đã tồn tại: " + displayedNameStr);
        }
        
        DevelopmentalDomain entity = convertToEntity(domainDto);
        DevelopmentalDomain savedEntity = developmentalDomainRepository.save(entity);
        log.info("Đã tạo lĩnh vực phát triển thành công với ID: {}", savedEntity.getId());
        
        return convertToDto(savedEntity);
    }
    
    /**
     * Cập nhật lĩnh vực phát triển
     * @param id ID của lĩnh vực phát triển cần cập nhật
     * @param domainDto thông tin mới
     * @return DevelopmentalDomainDto đã cập nhật
     */
    public DevelopmentalDomainDto updateDomain(UUID id, DevelopmentalDomainDto domainDto) {
        log.info("Cập nhật lĩnh vực phát triển với ID: {}", id);
        
        DevelopmentalDomain existingDomain = developmentalDomainRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy lĩnh vực phát triển với ID: " + id));
        
        // Kiểm tra tên mới có trùng với lĩnh vực khác không (trừ chính nó)
        Optional<DevelopmentalDomain> duplicateName = developmentalDomainRepository.findByName(domainDto.getName());
        if (duplicateName.isPresent() && !duplicateName.get().getId().equals(id)) {
            throw new IllegalArgumentException("Lĩnh vực phát triển với tên này đã tồn tại: " + domainDto.getName());
        }
        
        // Kiểm tra tên hiển thị mới có trùng với lĩnh vực khác không (trừ chính nó)
        String displayedNameStr = convertObjectToString(domainDto.getDisplayedName());
        Optional<DevelopmentalDomain> duplicateDisplayedName = developmentalDomainRepository.findByDisplayedName(displayedNameStr);
        if (duplicateDisplayedName.isPresent() && !duplicateDisplayedName.get().getId().equals(id)) {
            throw new IllegalArgumentException("Lĩnh vực phát triển với tên hiển thị này đã tồn tại: " + displayedNameStr);
        }
        
        // Cập nhật các trường bằng cách chuyển đổi DTO sang entity
        DevelopmentalDomain updatedDomain = convertToEntity(domainDto);
        existingDomain.setName(updatedDomain.getName());
        existingDomain.setDisplayedName(updatedDomain.getDisplayedName());
        existingDomain.setDescription(updatedDomain.getDescription());
        existingDomain.setCategory(updatedDomain.getCategory());
        
        DevelopmentalDomain updatedEntity = developmentalDomainRepository.save(existingDomain);
        log.info("Đã cập nhật lĩnh vực phát triển thành công");
        
        return convertToDto(updatedEntity);
    }
    
    /**
     * Xóa lĩnh vực phát triển
     * @param id ID của lĩnh vực phát triển cần xóa
     */
    public void deleteDomain(UUID id) {
        log.info("Xóa lĩnh vực phát triển với ID: {}", id);
        
        if (!developmentalDomainRepository.existsById(id)) {
            throw new IllegalArgumentException("Không tìm thấy lĩnh vực phát triển với ID: " + id);
        }
        
        developmentalDomainRepository.deleteById(id);
        log.info("Đã xóa lĩnh vực phát triển thành công");
    }
    
    /**
     * Kiểm tra xem lĩnh vực phát triển có tồn tại không
     * @param id ID của lĩnh vực phát triển
     * @return true nếu tồn tại, false nếu không
     */
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return developmentalDomainRepository.existsById(id);
    }
    
    /**
     * Kiểm tra xem tên lĩnh vực phát triển có tồn tại không
     * @param name tên lĩnh vực phát triển
     * @return true nếu tồn tại, false nếu không
     */
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return developmentalDomainRepository.existsByName(name);
    }
    
    /**
     * Kiểm tra xem tên hiển thị lĩnh vực phát triển có tồn tại không
     * @param displayedName tên hiển thị lĩnh vực phát triển
     * @return true nếu tồn tại, false nếu không
     */
    @Transactional(readOnly = true)
    public boolean existsByDisplayedName(String displayedName) {
        return developmentalDomainRepository.existsByDisplayedName(displayedName);
    }
}

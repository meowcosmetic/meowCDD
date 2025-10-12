package com.meowcdd.service;

import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.dto.RoleDto;
import com.meowcdd.entity.neon.Role;
import com.meowcdd.repository.neon.RoleNeonRepository;
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
public class RoleNeonService {

    private final RoleNeonRepository repository;

    public RoleDto create(RoleDto dto) {
        Role entity = convertToEntity(dto);
        Role saved = repository.save(entity);
        return convertToDto(saved);
    }

    public RoleDto update(UUID roleId, RoleDto dto) {
        Role existing = repository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleId));
        
        if (dto.getRoleName() != null) {
            existing.setRoleName(dto.getRoleName());
        }
        if (dto.getDescription() != null) {
            existing.setDescription(dto.getDescription());
        }
        
        Role saved = repository.save(existing);
        return convertToDto(saved);
    }

    @Transactional(readOnly = true)
    public RoleDto getById(UUID roleId) {
        Role entity = repository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleId));
        return convertToDto(entity);
    }

    public void delete(UUID roleId) {
        if (!repository.existsById(roleId)) {
            throw new EntityNotFoundException("Role not found: " + roleId);
        }
        repository.deleteById(roleId);
    }

    @Transactional(readOnly = true)
    public PageResponseDto<RoleDto> getAll(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Role> p = repository.findAll(pageable);
        return PageResponseDto.<RoleDto>builder()
                .content(p.getContent().stream().map(this::convertToDto).toList())
                .pageNumber(p.getNumber())
                .pageSize(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .isLast(p.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public PageResponseDto<RoleDto> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Role> p = repository.search(keyword, pageable);
        return PageResponseDto.<RoleDto>builder()
                .content(p.getContent().stream().map(this::convertToDto).toList())
                .pageNumber(p.getNumber())
                .pageSize(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .isLast(p.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public List<RoleDto> getByRoleName(String roleName) {
        return repository.findByRoleNameContainingIgnoreCase(roleName)
                .stream().map(this::convertToDto).toList();
    }

    private Role convertToEntity(RoleDto dto) {
        return Role.builder()
                .roleId(dto.getRoleId())
                .roleName(dto.getRoleName())
                .description(dto.getDescription())
                .build();
    }

    private RoleDto convertToDto(Role entity) {
        return RoleDto.builder()
                .roleId(entity.getRoleId())
                .roleName(entity.getRoleName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

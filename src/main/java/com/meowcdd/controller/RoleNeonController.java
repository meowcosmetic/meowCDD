package com.meowcdd.controller;

import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.dto.RoleDto;
import com.meowcdd.service.RoleNeonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/neon/roles")
@RequiredArgsConstructor
@Slf4j
public class RoleNeonController {

    private final RoleNeonService service;

    @PostMapping
    public ResponseEntity<RoleDto> create(@RequestBody RoleDto dto) {
        log.info("Creating role: {}", dto.getRoleName());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<RoleDto> update(@PathVariable UUID roleId, @RequestBody RoleDto dto) {
        log.info("Updating role id: {}", roleId);
        return ResponseEntity.ok(service.update(roleId, dto));
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDto> getById(@PathVariable UUID roleId) {
        return ResponseEntity.ok(service.getById(roleId));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> delete(@PathVariable UUID roleId) {
        service.delete(roleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<RoleDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "roleName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(service.getAll(page, size, sortBy, sortDir));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponseDto<RoleDto>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.search(keyword, page, size));
    }

    @GetMapping("/by-name")
    public ResponseEntity<List<RoleDto>> getByRoleName(@RequestParam String roleName) {
        return ResponseEntity.ok(service.getByRoleName(roleName));
    }
}

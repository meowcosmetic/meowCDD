package com.meowcdd.controller;

import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.dto.RoleDetailDto;
import com.meowcdd.service.RoleDetailNeonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/neon/role-details")
@RequiredArgsConstructor
@Slf4j
public class RoleDetailNeonController {

    private final RoleDetailNeonService service;

    @PostMapping
    public ResponseEntity<RoleDetailDto> create(@RequestBody RoleDetailDto dto) {
        log.info("Creating role detail for role: {}", dto.getRoleId());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{detailId}")
    public ResponseEntity<RoleDetailDto> update(@PathVariable UUID detailId, @RequestBody RoleDetailDto dto) {
        log.info("Updating role detail id: {}", detailId);
        return ResponseEntity.ok(service.update(detailId, dto));
    }

    @GetMapping("/{detailId}")
    public ResponseEntity<RoleDetailDto> getById(@PathVariable UUID detailId) {
        return ResponseEntity.ok(service.getById(detailId));
    }

    @DeleteMapping("/{detailId}")
    public ResponseEntity<Void> delete(@PathVariable UUID detailId) {
        service.delete(detailId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<RoleDetailDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "detailId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(service.getAll(page, size, sortBy, sortDir));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponseDto<RoleDetailDto>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.search(keyword, page, size));
    }

    @GetMapping("/role/{roleId}")
    public ResponseEntity<List<RoleDetailDto>> getByRoleId(@PathVariable UUID roleId) {
        return ResponseEntity.ok(service.getByRoleId(roleId));
    }
}

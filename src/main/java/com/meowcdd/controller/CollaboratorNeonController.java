package com.meowcdd.controller;

import com.meowcdd.dto.CollaboratorDto;
import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.entity.neon.Collaborator;
import com.meowcdd.service.CollaboratorNeonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/neon/collaborators")
@RequiredArgsConstructor
@Slf4j
public class CollaboratorNeonController {

    private final CollaboratorNeonService service;

    @PostMapping
    public ResponseEntity<CollaboratorDto> create(@RequestBody CollaboratorDto dto) {
        log.info("Creating collaborator for user: {} and role: {}", dto.getUserId(), dto.getRoleId());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{collaboratorId}")
    public ResponseEntity<CollaboratorDto> update(@PathVariable UUID collaboratorId, @RequestBody CollaboratorDto dto) {
        log.info("Updating collaborator id: {}", collaboratorId);
        return ResponseEntity.ok(service.update(collaboratorId, dto));
    }

    @GetMapping("/{collaboratorId}")
    public ResponseEntity<CollaboratorDto> getById(@PathVariable UUID collaboratorId) {
        return ResponseEntity.ok(service.getById(collaboratorId));
    }

    @DeleteMapping("/{collaboratorId}")
    public ResponseEntity<Void> delete(@PathVariable UUID collaboratorId) {
        service.delete(collaboratorId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<CollaboratorDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        return ResponseEntity.ok(service.getAll(page, size, sortBy, sortDir));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponseDto<CollaboratorDto>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.search(keyword, page, size));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CollaboratorDto>> getByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(service.getByUserId(userId));
    }

    @GetMapping("/role/{roleId}")
    public ResponseEntity<List<CollaboratorDto>> getByRoleId(@PathVariable UUID roleId) {
        return ResponseEntity.ok(service.getByRoleId(roleId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<CollaboratorDto>> getByStatus(@PathVariable Collaborator.Status status) {
        return ResponseEntity.ok(service.getByStatus(status));
    }
}

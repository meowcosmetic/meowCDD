   package com.meowcdd.controller;

import com.meowcdd.dto.InterventionMethodDto;
import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.service.InterventionMethodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/neon/intervention-methods")
@RequiredArgsConstructor
@Slf4j
public class InterventionMethodController {

    private final InterventionMethodService service;

    @PostMapping
    public ResponseEntity<InterventionMethodDto> create(@RequestBody InterventionMethodDto dto) {
        log.info("Creating intervention method");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterventionMethodDto> update(@PathVariable Long id, @RequestBody InterventionMethodDto dto) {
        log.info("Updating intervention method id: {}", id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterventionMethodDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<InterventionMethodDto> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(service.getByCode(code));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/restore")
    public ResponseEntity<Void> restore(@PathVariable Long id) {
        service.restore(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<InterventionMethodDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(service.getAll(page, size, sortBy, sortDir));
    }

    @GetMapping("/all")
    public ResponseEntity<List<InterventionMethodDto>> getAllNonPaged() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<InterventionMethodDto>> findByGroupId(@PathVariable Long groupId) {
        return ResponseEntity.ok(service.findByGroupId(groupId));
    }

    @GetMapping("/group/{groupId}/paginated")
    public ResponseEntity<Map<String, Object>> findByGroupIdPaginated(
            @PathVariable Long groupId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<InterventionMethodDto> allMethods = service.findByGroupId(groupId);
            
            Map<String, Object> result = new HashMap<>();
            result.put("content", allMethods);
            result.put("pageNumber", page);
            result.put("pageSize", size);
            result.put("totalElements", allMethods.size());
            result.put("totalPages", 1);
            result.put("isLast", true);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error in paginated endpoint", e);
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResult);
        }
    }
}


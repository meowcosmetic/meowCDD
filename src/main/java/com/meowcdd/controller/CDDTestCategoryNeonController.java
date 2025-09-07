package com.meowcdd.controller;

import com.meowcdd.dto.CDDTestCategoryDto;
import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.service.CDDTestCategoryNeonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/neon/cdd-test-categories")
@RequiredArgsConstructor
@Slf4j
public class CDDTestCategoryNeonController {

    private final CDDTestCategoryNeonService service;

    @PostMapping
    public ResponseEntity<CDDTestCategoryDto> create(@RequestBody CDDTestCategoryDto dto) {
        log.info("Creating CDD test category");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CDDTestCategoryDto> update(@PathVariable Long id, @RequestBody CDDTestCategoryDto dto) {
        log.info("Updating CDD test category id: {}", id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CDDTestCategoryDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<CDDTestCategoryDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(service.getAll(page, size, sortBy, sortDir));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CDDTestCategoryDto>> getAllNonPaged() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponseDto<CDDTestCategoryDto>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.search(keyword, page, size));
    }
}



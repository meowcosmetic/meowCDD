package com.meowcdd.controller;

import com.meowcdd.dto.DevelopmentalItemCriteriaDto;
import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.service.DevelopmentalItemCriteriaNeonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/neon/developmental-item-criteria")
@RequiredArgsConstructor
@Slf4j
public class DevelopmentalItemCriteriaNeonController {

    private final DevelopmentalItemCriteriaNeonService service;

    @PostMapping
    public ResponseEntity<DevelopmentalItemCriteriaDto> create(@RequestBody DevelopmentalItemCriteriaDto dto) {
        log.info("Creating criteria for item: {}", dto.getItemId());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DevelopmentalItemCriteriaDto> update(@PathVariable Long id, @RequestBody DevelopmentalItemCriteriaDto dto) {
        log.info("Updating criteria id: {}", id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DevelopmentalItemCriteriaDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<DevelopmentalItemCriteriaDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(service.getAll(page, size, sortBy, sortDir));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponseDto<DevelopmentalItemCriteriaDto>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.search(keyword, page, size));
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<DevelopmentalItemCriteriaDto>> getByItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(service.getByItem(itemId));
    }
}



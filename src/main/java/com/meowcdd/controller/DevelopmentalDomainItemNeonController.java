package com.meowcdd.controller;

import com.meowcdd.dto.DevelopmentalDomainItemDto;
import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.service.DevelopmentalDomainItemNeonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/neon/developmental-domain-items")
@RequiredArgsConstructor
@Slf4j
public class DevelopmentalDomainItemNeonController {

    private final DevelopmentalDomainItemNeonService service;

    @PostMapping
    public ResponseEntity<DevelopmentalDomainItemDto> create(@RequestBody DevelopmentalDomainItemDto dto) {
        log.info("Creating domain item for domain: {}", dto.getDomainId());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DevelopmentalDomainItemDto> update(@PathVariable Long id, @RequestBody DevelopmentalDomainItemDto dto) {
        log.info("Updating domain item id: {}", id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DevelopmentalDomainItemDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<DevelopmentalDomainItemDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(service.getAll(page, size, sortBy, sortDir));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponseDto<DevelopmentalDomainItemDto>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.search(keyword, page, size));
    }

    @GetMapping("/domain/{domainId}")
    public ResponseEntity<List<DevelopmentalDomainItemDto>> getByDomain(@PathVariable UUID domainId) {
        return ResponseEntity.ok(service.getByDomain(domainId));
    }
}



package com.meowcdd.controller;

import com.meowcdd.dto.DevelopmentalProgramDto;
import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.service.DevelopmentalProgramNeonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/neon/developmental-programs")
@RequiredArgsConstructor
@Slf4j
public class DevelopmentalProgramNeonController {

    private final DevelopmentalProgramNeonService service;

    @PostMapping
    public ResponseEntity<DevelopmentalProgramDto> create(@RequestBody DevelopmentalProgramDto dto) {
        log.info("Creating developmental program: {}", dto.getCode());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DevelopmentalProgramDto> update(@PathVariable Long id, @RequestBody DevelopmentalProgramDto dto) {
        log.info("Updating developmental program id: {}", id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DevelopmentalProgramDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<DevelopmentalProgramDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(service.getAll(page, size, sortBy, sortDir));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponseDto<DevelopmentalProgramDto>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.search(keyword, page, size));
    }
}



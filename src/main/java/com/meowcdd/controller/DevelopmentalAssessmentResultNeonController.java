package com.meowcdd.controller;

import com.meowcdd.dto.DevelopmentalAssessmentResultDto;
import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.service.DevelopmentalAssessmentResultNeonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/neon/developmental-assessment-results")
@RequiredArgsConstructor
@Slf4j
public class DevelopmentalAssessmentResultNeonController {

    private final DevelopmentalAssessmentResultNeonService service;

    @PostMapping
    public ResponseEntity<DevelopmentalAssessmentResultDto> create(@RequestBody DevelopmentalAssessmentResultDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DevelopmentalAssessmentResultDto> update(@PathVariable Long id, @RequestBody DevelopmentalAssessmentResultDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DevelopmentalAssessmentResultDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<DevelopmentalAssessmentResultDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(service.getAll(page, size, sortBy, sortDir));
    }

    @GetMapping("/child/{childId}")
    public ResponseEntity<PageResponseDto<DevelopmentalAssessmentResultDto>> getByChild(
            @PathVariable Long childId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getByChild(childId, page, size));
    }
}



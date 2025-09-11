   package com.meowcdd.controller;

import com.meowcdd.dto.InterventionMethodDto;
import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.service.InterventionMethodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/search")
    public ResponseEntity<PageResponseDto<InterventionMethodDto>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.search(keyword, page, size));
    }

    @GetMapping("/age-range")
    public ResponseEntity<List<InterventionMethodDto>> findByAgeRange(
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge) {
        return ResponseEntity.ok(service.findByAgeRange(minAge, maxAge));
    }
}


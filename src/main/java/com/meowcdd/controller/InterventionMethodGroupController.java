package com.meowcdd.controller;

import com.meowcdd.entity.neon.InterventionMethodGroup;
import com.meowcdd.dto.InterventionMethodDto;
import org.springframework.data.domain.Page;
import com.meowcdd.service.InterventionMethodGroupService;
import com.meowcdd.service.InterventionMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/neon/intervention-method-groups")
@RequiredArgsConstructor
public class InterventionMethodGroupController {

    private final InterventionMethodGroupService service;

    // Groups
    @PostMapping
    public ResponseEntity<InterventionMethodGroup> createGroup(@RequestBody InterventionMethodGroup group) {
        return ResponseEntity.ok(service.createGroup(group));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<InterventionMethodGroup> updateGroup(@PathVariable Long id,
                                                               @RequestBody InterventionMethodGroup update) {
        return ResponseEntity.ok(service.updateGroup(id, update));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterventionMethodGroup> getGroup(@PathVariable Long id) {
        return ResponseEntity.ok(service.getGroup(id));
    }

    @GetMapping
    public ResponseEntity<Page<InterventionMethodGroup>> listGroups(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size,
                                                                    @RequestParam(defaultValue = "id") String sortBy,
                                                                    @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(service.listGroups(page, size, sortBy, sortDir));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        service.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
}
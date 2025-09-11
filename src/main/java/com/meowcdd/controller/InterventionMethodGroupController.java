package com.meowcdd.controller;

import com.meowcdd.entity.neon.InterventionMethodGroup;
import com.meowcdd.entity.neon.InterventionMethodGroupMember;
import org.springframework.data.domain.Page;
import com.meowcdd.service.InterventionMethodGroupService;
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

    // Members
    public record AddMemberRequest(Long methodId, Integer orderIndex, String notes) {}

    @PostMapping("/{groupId}/members")
    public ResponseEntity<InterventionMethodGroupMember> addMember(@PathVariable Long groupId,
                                                                   @RequestBody AddMemberRequest body) {
        return ResponseEntity.ok(service.addMember(groupId, body.methodId(), body.orderIndex(), body.notes()));
    }

    public record UpdateMemberRequest(Integer orderIndex, String notes) {}

    @PatchMapping("/members/{memberId}")
    public ResponseEntity<InterventionMethodGroupMember> updateMember(@PathVariable Long memberId,
                                                                      @RequestBody UpdateMemberRequest body) {
        return ResponseEntity.ok(service.updateMember(memberId, body.orderIndex(), body.notes()));
    }

    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<Void> removeMember(@PathVariable Long memberId) {
        service.removeMember(memberId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{groupId}/members")
    public ResponseEntity<Page<InterventionMethodGroupMember>> listMembers(@PathVariable Long groupId,
                                                                           @RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size,
                                                                           @RequestParam(defaultValue = "orderIndex") String sortBy,
                                                                           @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(service.listMembers(groupId, page, size, sortBy, sortDir));
    }
}



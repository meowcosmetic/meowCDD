package com.meowcdd.controller;

import com.meowcdd.entity.supabase.ChildSupabase;
import com.meowcdd.service.ChildSupabaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/supabase/children")
@RequiredArgsConstructor
@Slf4j
public class ChildSupabaseController {

    private final ChildSupabaseService childService;

    /**
     * Tạo trẻ mới
     */
    @PostMapping
    public ResponseEntity<ChildSupabase> createChild(@RequestBody ChildSupabase child) {
        log.info("Creating new child: {}", child.getFullName());
        ChildSupabase createdChild = childService.createChild(child);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdChild);
    }

    /**
     * Cập nhật thông tin trẻ
     */
    @PutMapping("/{id}")
    public ResponseEntity<ChildSupabase> updateChild(@PathVariable Long id, @RequestBody ChildSupabase child) {
        log.info("Updating child with id: {}", id);
        try {
            ChildSupabase updatedChild = childService.updateChild(id, child);
            return ResponseEntity.ok(updatedChild);
        } catch (IllegalArgumentException e) {
            log.error("Error updating child: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Lấy trẻ theo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ChildSupabase> getChildById(@PathVariable Long id) {
        log.info("Getting child with id: {}", id);
        Optional<ChildSupabase> child = childService.getChildById(id);
        return child.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lấy trẻ theo external ID
     */
    @GetMapping("/external/{externalId}")
    public ResponseEntity<ChildSupabase> getChildByExternalId(@PathVariable String externalId) {
        log.info("Getting child with external id: {}", externalId);
        Optional<ChildSupabase> child = childService.getChildByExternalId(externalId);
        return child.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lấy tất cả trẻ
     */
    @GetMapping
    public ResponseEntity<List<ChildSupabase>> getAllChildren() {
        log.info("Getting all children");
        List<ChildSupabase> children = childService.getAllChildren();
        return ResponseEntity.ok(children);
    }

    /**
     * Tìm trẻ theo tên
     */
    @GetMapping("/search")
    public ResponseEntity<List<ChildSupabase>> searchChildrenByName(@RequestParam String name) {
        log.info("Searching children by name: {}", name);
        List<ChildSupabase> children = childService.findChildrenByName(name);
        return ResponseEntity.ok(children);
    }

    /**
     * Tìm trẻ theo giới tính
     */
    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<ChildSupabase>> getChildrenByGender(@PathVariable ChildSupabase.Gender gender) {
        log.info("Getting children by gender: {}", gender);
        List<ChildSupabase> children = childService.findChildrenByGender(gender);
        return ResponseEntity.ok(children);
    }

    /**
     * Tìm trẻ theo độ tuổi
     */
    @GetMapping("/age")
    public ResponseEntity<List<ChildSupabase>> getChildrenByAgeRange(
            @RequestParam Integer minAge, 
            @RequestParam Integer maxAge) {
        log.info("Getting children by age range: {} - {}", minAge, maxAge);
        List<ChildSupabase> children = childService.findChildrenByAgeRange(minAge, maxAge);
        return ResponseEntity.ok(children);
    }

    /**
     * Tìm trẻ sinh non
     */
    @GetMapping("/premature")
    public ResponseEntity<List<ChildSupabase>> getPrematureChildren() {
        log.info("Getting premature children");
        List<ChildSupabase> children = childService.findPrematureChildren();
        return ResponseEntity.ok(children);
    }

    /**
     * Tìm trẻ có rối loạn phát triển
     */
    @GetMapping("/developmental-disorder/{status}")
    public ResponseEntity<List<ChildSupabase>> getChildrenWithDevelopmentalDisorder(
            @PathVariable ChildSupabase.DevelopmentalDisorderStatus status) {
        log.info("Getting children with developmental disorder: {}", status);
        List<ChildSupabase> children = childService.findChildrenWithDevelopmentalDisorder(status);
        return ResponseEntity.ok(children);
    }

    /**
     * Tìm trẻ có can thiệp sớm
     */
    @GetMapping("/early-intervention")
    public ResponseEntity<List<ChildSupabase>> getChildrenWithEarlyIntervention() {
        log.info("Getting children with early intervention");
        List<ChildSupabase> children = childService.findChildrenWithEarlyIntervention();
        return ResponseEntity.ok(children);
    }

    /**
     * Xóa trẻ
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChild(@PathVariable Long id) {
        log.info("Deleting child with id: {}", id);
        childService.deleteChild(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Thống kê theo giới tính
     */
    @GetMapping("/stats/gender/{gender}")
    public ResponseEntity<Long> countByGender(@PathVariable ChildSupabase.Gender gender) {
        log.info("Counting children by gender: {}", gender);
        long count = childService.countByGender(gender);
        return ResponseEntity.ok(count);
    }

    /**
     * Thống kê theo trạng thái
     */
    @GetMapping("/stats/status/{status}")
    public ResponseEntity<Long> countByStatus(@PathVariable ChildSupabase.Status status) {
        log.info("Counting children by status: {}", status);
        long count = childService.countByStatus(status);
        return ResponseEntity.ok(count);
    }

    /**
     * Tìm trẻ theo khoảng ngày sinh
     */
    @GetMapping("/birth-date")
    public ResponseEntity<List<ChildSupabase>> getChildrenByBirthDateRange(
            @RequestParam String startDate, 
            @RequestParam String endDate) {
        log.info("Getting children by birth date range: {} - {}", startDate, endDate);
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<ChildSupabase> children = childService.findChildrenByBirthDateRange(start, end);
        return ResponseEntity.ok(children);
    }

    /**
     * Tìm trẻ theo cân nặng khi sinh
     */
    @GetMapping("/birth-weight")
    public ResponseEntity<List<ChildSupabase>> getChildrenByBirthWeightRange(
            @RequestParam Integer minWeight, 
            @RequestParam Integer maxWeight) {
        log.info("Getting children by birth weight range: {} - {}", minWeight, maxWeight);
        List<ChildSupabase> children = childService.findChildrenByBirthWeightRange(minWeight, maxWeight);
        return ResponseEntity.ok(children);
    }
}

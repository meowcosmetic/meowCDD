package com.meowcdd.controller;

import com.meowcdd.entity.neon.ChildNeon;
import com.meowcdd.service.ChildNeonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping({"/neon/children", "/supabase/children"})
@RequiredArgsConstructor
@Slf4j
public class ChildNeonController {

    private final ChildNeonService childNeonService;

    @GetMapping("/paginated")
    public ResponseEntity<Page<ChildNeon>> getAllChildrenWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting all children with pagination (page: {}, size: {})", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<ChildNeon> children = childNeonService.getAllChildrenWithPagination(pageable);
        return ResponseEntity.ok(children);
    }

    @GetMapping
    public ResponseEntity<List<ChildNeon>> getAllChildren() {
        log.info("Getting all children");
        List<ChildNeon> children = childNeonService.getAllChildren();
        return ResponseEntity.ok(children);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChildNeon> getChildById(@PathVariable Long id) {
        log.info("Getting child by ID: {}", id);
        return childNeonService.getChildById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<ChildNeon>> getChildrenByParentId(@PathVariable String parentId) {
        log.info("Getting children by parent ID: {}", parentId);
        List<ChildNeon> children = childNeonService.getChildrenByParentId(parentId);
        return ResponseEntity.ok(children);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ChildNeon>> getChildrenByStatus(@PathVariable ChildNeon.Status status) {
        log.info("Getting children by status: {}", status);
        List<ChildNeon> children = childNeonService.getChildrenByStatus(status);
        return ResponseEntity.ok(children);
    }

    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<ChildNeon>> getChildrenByGender(@PathVariable ChildNeon.Gender gender) {
        log.info("Getting children by gender: {}", gender);
        List<ChildNeon> children = childNeonService.getChildrenByGender(gender);
        return ResponseEntity.ok(children);
    }

    @GetMapping("/premature/{isPremature}")
    public ResponseEntity<List<ChildNeon>> getChildrenByIsPremature(@PathVariable Boolean isPremature) {
        log.info("Getting children by is premature: {}", isPremature);
        List<ChildNeon> children = childNeonService.getChildrenByIsPremature(isPremature);
        return ResponseEntity.ok(children);
    }

    @GetMapping("/diagnosis/{diagnosis}")
    public ResponseEntity<List<ChildNeon>> getChildrenByDevelopmentalDisorderDiagnosis(
            @PathVariable ChildNeon.DevelopmentalDisorderStatus diagnosis) {
        log.info("Getting children by developmental disorder diagnosis: {}", diagnosis);
        List<ChildNeon> children = childNeonService.getChildrenByDevelopmentalDisorderDiagnosis(diagnosis);
        return ResponseEntity.ok(children);
    }

    @GetMapping("/intervention/{hasEarlyIntervention}")
    public ResponseEntity<List<ChildNeon>> getChildrenByHasEarlyIntervention(@PathVariable Boolean hasEarlyIntervention) {
        log.info("Getting children by has early intervention: {}", hasEarlyIntervention);
        List<ChildNeon> children = childNeonService.getChildrenByHasEarlyIntervention(hasEarlyIntervention);
        return ResponseEntity.ok(children);
    }

    @GetMapping("/language/{language}")
    public ResponseEntity<List<ChildNeon>> getChildrenByPrimaryLanguage(@PathVariable String language) {
        log.info("Getting children by primary language: {}", language);
        List<ChildNeon> children = childNeonService.getChildrenByPrimaryLanguage(language);
        return ResponseEntity.ok(children);
    }

    @GetMapping("/family-issues/{issue}")
    public ResponseEntity<List<ChildNeon>> getChildrenByFamilyDevelopmentalIssues(
            @PathVariable String issue) {
        log.info("Getting children by family developmental issues: {}", issue);
        List<ChildNeon> children = childNeonService.getChildrenByFamilyDevelopmentalIssues(issue);
        return ResponseEntity.ok(children);
    }

    @GetMapping("/birth-date")
    public ResponseEntity<List<ChildNeon>> getChildrenByDateOfBirthBetween(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        log.info("Getting children by birth date between: {} and {}", startDate, endDate);
        List<ChildNeon> children = childNeonService.getChildrenByDateOfBirthBetween(startDate, endDate);
        return ResponseEntity.ok(children);
    }

    @GetMapping("/age-range")
    public ResponseEntity<List<ChildNeon>> getChildrenByAgeRange(
            @RequestParam Integer minAge,
            @RequestParam Integer maxAge) {
        log.info("Getting children by age range: {} to {} months", minAge, maxAge);
        List<ChildNeon> children = childNeonService.getChildrenByAgeRange(minAge, maxAge);
        return ResponseEntity.ok(children);
    }

    @PostMapping
    public ResponseEntity<ChildNeon> createChild(@RequestBody ChildNeon child) {
        log.info("Creating new child: {}", child.getFullName());
        ChildNeon createdChild = childNeonService.createChild(child);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdChild);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChildNeon> updateChild(@PathVariable Long id, @RequestBody ChildNeon child) {
        log.info("Updating child with ID: {}", id);
        return childNeonService.updateChild(id, child)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChild(@PathVariable Long id) {
        log.info("Deleting child with ID: {}", id);
        if (childNeonService.deleteChild(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ChildNeon>> searchChildren(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Searching children with keyword: {}", keyword);
        Pageable pageable = PageRequest.of(page, size);
        Page<ChildNeon> children = childNeonService.searchChildren(keyword, pageable);
        return ResponseEntity.ok(children);
    }

    @GetMapping("/stats/status")
    public ResponseEntity<Long> getChildCountByStatus(@RequestParam ChildNeon.Status status) {
        log.info("Getting child count by status: {}", status);
        long count = childNeonService.getChildCountByStatus(status);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/stats/gender")
    public ResponseEntity<Long> getChildCountByGender(@RequestParam ChildNeon.Gender gender) {
        log.info("Getting child count by gender: {}", gender);
        long count = childNeonService.getChildCountByGender(gender);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/stats/premature")
    public ResponseEntity<Long> getChildCountByIsPremature(@RequestParam Boolean isPremature) {
        log.info("Getting child count by is premature: {}", isPremature);
        long count = childNeonService.getChildCountByIsPremature(isPremature);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/stats/diagnosis")
    public ResponseEntity<Long> getChildCountByDevelopmentalDisorderDiagnosis(
            @RequestParam ChildNeon.DevelopmentalDisorderStatus diagnosis) {
        log.info("Getting child count by developmental disorder diagnosis: {}", diagnosis);
        long count = childNeonService.getChildCountByDevelopmentalDisorderDiagnosis(diagnosis);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/stats/intervention")
    public ResponseEntity<Long> getChildCountByHasEarlyIntervention(@RequestParam Boolean hasEarlyIntervention) {
        log.info("Getting child count by has early intervention: {}", hasEarlyIntervention);
        long count = childNeonService.getChildCountByHasEarlyIntervention(hasEarlyIntervention);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/stats/language")
    public ResponseEntity<List<Object[]>> getChildCountByPrimaryLanguage() {
        log.info("Getting child count by primary language");
        List<Object[]> stats = childNeonService.getChildCountByPrimaryLanguage();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/stats/family-issues")
    public ResponseEntity<List<Object[]>> getChildCountByFamilyDevelopmentalIssues() {
        log.info("Getting child count by family developmental issues");
        List<Object[]> stats = childNeonService.getChildCountByFamilyDevelopmentalIssues();
        return ResponseEntity.ok(stats);
    }
}

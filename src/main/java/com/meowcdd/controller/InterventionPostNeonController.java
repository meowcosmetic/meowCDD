package com.meowcdd.controller;

import com.meowcdd.dto.InterventionPostDto;
import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.entity.neon.InterventionPost;
import com.meowcdd.service.InterventionPostNeonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/neon/intervention-posts")
@RequiredArgsConstructor
@Slf4j
public class InterventionPostNeonController {

    private final InterventionPostNeonService service;

    @GetMapping
    public ResponseEntity<List<InterventionPostDto>> getAll() {
        try {
            List<InterventionPostDto> posts = service.getAll();
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            log.error("Error getting all intervention posts", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/paginated")
    public ResponseEntity<PageResponseDto<InterventionPostDto>> getAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            PageResponseDto<InterventionPostDto> response = service.getAllPaginated(page, size);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting paginated intervention posts", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterventionPostDto> getById(@PathVariable Long id) {
        try {
            InterventionPostDto post = service.getById(id);
            return ResponseEntity.ok(post);
        } catch (RuntimeException e) {
            log.error("InterventionPost not found with id: {}", id, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error getting intervention post with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponseDto<InterventionPostDto>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            PageResponseDto<InterventionPostDto> response = service.search(keyword, page, size);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error searching intervention posts", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/criteria/{criteriaId}")
    public ResponseEntity<List<InterventionPostDto>> getByCriteria(@PathVariable Long criteriaId) {
        try {
            List<InterventionPostDto> posts = service.getByCriteria(criteriaId);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            log.error("Error getting intervention posts by criteria: {}", criteriaId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/criteria/{criteriaId}/ordered")
    public ResponseEntity<List<InterventionPostDto>> getByCriteriaOrdered(@PathVariable Long criteriaId) {
        try {
            List<InterventionPostDto> posts = service.getByCriteriaOrderByDifficulty(criteriaId);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            log.error("Error getting ordered intervention posts by criteria: {}", criteriaId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/program/{programId}")
    public ResponseEntity<List<InterventionPostDto>> getByProgram(@PathVariable Long programId) {
        try {
            List<InterventionPostDto> posts = service.getByProgram(programId);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            log.error("Error getting intervention posts by program: {}", programId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/type/{postType}")
    public ResponseEntity<List<InterventionPostDto>> getByPostType(@PathVariable InterventionPost.PostType postType) {
        try {
            List<InterventionPostDto> posts = service.getByPostType(postType);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            log.error("Error getting intervention posts by type: {}", postType, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/age/{age}")
    public ResponseEntity<List<InterventionPostDto>> getByTargetAge(@PathVariable Integer age) {
        try {
            List<InterventionPostDto> posts = service.getByTargetAge(age);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            log.error("Error getting intervention posts by age: {}", age, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<PageResponseDto<InterventionPostDto>> getByFilters(
            @RequestParam(required = false) Long criteriaId,
            @RequestParam(required = false) InterventionPost.PostType postType,
            @RequestParam(required = false) Boolean isPublished,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            PageResponseDto<InterventionPostDto> response = service.getByFilters(
                    criteriaId, postType, isPublished, page, size);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error filtering intervention posts", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<InterventionPostDto> create(@RequestBody InterventionPostDto postDto) {
        try {
            InterventionPostDto createdPost = service.create(postDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        } catch (Exception e) {
            log.error("Error creating intervention post", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterventionPostDto> update(@PathVariable Long id, @RequestBody InterventionPostDto postDto) {
        try {
            InterventionPostDto updatedPost = service.update(id, postDto);
            return ResponseEntity.ok(updatedPost);
        } catch (RuntimeException e) {
            log.error("InterventionPost not found with id: {}", id, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error updating intervention post with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("InterventionPost not found with id: {}", id, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting intervention post with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<InterventionPostDto> publish(@PathVariable Long id) {
        try {
            InterventionPostDto publishedPost = service.publish(id);
            return ResponseEntity.ok(publishedPost);
        } catch (RuntimeException e) {
            log.error("InterventionPost not found with id: {}", id, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error publishing intervention post with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{id}/unpublish")
    public ResponseEntity<InterventionPostDto> unpublish(@PathVariable Long id) {
        try {
            InterventionPostDto unpublishedPost = service.unpublish(id);
            return ResponseEntity.ok(unpublishedPost);
        } catch (RuntimeException e) {
            log.error("InterventionPost not found with id: {}", id, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error unpublishing intervention post with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

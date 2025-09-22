package com.meowcdd.controller;

import com.meowcdd.dto.PageResponseDto;
import com.meowcdd.dto.YouTubeVideoDto;
import com.meowcdd.service.YouTubeVideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/neon/youtube-videos")
@RequiredArgsConstructor
@Slf4j
public class YouTubeVideoController {

    private final YouTubeVideoService videoService;

    @PostMapping
    public ResponseEntity<YouTubeVideoDto> create(@RequestBody YouTubeVideoDto dto) {
        log.info("Creating YouTube video: {}", dto.getUrl());
        YouTubeVideoDto created = videoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<YouTubeVideoDto> update(@PathVariable Long id, @RequestBody YouTubeVideoDto dto) {
        log.info("Updating YouTube video id: {}", id);
        YouTubeVideoDto updated = videoService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<YouTubeVideoDto> getById(@PathVariable Long id) {
        log.info("Getting YouTube video id: {}", id);
        return ResponseEntity.ok(videoService.getById(id));
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<YouTubeVideoDto>> getAll(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size) {
        log.info("Getting YouTube videos page: {}, size: {}", page, size);
        return ResponseEntity.ok(videoService.getAll(page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting YouTube video id: {}", id);
        videoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/format/{formatId}")
    public ResponseEntity<List<YouTubeVideoDto>> getByFormat(@PathVariable Long formatId) {
        log.info("Getting YouTube videos by format: {}", formatId);
        return ResponseEntity.ok(videoService.getByFormat(formatId));
    }

    @GetMapping("/domain/{domainId}")
    public ResponseEntity<List<YouTubeVideoDto>> getByDomain(@PathVariable UUID domainId) {
        log.info("Getting YouTube videos by developmental domain: {}", domainId);
        return ResponseEntity.ok(videoService.getByDomain(domainId));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponseDto<YouTubeVideoDto>> search(@RequestParam String keyword,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size) {
        log.info("Searching YouTube videos by keyword: {}", keyword);
        return ResponseEntity.ok(videoService.searchByKeyword(keyword, page, size));
    }
}

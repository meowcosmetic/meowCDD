package com.meowcdd.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AuthController {

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getAuthStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> response = new HashMap<>();
        
        if (authentication != null && authentication.isAuthenticated() && 
            !"anonymousUser".equals(authentication.getName())) {
            response.put("authenticated", true);
            response.put("username", authentication.getName());
            response.put("authorities", authentication.getAuthorities());
        } else {
            response.put("authenticated", false);
        }
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> response = new HashMap<>();
        
        if (authentication != null && authentication.isAuthenticated()) {
            SecurityContextHolder.clearContext();
            response.put("success", true);
            response.put("message", "Logout successful");
            response.put("user", authentication.getName());
            log.info("User {} logged out successfully", authentication.getName());
        } else {
            response.put("success", false);
            response.put("message", "No active session found");
        }
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> response = new HashMap<>();
        
        if (authentication != null && authentication.isAuthenticated() && 
            !"anonymousUser".equals(authentication.getName())) {
            response.put("username", authentication.getName());
            response.put("authorities", authentication.getAuthorities());
            response.put("authenticated", true);
        } else {
            response.put("authenticated", false);
            response.put("message", "No authenticated user found");
        }
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/check")
    public ResponseEntity<Map<String, Object>> checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> response = new HashMap<>();
        
        boolean isAuthenticated = authentication != null && 
                                 authentication.isAuthenticated() && 
                                 !"anonymousUser".equals(authentication.getName());
        
        response.put("authenticated", isAuthenticated);
        
        if (isAuthenticated) {
            response.put("username", authentication.getName());
            response.put("authorities", authentication.getAuthorities());
        }
        
        return ResponseEntity.ok(response);
    }
}

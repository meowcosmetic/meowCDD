package com.meowcdd.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParentDto {
    
    private Long id;
    
    @NotBlank(message = "Full name is required")
    private String fullName;
    
    private String phoneNumber;
    
    @Email(message = "Invalid email format")
    private String email;
    
    private String address;
    
    private LocalDateTime dateOfBirth;
    
    @NotNull(message = "Gender is required")
    private String gender;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private List<ChildDto> children;
}

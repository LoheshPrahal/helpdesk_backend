package com.springboot.helpdesk.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CustomerDto(
        @NotBlank(message = "Name is required")
        @NotNull(message = "Name is required")
        String name,
        @NotNull(message = "City is required")
        @NotBlank(message = "City is required")
        String city,
        @NotBlank(message = "Email is required")
        @NotNull(message = "Email is required")
        @Email(message = "Email seems invalid")
        String email,
        @NotBlank(message = "Username is required")
        @NotNull(message = "Username is required")
        @Size(min = 3, max = 15)
        String username,
        @NotNull(message = "Password is required")
        @NotBlank(message = "Password is required")
        String password
) {
}

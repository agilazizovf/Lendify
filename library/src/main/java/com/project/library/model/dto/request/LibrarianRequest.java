package com.project.library.model.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LibrarianRequest {

    @Size(min = 1, max = 20, message = "Username must be between 1 and 20 characters")
    @NotEmpty(message = "Username must be not empty")
    @NotBlank(message = "Username is required")
    private String username;

    @NotEmpty(message = "Email must not be empty")
    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Email must be valid.Example: firstname-lastname@example.com  ")
    @Column(columnDefinition = "VARCHAR(70)")
    @Size(min = 6, max = 70, message = "Email must be between 6 and 70 characters")
    private String email;

    @Size(min = 1, message = "Password must be at least 1 character")
    @Size(max = 30, message = "Password must be maximum 30 characters")
    @NotEmpty(message = "Password cannot be empty")
    @NotBlank(message = "Password is required")
    private String password;
}

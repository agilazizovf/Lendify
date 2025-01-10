package com.project.library.model.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LibrarianUpdateRequest {

    @Size(min = 1, max = 20, message = "Username must be between 1 and 20 characters")
    @NotEmpty(message = "Username must be not empty")
    @NotBlank(message = "Username is required")
    private String username;
}

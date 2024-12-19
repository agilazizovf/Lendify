package com.project.library.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequest {

    @Size(min = 1, max = 30, message = "Name must be between 1 and 30 characters")
    @NotEmpty(message = "Name must be not empty")
    @NotBlank(message = "Name is required")
    private String name;

    @Size(min = 1, max = 1000, message = "Description must be between 1 and 1000 characters")
    @NotEmpty(message = "Description must be not empty")
    @NotBlank(message = "Description is required")
    private String description;
}

package com.project.library.model.dto.request;

import lombok.Data;

@Data
public class BookRequest {

    private String name;

    private String description;

    private String author;

    private Long categoryId;
}

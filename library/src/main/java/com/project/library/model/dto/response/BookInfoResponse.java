package com.project.library.model.dto.response;

import com.project.library.dao.entity.CategoryEntity;
import com.project.library.dao.entity.LibrarianEntity;
import lombok.Data;

@Data
public class BookInfoResponse {

    private Long id;
    private String name;
    private String description;
    private String author;
    private CategoryEntity category;
    private LibrarianEntity librarian;
}

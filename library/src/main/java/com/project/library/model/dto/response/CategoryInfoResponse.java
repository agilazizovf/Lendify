package com.project.library.model.dto.response;

import com.project.library.dao.entity.LibrarianEntity;
import lombok.Data;

@Data
public class CategoryInfoResponse {

    private Long id;
    private String name;
    private String description;
    private LibrarianEntity librarian;
}

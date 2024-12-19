package com.project.library.mapper;

import com.project.library.dao.entity.CategoryEntity;
import com.project.library.model.dto.response.CategoryInfoResponse;

public class CategoryMapper {

    public static CategoryInfoResponse toCategoryDTO(CategoryEntity category) {
        CategoryInfoResponse response = new CategoryInfoResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());

        if (category.getLibrarian() != null) {
            response.setLibrarian(category.getLibrarian());
        }

        return response;
    }
}

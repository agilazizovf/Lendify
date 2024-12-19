package com.project.library.service;

import com.project.library.dao.entity.UserEntity;
import com.project.library.model.dto.request.CategoryRequest;
import com.project.library.model.dto.response.CategoryInfoResponse;
import com.project.library.model.dto.response.MessageResponse;
import com.project.library.model.dto.response.PageResponse;
import org.springframework.http.ResponseEntity;

public interface CategoryService {

    ResponseEntity<MessageResponse> create(CategoryRequest request);
    PageResponse<CategoryInfoResponse> findAllCategories(int page, int size);
    ResponseEntity<CategoryInfoResponse> findCategoryById(Long categoryId);
    ResponseEntity<MessageResponse> update(Long categoryId, CategoryRequest request);
    void delete(Long categoryId);
    UserEntity getCurrentUser();
}

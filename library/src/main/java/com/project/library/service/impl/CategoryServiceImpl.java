package com.project.library.service.impl;

import com.project.library.dao.entity.CategoryEntity;
import com.project.library.dao.entity.UserEntity;
import com.project.library.dao.repository.CategoryRepository;
import com.project.library.dao.repository.UserRepository;
import com.project.library.mapper.CategoryMapper;
import com.project.library.model.dto.request.CategoryRequest;
import com.project.library.model.dto.response.CategoryInfoResponse;
import com.project.library.model.dto.response.MessageResponse;
import com.project.library.model.dto.response.PageResponse;
import com.project.library.model.exception.AuthenticationException;
import com.project.library.model.exception.ResourceNotFoundException;
import com.project.library.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public ResponseEntity<MessageResponse> create(CategoryRequest request) {
        UserEntity user = getCurrentUser();

        CategoryEntity category = new CategoryEntity();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setLibrarian(user.getLibrarian());

        categoryRepository.save(category);

        MessageResponse response = new MessageResponse(request.getName()+" category created successfully!");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public PageResponse<CategoryInfoResponse> findAllCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryEntity> categoryEntities = categoryRepository.findAll(pageable);

        List<CategoryInfoResponse> responses = categoryEntities
                .stream()
                .map(CategoryMapper::toCategoryDTO)
                .collect(Collectors.toList());

        PageResponse<CategoryInfoResponse> pageResponse = new PageResponse<>();
        pageResponse.setContent(responses);
        pageResponse.setPage(categoryEntities.getPageable().getPageNumber());
        pageResponse.setSize(categoryEntities.getPageable().getPageSize());
        pageResponse.setTotalElements(categoryEntities.getTotalElements());
        pageResponse.setTotalPages(categoryEntities.getTotalPages());
        pageResponse.setLast(categoryEntities.isLast());
        pageResponse.setFirst(categoryEntities.isFirst());

        return pageResponse;
    }

    @Override
    public ResponseEntity<CategoryInfoResponse> findCategoryById(Long categoryId) {
        UserEntity user = getCurrentUser();

        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: "+categoryId));

        CategoryInfoResponse response = new CategoryInfoResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        response.setLibrarian(category.getLibrarian());

        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @Override
    public ResponseEntity<MessageResponse> update(Long categoryId, CategoryRequest request) {
        UserEntity user = getCurrentUser();

        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: "+categoryId));

        if (!category.getLibrarian().equals(user.getLibrarian())) {
            throw new AuthenticationException("You do not have permission to update this category!");
        }
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setLibrarian(user.getLibrarian());

        categoryRepository.save(category);

        MessageResponse response = new MessageResponse(request.getName()+" category updated successfully!");

        return ResponseEntity.ok(response);
    }

    @Override
    public void delete(Long categoryId) {
        UserEntity user = getCurrentUser();

        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: "+categoryId));

        if (!category.getLibrarian().equals(user.getLibrarian())) {
            throw new AuthenticationException("You do not have permission to delete this category!");
        }
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}

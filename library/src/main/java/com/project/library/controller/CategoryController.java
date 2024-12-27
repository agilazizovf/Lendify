package com.project.library.controller;

import com.project.library.model.dto.request.CategoryRequest;
import com.project.library.model.dto.response.CategoryInfoResponse;
import com.project.library.model.dto.response.MessageResponse;
import com.project.library.model.dto.response.PageResponse;
import com.project.library.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    // @PreAuthorize("hasAuthority('ADD_CATEGORY')")
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid CategoryRequest request) {
        return categoryService.create(request);
    }

    @GetMapping("/get-all")
    // @PreAuthorize("hasAuthority('GET_ALL_CATEGORIES')")
    public PageResponse<CategoryInfoResponse> findAllCategories(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        return categoryService.findAllCategories(page, size);
    }

    @GetMapping("/{categoryId}")
    // @PreAuthorize("hasAuthority('FIND_CATEGORY')")
    public ResponseEntity<CategoryInfoResponse> findCategoryById(@PathVariable Long categoryId) {
        return categoryService.findCategoryById(categoryId);
    }

    @PutMapping("/update/{categoryId}")
    // @PreAuthorize("hasAuthority('UPDATE_CATEGORY')")
    public ResponseEntity<MessageResponse> update(@PathVariable Long categoryId, CategoryRequest request) {
        return categoryService.update(categoryId, request);
    }

    @DeleteMapping("/delete/{categoryId}")
    // @PreAuthorize("hasAuthority('DELETE_CATEGORY')")
    public void delete(@PathVariable Long categoryId) {
        categoryService.delete(categoryId);
    }
}

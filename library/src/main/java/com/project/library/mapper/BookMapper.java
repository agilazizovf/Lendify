package com.project.library.mapper;

import com.project.library.dao.entity.BookEntity;
import com.project.library.model.dto.response.BookInfoResponse;

public class BookMapper {

    public static BookInfoResponse toBookDTO(BookEntity book) {
        BookInfoResponse response = new BookInfoResponse();
        response.setId(book.getId());
        response.setName(book.getName());
        response.setDescription(book.getDescription());
        response.setAuthor(book.getAuthor());

        if (book.getCategory() != null) {
            response.setCategory(book.getCategory());
        }
        if (book.getLibrarian() != null) {
            response.setLibrarian(book.getLibrarian());
        }

        return response;
    }
}

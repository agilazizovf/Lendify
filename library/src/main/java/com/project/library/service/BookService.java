package com.project.library.service;

import com.project.library.dao.entity.UserEntity;
import com.project.library.model.dto.request.BookRequest;
import com.project.library.model.dto.response.BookInfoResponse;
import com.project.library.model.dto.response.MessageResponse;
import com.project.library.model.dto.response.PageResponse;
import org.springframework.http.ResponseEntity;

public interface BookService {

    ResponseEntity<MessageResponse> create(BookRequest request);
    PageResponse<BookInfoResponse> findAllBooks(int page, int size);
    ResponseEntity<BookInfoResponse> findBookById(Long bookId);
    ResponseEntity<MessageResponse> update(Long bookId, BookRequest request);
    void delete(Long bookId);
    ResponseEntity<MessageResponse> borrowBook(Long bookId);
    ResponseEntity<MessageResponse> returnBook(Long bookId);
    UserEntity getCurrentUser();
}

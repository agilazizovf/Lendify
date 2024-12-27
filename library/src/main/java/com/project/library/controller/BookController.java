package com.project.library.controller;

import com.project.library.model.dto.request.BookRequest;
import com.project.library.model.dto.response.BookInfoResponse;
import com.project.library.model.dto.response.MessageResponse;
import com.project.library.model.dto.response.PageResponse;
import com.project.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/create/{categoryId}")
    // @PreAuthorize("hasAuthority('ADD_BOOK')")
    public ResponseEntity<MessageResponse> create(@PathVariable Long categoryId, @RequestBody @Valid BookRequest request) {
        return bookService.create(categoryId, request);
    }

    @GetMapping("/get-all")
    // @PreAuthorize("hasAuthority('GET_ALL_BOOKS')")
    public PageResponse<BookInfoResponse> findAllBooks(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        return bookService.findAllBooks(page, size);
    }

    @GetMapping("/{bookId}")
    // @PreAuthorize("hasAuthority('FIND_BOOK')")
    public ResponseEntity<BookInfoResponse> findBookById(@PathVariable Long bookId) {
        return bookService.findBookById(bookId);
    }

    @PutMapping("/update/{categoryId}/{bookId}")
    // @PreAuthorize("hasAuthority('UPDATE_BOOK')")
    public ResponseEntity<MessageResponse> update(@PathVariable Long categoryId, @PathVariable Long bookId,
                                                  @RequestBody @Valid BookRequest request) {
        return bookService.update(categoryId, bookId, request);
    }

    @DeleteMapping("/delete/{bookId}")
    // @PreAuthorize("hasAuthority('DELETE_BOOK')")
    public void delete(@PathVariable Long bookId) {
        bookService.delete(bookId);
    }

    @PostMapping("/borrow/{bookId}")
    // @PreAuthorize("hasAuthority('BORROW_BOOK')")
    public ResponseEntity<MessageResponse> borrowBook(@PathVariable Long bookId) {
        return bookService.borrowBook(bookId);
    }

    @PostMapping("/return/{bookId}")
    // @PreAuthorize("hasAuthority('RETURN_BOOK')")
    public ResponseEntity<MessageResponse> returnBook(@PathVariable Long bookId) {
        return bookService.returnBook(bookId);
    }

}

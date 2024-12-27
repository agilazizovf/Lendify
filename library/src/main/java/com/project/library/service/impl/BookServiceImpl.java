package com.project.library.service.impl;

import com.project.library.dao.entity.BookEntity;
import com.project.library.dao.entity.BorrowedBookEntity;
import com.project.library.dao.entity.CategoryEntity;
import com.project.library.dao.entity.UserEntity;
import com.project.library.dao.repository.BookRepository;
import com.project.library.dao.repository.BorrowedBookRepository;
import com.project.library.dao.repository.CategoryRepository;
import com.project.library.dao.repository.UserRepository;
import com.project.library.mapper.BookMapper;
import com.project.library.model.dto.request.BookRequest;
import com.project.library.model.dto.response.BookInfoResponse;
import com.project.library.model.dto.response.MessageResponse;
import com.project.library.model.dto.response.PageResponse;
import com.project.library.model.exception.AuthenticationException;
import com.project.library.model.exception.ResourceNotFoundException;
import com.project.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final BorrowedBookRepository borrowedBookRepository;

    @Override
    public ResponseEntity<MessageResponse> create(Long categoryId, BookRequest request) {
        UserEntity user = getCurrentUser();

        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: "+categoryId));

        BookEntity book = new BookEntity();
        modelMapper.map(request, book);
        book.setCategory(category);
        book.setLibrarian(user.getLibrarian());
        book.setBorrowed(false);
        bookRepository.save(book);

        MessageResponse response = new MessageResponse(request.getName()+" book created successfully!");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public PageResponse<BookInfoResponse> findAllBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookEntity> bookEntities = bookRepository.findAll(pageable);

        List<BookInfoResponse> responses = bookEntities
                .stream()
                .map(BookMapper::toBookDTO)
                .collect(Collectors.toList());

        PageResponse<BookInfoResponse> pageResponse = new PageResponse<>();
        pageResponse.setContent(responses);
        pageResponse.setPage(bookEntities.getPageable().getPageNumber());
        pageResponse.setSize(bookEntities.getPageable().getPageSize());
        pageResponse.setTotalElements(bookEntities.getTotalElements());
        pageResponse.setTotalPages(bookEntities.getTotalPages());
        pageResponse.setLast(bookEntities.isLast());
        pageResponse.setFirst(bookEntities.isFirst());

        return pageResponse;
    }

    @Override
    public ResponseEntity<BookInfoResponse> findBookById(Long bookId) {
        UserEntity user = getCurrentUser();

        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: "+bookId));
        BookInfoResponse response = new BookInfoResponse();
        response.setId(book.getId());
        response.setName(book.getName());
        response.setDescription(book.getDescription());
        response.setAuthor(book.getAuthor());
        response.setCategory(book.getCategory());
        response.setLibrarian(user.getLibrarian());

        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @Override
    public ResponseEntity<MessageResponse> update(Long findCategoryId, Long bookId, BookRequest request) {
        UserEntity user = getCurrentUser();

        CategoryEntity category = categoryRepository.findById(findCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: "+findCategoryId));
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: "+bookId));

        if (!book.getLibrarian().equals(user.getLibrarian())) {
            throw new AuthenticationException("You do not have permission to update this book");
        }
        modelMapper.map(request, book);
        book.setCategory(category);
        book.setLibrarian(user.getLibrarian());
        book.setBorrowed(false);
        bookRepository.save(book);

        MessageResponse response = new MessageResponse(request.getName()+" updated successfully!");

        return ResponseEntity.ok(response);
    }

    @Override
    public void delete(Long bookId) {
        UserEntity user = getCurrentUser();

        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: "+bookId));

        if (!book.getLibrarian().equals(user.getLibrarian())) {
            throw new AuthenticationException("You do not have permission to update this book");
        }
        bookRepository.deleteById(bookId);

    }

    @Override
    public ResponseEntity<MessageResponse> borrowBook(Long bookId) {
        UserEntity user = getCurrentUser();

        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: "+bookId));

        BorrowedBookEntity borrowedBook = new BorrowedBookEntity();
        borrowedBook.setBook(book);
        borrowedBook.setStudent(user.getStudent());
        borrowedBook.setBorrowDate(LocalDate.now());
        borrowedBookRepository.save(borrowedBook);

        book.setBorrowed(true);
        bookRepository.save(book);

        MessageResponse response = new MessageResponse(book.getName()+" borrowed successfully!");

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @Override
    public ResponseEntity<MessageResponse> returnBook(Long bookId) {
        UserEntity user = getCurrentUser();

        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: "+bookId));

        if (!book.isBorrowed()) {
            throw new RuntimeException("Book is not borrowed.");
        }

        BorrowedBookEntity borrowedBook = borrowedBookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrowed book not found with ID: "+bookId));

        borrowedBook.setReturnDate(LocalDate.now());
        borrowedBookRepository.save(borrowedBook);

        book.setBorrowed(false);
        bookRepository.save(book);

        MessageResponse response = new MessageResponse(book.getName()+" returned successfully!");

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @Override
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}

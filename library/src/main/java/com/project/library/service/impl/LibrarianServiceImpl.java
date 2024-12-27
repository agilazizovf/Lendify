package com.project.library.service.impl;

import com.project.library.dao.entity.AuthorityEntity;
import com.project.library.dao.entity.LibrarianEntity;
import com.project.library.dao.entity.UserEntity;
import com.project.library.dao.repository.LibrarianRepository;
import com.project.library.dao.repository.UserRepository;
import com.project.library.model.dto.request.LibrarianRequest;
import com.project.library.model.dto.response.MessageResponse;
import com.project.library.model.enums.UserStatus;
import com.project.library.model.exception.AlreadyExistsException;
import com.project.library.service.LibrarianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LibrarianServiceImpl implements LibrarianService {

    private final UserRepository userRepository;
    private final LibrarianRepository librarianRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<MessageResponse> register(LibrarianRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new AlreadyExistsException("User already exists");
        }

        UserEntity user = new UserEntity(request.getUsername(), passwordEncoder.encode(request.getPassword()));
        Set<AuthorityEntity> authorityEntitySet = Set.of(
                new AuthorityEntity("ADD_STUDENT"),
                new AuthorityEntity("GET_ALL_STUDENTS"),
                new AuthorityEntity("FIND_STUDENT"),
                new AuthorityEntity("UPDATE_STUDENT"),
                new AuthorityEntity("DELETE_STUDENT"),
                new AuthorityEntity("GET_ALL_BOOKS"),
                new AuthorityEntity("FIND_BOOK"),
                new AuthorityEntity("GET_ALL_CATEGORIES"),
                new AuthorityEntity("FIND_CATEGORY")
        );
        user.setAuthorities(authorityEntitySet);

        userRepository.save(user);

        LibrarianEntity librarian = new LibrarianEntity();
        librarian.setUsername(request.getUsername());
        librarian.setEmail(request.getEmail());
        librarian.setRegisterDate(LocalDateTime.now());
        librarian.setUpdateDate(LocalDateTime.now());
        librarian.setStatus(UserStatus.CREATED);
        librarian.setUser(user);

        librarianRepository.save(librarian);

        MessageResponse response = new MessageResponse(request.getUsername()+" created successfully!");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

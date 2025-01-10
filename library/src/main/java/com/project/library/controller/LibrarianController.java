package com.project.library.controller;

import com.project.library.model.dto.request.LibrarianRequest;
import com.project.library.model.dto.request.LibrarianUpdateRequest;
import com.project.library.model.dto.request.LoginRequest;
import com.project.library.model.dto.response.MessageResponse;
import com.project.library.service.LibrarianService;
import com.project.library.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/librarians")
@RequiredArgsConstructor
public class LibrarianController {

    private final LoginService loginService;
    private final LibrarianService librarianService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        return loginService.login(request);
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registration(@RequestBody @Valid LibrarianRequest request) {
        return librarianService.register(request);
    }

    @PutMapping("/update")
    public ResponseEntity<MessageResponse> update(@RequestBody @Valid LibrarianUpdateRequest request) {
        return librarianService.update(request);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount() {
        return librarianService.deleteAccount();
    }
}

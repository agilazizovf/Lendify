package com.project.library.controller;

import com.project.library.model.dto.request.LibrarianRequest;
import com.project.library.model.dto.request.LoginRequest;
import com.project.library.model.dto.response.MessageResponse;
import com.project.library.service.LibrarianService;
import com.project.library.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

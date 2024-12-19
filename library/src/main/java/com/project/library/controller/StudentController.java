package com.project.library.controller;

import com.project.library.model.dto.request.LoginRequest;
import com.project.library.model.dto.request.StudentRequest;
import com.project.library.model.dto.response.MessageResponse;
import com.project.library.model.dto.response.PageResponse;
import com.project.library.model.dto.response.StudentInfoResponse;
import com.project.library.service.LoginService;
import com.project.library.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final LoginService loginService;
    private final StudentService studentService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        return loginService.login(request);
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registration(@RequestBody @Valid StudentRequest request) {
        return studentService.register(request);
    }

    @GetMapping("/get-all")
    public PageResponse<StudentInfoResponse> findAllStudents(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        return studentService.findAllStudents(page, size);
    }
}

package com.project.library.service;

import com.project.library.model.dto.request.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    ResponseEntity<?> login(LoginRequest request);
}

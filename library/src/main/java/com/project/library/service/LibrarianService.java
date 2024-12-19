package com.project.library.service;

import com.project.library.model.dto.request.LibrarianRequest;
import com.project.library.model.dto.response.MessageResponse;
import org.springframework.http.ResponseEntity;

public interface LibrarianService {

    ResponseEntity<MessageResponse> register(LibrarianRequest request);
}

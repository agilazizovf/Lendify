package com.project.library.service;

import com.project.library.dao.entity.UserEntity;
import com.project.library.model.dto.request.StudentRequest;
import com.project.library.model.dto.response.MessageResponse;
import com.project.library.model.dto.response.PageResponse;
import com.project.library.model.dto.response.StudentInfoResponse;
import org.springframework.http.ResponseEntity;

public interface StudentService {

    ResponseEntity<MessageResponse> register(StudentRequest request);
    PageResponse<StudentInfoResponse> findAllStudents(int page, int size);
    UserEntity getCurrentUser();
}

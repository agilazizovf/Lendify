package com.project.library.service.impl;

import com.project.library.dao.entity.AuthorityEntity;
import com.project.library.dao.entity.LibrarianEntity;
import com.project.library.dao.entity.StudentEntity;
import com.project.library.dao.entity.UserEntity;
import com.project.library.dao.repository.LibrarianRepository;
import com.project.library.dao.repository.StudentRepository;
import com.project.library.dao.repository.UserRepository;
import com.project.library.mapper.StudentMapper;
import com.project.library.model.dto.request.StudentRequest;
import com.project.library.model.dto.response.MessageResponse;
import com.project.library.model.dto.response.PageResponse;
import com.project.library.model.dto.response.StudentInfoResponse;
import com.project.library.model.enums.UserStatus;
import com.project.library.model.exception.ResourceNotFoundException;
import com.project.library.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final LibrarianRepository librarianRepository;
    private final ModelMapper modelMapper;
    @Override
    public ResponseEntity<MessageResponse> register(StudentRequest request) {
        UserEntity registeredByLibrarian = getCurrentUser();

        UserEntity userAsStudent = new UserEntity(request.getUsername(), passwordEncoder.encode(request.getPassword()));
        AuthorityEntity authority = new AuthorityEntity("STUDENT");
        Set<AuthorityEntity> authorityEntitySet = Set.of(authority);
        userAsStudent.setAuthorities(authorityEntitySet);

        userRepository.save(userAsStudent);

        StudentEntity student = new StudentEntity();
        modelMapper.map(request, student);
        student.setRegisterDate(LocalDateTime.now());
        student.setUpdateDate(LocalDateTime.now());
        student.setStatus(UserStatus.CREATED);
        student.setUser(userAsStudent);
        student.setLibrarian(registeredByLibrarian.getLibrarian());

        studentRepository.save(student);

        MessageResponse response = new MessageResponse(request.getUsername()+" created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public PageResponse<StudentInfoResponse> findAllStudents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentEntity> studentEntities = studentRepository.findAll(pageable);

        List<StudentInfoResponse> responses = studentEntities
                .stream()
                .map(StudentMapper::toStudentDTO)
                .collect(Collectors.toList());

        PageResponse<StudentInfoResponse> pageResponse = new PageResponse<>();
        pageResponse.setContent(responses);
        pageResponse.setPage(studentEntities.getPageable().getPageNumber());
        pageResponse.setSize(studentEntities.getPageable().getPageSize());
        pageResponse.setTotalElements(studentEntities.getTotalElements());
        pageResponse.setTotalPages(studentEntities.getTotalPages());
        pageResponse.setLast(studentEntities.isLast());
        pageResponse.setFirst(studentEntities.isFirst());

        return pageResponse;
    }

    @Override
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}

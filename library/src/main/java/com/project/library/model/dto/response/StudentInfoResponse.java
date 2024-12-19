package com.project.library.model.dto.response;

import com.project.library.dao.entity.LibrarianEntity;
import com.project.library.model.enums.UserStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StudentInfoResponse {

    private Long id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String phone;
    private LocalDate birthday;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;
    private UserStatus status;
    private LibrarianEntity librarian;
}

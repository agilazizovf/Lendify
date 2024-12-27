package com.project.library.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.library.model.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String username;

    @Email
    @Column(unique = true)
    private String email;

    private String phone;
    private LocalDate birthday;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status", nullable = false)
    private UserStatus status;

    @ManyToOne
    @JoinColumn(name = "registered_by_librarian_id")
    @JsonBackReference
    private LibrarianEntity librarian;

    @OneToMany(mappedBy = "student")
    private List<BorrowedBookEntity> borrowedBooks = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity user;

}

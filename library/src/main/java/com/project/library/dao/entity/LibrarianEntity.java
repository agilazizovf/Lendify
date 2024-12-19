package com.project.library.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.library.model.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "librarians")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibrarianEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private LocalDateTime registerDate;
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "librarian")
    @JsonBackReference
    private List<StudentEntity> students;

    @JsonIgnore
    @JsonBackReference
    @OneToMany(mappedBy = "librarian")
    private List<CategoryEntity> categories;

    @Email
    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status", nullable = false)
    private UserStatus status;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity user;
}

package com.project.library.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "borrowed-books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowedBookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    private LocalDate borrowDate;
    private LocalDate returnDate;
}

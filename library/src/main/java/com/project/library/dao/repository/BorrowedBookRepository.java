package com.project.library.dao.repository;

import com.project.library.dao.entity.BorrowedBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBookEntity, Long> {
}

package com.project.library.dao.repository;

import com.project.library.dao.entity.LibrarianEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibrarianRepository extends JpaRepository<LibrarianEntity, Long> {
}

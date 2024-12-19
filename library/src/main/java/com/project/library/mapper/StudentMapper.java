package com.project.library.mapper;

import com.project.library.dao.entity.StudentEntity;
import com.project.library.model.dto.response.StudentInfoResponse;

public class StudentMapper {

    public static StudentInfoResponse toStudentDTO(StudentEntity studentEntity) {
        StudentInfoResponse response = new StudentInfoResponse();
        response.setId(studentEntity.getId());
        response.setName(studentEntity.getName());
        response.setSurname(studentEntity.getSurname());
        response.setEmail(studentEntity.getEmail());
        response.setPhone(studentEntity.getPhone());
        response.setUsername(studentEntity.getUsername());
        response.setBirthday(studentEntity.getBirthday());
        response.setRegisterDate(studentEntity.getRegisterDate());
        response.setUpdateDate(studentEntity.getUpdateDate());

        if (studentEntity.getStatus() != null) {
            response.setStatus(studentEntity.getStatus());
        }
        if (studentEntity.getLibrarian() != null) {
            response.setLibrarian(studentEntity.getLibrarian());
        }
        return response;
    }
}

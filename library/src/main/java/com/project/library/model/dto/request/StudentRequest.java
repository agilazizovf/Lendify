package com.project.library.model.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StudentRequest {

    @NotEmpty(message = "Name must not be empty")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    @Column(columnDefinition = "VARCHAR(50)")
    private String name;

    @NotEmpty(message = "Surname must not be empty")
    @Size(min = 3, max = 50, message = "Surname must be between 3 and 50 characters")
    @Column(columnDefinition = "VARCHAR(50)")
    private String surname;

    @Size(min = 1, max = 20, message = "Username must be between 1 and 20 characters")
    @NotEmpty(message = "Username must be not empty")
    @NotBlank(message = "Username is required")
    private String username;

    @NotEmpty(message = "Email must not be empty")
    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Email must be valid.Example: firstname-lastname@example.com  ")
    @Column(columnDefinition = "VARCHAR(70)")
    @Size(min = 6, max = 70, message = "Email must be between 6 and 70 characters")
    private String email;

    @Size(min = 1, message = "Password must be at least 1 character")
    @Size(max = 30, message = "Password must be maximum 30 characters")
    @NotEmpty(message = "Password cannot be empty")
    @NotBlank(message = "Password is required")
    private String password;

    @NotEmpty(message = "Phone must not be empty")
    @Pattern(regexp = "^(\\+994|0)(50|51|55|70|77|99)\\d{7}$", message = "Phone number must be valid. Example: +994000000000 or 0000000000")
    @Column(columnDefinition = "VARCHAR(20)")
    @Size(min = 10, max = 20, message = "Phone must be between 10 and 20 characters")
    private String phone;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthday;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;
}

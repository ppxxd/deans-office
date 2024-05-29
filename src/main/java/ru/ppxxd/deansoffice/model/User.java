package ru.ppxxd.deansoffice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
public class User {
    private Integer id;
    @NotBlank
    @NotEmpty
    @NotNull
    private String login;
    @NotBlank
    @NotEmpty
    @NotNull
    private String password;
    @NotNull
    private Role role;

    public enum Role {
        ADMIN,
        STUDENT,
        TEACHER
    }
}

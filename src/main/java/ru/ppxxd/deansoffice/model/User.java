package ru.ppxxd.deansoffice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private Integer rights;
}

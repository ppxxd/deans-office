package ru.ppxxd.deansoffice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * Студент.
 */

@Data
@Builder
@AllArgsConstructor
public class Student {
    @Getter
    private Integer id;
    @Getter
    private Integer groupID;
    @NotBlank
    @NotEmpty
    @NotNull
    private String lastName;
    @NotBlank
    @NotEmpty
    @NotNull
    private String name;
    private String patronymic;
    @NotNull
    private Integer phoneNumber;
}

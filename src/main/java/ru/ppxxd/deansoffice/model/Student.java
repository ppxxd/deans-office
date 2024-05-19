package ru.ppxxd.deansoffice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Студент.
 */

@Data
@Builder
@AllArgsConstructor
public class Student {
    private Integer id;
    private Integer group_id;
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

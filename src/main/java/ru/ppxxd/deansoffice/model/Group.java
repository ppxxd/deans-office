package ru.ppxxd.deansoffice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * Группа студентов.
 */

@Data
@Builder
@AllArgsConstructor
public class Group {
    @Getter
    private Integer id;
    @NotBlank
    @NotEmpty
    @NotNull
    private String name; //Номер группы
    @NotNull
    @Positive
    private Integer course;
    @NotBlank
    @NotEmpty
    @NotNull
    private String educationDirection; //Направление обучения
}

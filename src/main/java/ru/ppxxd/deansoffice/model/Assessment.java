package ru.ppxxd.deantsoffice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Оценка.
 * Айди предмета
 * Название предмета
 * Оценка
 */

@Data
@Builder
@AllArgsConstructor
public class Assessment {
    private Integer assessmentID;
    @NotNull
    private Integer subjectID;
    @Positive
    private Integer mark;
    @NotBlank
    @NotEmpty
    @NotNull
    private String type;
    @NotNull
    private Integer received;
}

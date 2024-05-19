package ru.ppxxd.deansoffice.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Subject {
    private Integer subjectID;
    @NotBlank
    @NotEmpty
    @NotNull
    private String name;
    @NotNull
    @Positive
    private Double subjectCoefficient;
}

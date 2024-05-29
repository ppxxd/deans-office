package ru.ppxxd.deansoffice.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@AllArgsConstructor
public class Subject {
    @Getter
    private Integer subjectID;
    @NotBlank
    @NotEmpty
    @NotNull
    private String name;
    @NotNull
    @Positive
    private Double subjectCoefficient;
}

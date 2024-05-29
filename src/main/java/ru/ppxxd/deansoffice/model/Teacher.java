package ru.ppxxd.deansoffice.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@AllArgsConstructor
public class Teacher {
    @Getter
    private Integer id;
    @NotNull
    private Integer chairID;
    @NotNull
    private Integer positionID;
    @NotNull
    private Integer educationID;
    @NotNull
    private Integer workExperienceID;
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

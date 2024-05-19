package ru.ppxxd.deansoffice.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class WorkExperience {
    private Integer workExperienceID;
    @NotNull
    @NotEmpty
    @NotBlank
    private String companyName;
    @NotNull
    @NotEmpty
    @NotBlank
    private String positionName;
    @NotNull
    private LocalDate dateStart;
    @NotNull
    private LocalDate dateEnd;
    private String responsibilities;
}

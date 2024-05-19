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
public class Education {
    private Integer educationID;
    @NotBlank
    @NotEmpty
    @NotNull
    private String type;
    @NotBlank
    @NotEmpty
    @NotNull
    private String specialization;
    @NotBlank
    @NotEmpty
    @NotNull
    private String universityName;
    @NotNull
    private LocalDate dateEnd;
}

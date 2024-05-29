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
public class Chair {
    @Getter
    private Integer chairID;
    @NotBlank
    @NotEmpty
    @NotNull
    private String name;
    @NotBlank
    @NotEmpty
    @NotNull
    private String headLastName;
    @NotBlank
    @NotEmpty
    @NotNull
    private String headFirstName;
    private String headPatronymic;
    @NotNull
    private Integer chairPhoneNumber;
}

package ru.ppxxd.deansoffice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Администратор виртуального деканата.
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@AllArgsConstructor
public class Admin extends User {
}

package by.aston.bank.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record UserWriteDto (
        @NotNull(message = "Name is require field")
        @NotBlank(message = "Name can't be empty")
        String name,
        @NotNull(message = "Lastname ss require field")
        @NotBlank(message = "Lastname can't be empty")
        String lastName,
        String startWork,
        String  endWork) {
}

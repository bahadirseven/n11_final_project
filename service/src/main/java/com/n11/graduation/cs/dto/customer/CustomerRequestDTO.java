package com.n11.graduation.cs.dto.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDTO {

    @Size(min = 11, max = 11, message = "Identity number length should be 11")
    @NotNull(message = "Identity number should not be null")
    @NotBlank(message = "Identity number should not be blank")
    private String identityNumber;

    @NotNull(message = "Fullname should not be null")
    @NotBlank(message = "Fullname should not be empty")
    private String fullName;

    @NotNull(message = "Phone number should not be null")
    @NotBlank(message = "Phone number should not be empty")
    @Size(min = 10, max = 10, message = "Phone number length should be 10")
    private String phoneNumber;

    @NotNull(message = "Birth date should not be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotNull(message = "Income should not be null")
    @PositiveOrZero(message = "Income should be positive or zero")
    private BigDecimal income;
}

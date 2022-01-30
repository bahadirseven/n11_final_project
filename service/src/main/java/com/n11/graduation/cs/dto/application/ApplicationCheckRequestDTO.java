package com.n11.graduation.cs.dto.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationCheckRequestDTO {
    @Size(min = 11, max = 11, message = "Identity number length should be 11")
    @NotNull(message = "Identity number should not be null")
    @NotBlank(message = "Identity number should not be blank")
    private String identityNumber;
    
    @NotNull(message = "Birth date should not be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
}

package com.n11.graduation.cs.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponseDTO {
    private Long id;
    private String identityNumber;
    private String fullname;
    private String phoneNumber;
    private LocalDate birthDate;
    private Integer creditScore;
    private BigDecimal income;
}

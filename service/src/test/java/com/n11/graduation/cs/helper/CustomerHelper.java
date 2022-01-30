package com.n11.graduation.cs.helper;

import com.n11.graduation.cs.dto.customer.CustomerRequestDTO;
import com.n11.graduation.cs.dto.customer.CustomerResponseDTO;
import com.n11.graduation.cs.model.CreditScore;
import com.n11.graduation.cs.model.Customer;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDate;

@UtilityClass
public class CustomerHelper {
    public static CustomerRequestDTO createValidCustomerRequestDTO() {
        return new CustomerRequestDTO(
                "12345678901",
                "TestFullName",
                "1234567890",
                LocalDate.of(1990, 1, 1),
                BigDecimal.valueOf(5000)
        );
    }

    public static CustomerResponseDTO createCustomerResponseDTO() {
        return new CustomerResponseDTO(
                1L,
                "12345678901",
                "TestFullName",
                "1234567890",
                LocalDate.of(1990, 1, 1),
                1000,
                BigDecimal.valueOf(5000)
        );
    }

    public static Customer createCustomer() {
        CreditScore creditScore = CreditScore.builder()
                .id(1L)
                .score(1000)
                .build();

        return Customer.builder()
                .id(1L)
                .identityNumber("12345678901")
                .birthDate(LocalDate.of(1990, 1, 1))
                .fullname("FullName")
                .income(BigDecimal.valueOf(5000))
                .phoneNumber("1234567890")
                .creditScore(creditScore)
                .build();

    }
}

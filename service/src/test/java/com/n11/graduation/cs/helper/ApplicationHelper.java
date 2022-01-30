package com.n11.graduation.cs.helper;

import com.n11.graduation.cs.constant.ApplicationStatus;
import com.n11.graduation.cs.constant.DepositType;
import com.n11.graduation.cs.dto.application.ApplicationRequestDTO;
import com.n11.graduation.cs.dto.application.ApplicationResponseDTO;
import com.n11.graduation.cs.dto.deposit.DepositRequestDTO;
import com.n11.graduation.cs.dto.deposit.DepositResponseDTO;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDate;

@UtilityClass
public class ApplicationHelper {
    public static ApplicationRequestDTO createValidApplicationRequestDTO() {
        return new ApplicationRequestDTO(
                "12345678901",
                "TestFullName",
                BigDecimal.valueOf(5000),
                "1234567890",
                LocalDate.of(1990, 1, 1),
                createValidDepositRequestDTO()
        );
    }

    private static DepositRequestDTO createValidDepositRequestDTO() {
        return new DepositRequestDTO(
                DepositType.HOUSE,
                BigDecimal.valueOf(10000)
        );
    }

    public static ApplicationResponseDTO createApplicationResponseDTO() {
        return new ApplicationResponseDTO(
                1L,
                ApplicationStatus.ACCEPTED,
                BigDecimal.valueOf(10000),
                createDepositResponseDTO()
        );
    }

    private static DepositResponseDTO createDepositResponseDTO() {
        return new DepositResponseDTO(
                DepositType.HOUSE,
                BigDecimal.valueOf(10000)
        );
    }
}

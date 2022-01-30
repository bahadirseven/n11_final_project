package com.n11.graduation.cs.dto.application;

import com.n11.graduation.cs.constant.ApplicationStatus;
import com.n11.graduation.cs.dto.deposit.DepositResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponseDTO {
    private Long id;
    private ApplicationStatus status;
    private BigDecimal value;
    private DepositResponseDTO depositResponseDTO;
}

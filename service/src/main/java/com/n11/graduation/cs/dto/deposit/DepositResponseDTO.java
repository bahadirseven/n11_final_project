package com.n11.graduation.cs.dto.deposit;

import com.n11.graduation.cs.constant.DepositType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositResponseDTO {
    private DepositType type;
    private BigDecimal depositValue;
}

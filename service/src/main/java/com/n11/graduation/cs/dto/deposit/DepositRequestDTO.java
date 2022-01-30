package com.n11.graduation.cs.dto.deposit;

import com.n11.graduation.cs.constant.DepositType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositRequestDTO {

    @NotNull(message = "Deposit type should not be null")
    private DepositType depositType;

    @NotNull(message = "Deposit value should not be null")
    @PositiveOrZero(message = "Deposit value should be positive or zero")
    private BigDecimal value;
}

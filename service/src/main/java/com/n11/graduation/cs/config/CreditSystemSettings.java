package com.n11.graduation.cs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class CreditSystemSettings {

    @Value("${com.n11.graduation.cs.default-credit-limit-multiplier}")
    private BigDecimal defaultCreditLimitMultiplier;

    @Value("${com.n11.graduation.cs.level-one-income}")
    private BigDecimal levelOneIncome;

    @Value("${com.n11.graduation.cs.level-two-income}")
    private BigDecimal levelTwoIncome;

    @Value("${com.n11.graduation.cs.level-one-score}")
    private Integer levelOneScore;

    @Value("${com.n11.graduation.cs.level-two-score}")
    private Integer levelTwoScore;

    @Value("${com.n11.graduation.cs.level-one-result}")
    private BigDecimal levelOneResult;

    @Value("${com.n11.graduation.cs.level-two-result}")
    private BigDecimal levelTwoResult;

    @Value("${com.n11.graduation.cs.level-one-deposit-multiplier}")
    private BigDecimal levelOneDepositMultiplier;

    @Value("${com.n11.graduation.cs.level-two-deposit-multiplier}")
    private BigDecimal levelTwoDepositMultiplier;

    @Value("${com.n11.graduation.cs.level-three-deposit-multiplier}")
    private BigDecimal levelThreeDepositMultiplier;

    @Value("${com.n11.graduation.cs.level-four-deposit-multiplier}")
    private BigDecimal levelFourDepositMultiplier;

    public CreditSystemSettings() {
        super();
    }

    public BigDecimal getDefaultCreditLimitMultiplier() {
        return defaultCreditLimitMultiplier;
    }

    public BigDecimal getLevelOneIncome() {
        return levelOneIncome;
    }

    public BigDecimal getLevelTwoIncome() {
        return levelTwoIncome;
    }

    public Integer getLevelOneScore() {
        return levelOneScore;
    }

    public Integer getLevelTwoScore() {
        return levelTwoScore;
    }

    public BigDecimal getLevelOneResult() {
        return levelOneResult;
    }

    public BigDecimal getLevelTwoResult() {
        return levelTwoResult;
    }

    public BigDecimal getLevelOneDepositMultiplier() {
        return levelOneDepositMultiplier;
    }

    public BigDecimal getLevelTwoDepositMultiplier() {
        return levelTwoDepositMultiplier;
    }

    public BigDecimal getLevelThreeDepositMultiplier() {
        return levelThreeDepositMultiplier;
    }

    public BigDecimal getLevelFourDepositMultiplier() {
        return levelFourDepositMultiplier;
    }

}
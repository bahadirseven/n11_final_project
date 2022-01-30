package com.n11.graduation.cs.repository.creditscore;

import com.n11.graduation.cs.model.CreditScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditScoreRepository extends JpaRepository<CreditScore, Long> {
    Optional<CreditScore> findCreditScoreByCustomerId(Long customerId);
}

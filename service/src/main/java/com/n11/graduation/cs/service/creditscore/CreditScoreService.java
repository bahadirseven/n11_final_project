package com.n11.graduation.cs.service.creditscore;

import com.n11.graduation.cs.constant.ErrorMessage;
import com.n11.graduation.cs.dto.creditscore.CreditScoreResponseDTO;
import com.n11.graduation.cs.exception.CsEntityNotFoundException;
import com.n11.graduation.cs.model.CreditScore;
import com.n11.graduation.cs.repository.creditscore.CreditScoreRepository;
import com.n11.graduation.cs.util.ConverterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditScoreService {
    private final CreditScoreRepository creditScoreRepository;

    public CreditScoreResponseDTO findCreditScoreByCustomerId(Long customerId) {

        CreditScore creditScore = creditScoreRepository.findCreditScoreByCustomerId(customerId)
                .orElseThrow(() -> new CsEntityNotFoundException(ErrorMessage.CREDIT_SCORE_NOT_FOUND_FOR_USER));

        return ConverterUtil.convertCreditScoreToCreditScoreResponse(creditScore);
    }
}

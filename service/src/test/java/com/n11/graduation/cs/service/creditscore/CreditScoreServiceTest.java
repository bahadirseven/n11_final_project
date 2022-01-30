package com.n11.graduation.cs.service.creditscore;

import com.n11.graduation.cs.constant.ErrorMessage;
import com.n11.graduation.cs.dto.creditscore.CreditScoreResponseDTO;
import com.n11.graduation.cs.exception.CsEntityNotFoundException;
import com.n11.graduation.cs.model.CreditScore;
import com.n11.graduation.cs.model.Customer;
import com.n11.graduation.cs.repository.creditscore.CreditScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreditScoreServiceTest {
    private CreditScoreService creditScoreService;

    @Mock
    private CreditScoreRepository creditScoreRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        creditScoreService = new CreditScoreService(creditScoreRepository);
    }

    @Test
    void given_validCustomerId_when_findCreditScoreByCustomerId_then_returnCreditResponseDTO() {
        Long customerId = 1L;

        CreditScore expectedCreditScore = new CreditScore(1L, 1000, Customer.builder().id(1L).build());

        Mockito.when(creditScoreRepository.findCreditScoreByCustomerId(customerId)).thenReturn(Optional.of(expectedCreditScore));

        CreditScoreResponseDTO actualCreditScoreResponse = creditScoreService.findCreditScoreByCustomerId(customerId);

        assertThat(actualCreditScoreResponse)
                .isNotNull();

        assertThat(actualCreditScoreResponse.getCreditScore())
                .isNotNull()
                .isPositive()
                .isBetween(1, 1900)
                .isEqualTo(1000);
    }

    @Test
    void given_invalidCustomerId_when_findCreditScoreByCustomerId_then_throwCsEntityNotFoundException() {
        Mockito.when(creditScoreRepository.findCreditScoreByCustomerId(0L)).thenReturn(Optional.ofNullable(null));

        CsEntityNotFoundException exception = assertThrows(CsEntityNotFoundException.class,
                () -> creditScoreService.findCreditScoreByCustomerId(0L));

        assertNotNull(exception);
        assertThat(ErrorMessage.CREDIT_SCORE_NOT_FOUND_FOR_USER.getMessage())
                .isEqualTo(exception.getMessage());
    }
}

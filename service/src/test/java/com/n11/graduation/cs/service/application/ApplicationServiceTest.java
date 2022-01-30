package com.n11.graduation.cs.service.application;

import com.n11.graduation.cs.config.CreditSystemSettings;
import com.n11.graduation.cs.constant.ApplicationStatus;
import com.n11.graduation.cs.constant.DepositType;
import com.n11.graduation.cs.constant.ErrorMessage;
import com.n11.graduation.cs.dto.application.ApplicationRequestDTO;
import com.n11.graduation.cs.dto.application.ApplicationResponseDTO;
import com.n11.graduation.cs.dto.creditscore.CreditScoreResponseDTO;
import com.n11.graduation.cs.dto.deposit.DepositRequestDTO;
import com.n11.graduation.cs.exception.CsEntityNotFoundException;
import com.n11.graduation.cs.helper.CustomerHelper;
import com.n11.graduation.cs.model.Application;
import com.n11.graduation.cs.model.Customer;
import com.n11.graduation.cs.model.Deposit;
import com.n11.graduation.cs.repository.application.ApplicationRepository;
import com.n11.graduation.cs.service.creditscore.CreditScoreService;
import com.n11.graduation.cs.service.customer.CustomerService;
import com.n11.graduation.cs.service.notification.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicationServiceTest {
    public static final String IDENTITY_NUMBER = "12345678901";
    public static final int LEVEL_2_CREDIT_SCORE_FOR_CUSTOMER = 1000;
    public static final int LEVEL_1_CREDIT_SCORE_FOR_CUSTOMER = 750;
    public static final int LEVEL_0_CREDIT_SCORE_FOR_CUSTOMER = 450;

    private ApplicationService applicationService;
    @Mock
    private CreditSystemSettings creditSystemSettings;
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private CreditScoreService creditScoreService;
    @Mock
    private CustomerService customerService;
    @Mock
    private NotificationService notificationService;

    private Customer customer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        applicationService = new ApplicationService(applicationRepository, creditScoreService, customerService, creditSystemSettings, notificationService);
        customer = CustomerHelper.createCustomer();

        Mockito.when(customerService.findUserByIdentityNumber(IDENTITY_NUMBER)).thenReturn(customer);
        mockCreditSystemSettings();
    }

    @Test
    void given_emptyDeposit_And_GreaterLevelTwoScore_And_GreaterLevelTwoScore_And_LessThanLevelTwoIncome_when_saveApplication_then_returnEmptyDeposit() {
        Application application = getApplication(20000, ApplicationStatus.ACCEPTED, null);

        Mockito.when(creditScoreService.findCreditScoreByCustomerId(1L)).thenReturn(new CreditScoreResponseDTO(LEVEL_2_CREDIT_SCORE_FOR_CUSTOMER));
        Mockito.when(applicationRepository.save(ArgumentMatchers.any())).thenReturn(application);

        ApplicationRequestDTO applicationRequestDTO = getApplicationRequestDTO(BigDecimal.valueOf(5000), null);

        ApplicationResponseDTO applicationResponseDTO = applicationService.saveApplication(applicationRequestDTO);

        assertThat(applicationResponseDTO)
                .isNotNull();

        assertThat(applicationResponseDTO.getDepositResponseDTO())
                .isNull();
    }

    @Test
    void given_notEmptyDeposit_And_GreaterLevelTwoScore_And_GreaterLevelTwoScore_And_LessThanLevelTwoIncome_when_saveApplication_then_returnNotEmptyDeposit() {
        Deposit deposit = Deposit.builder()
                .id(1L)
                .depositType(DepositType.HOUSE)
                .value(BigDecimal.valueOf(50000))
                .build();

        Application application = getApplication(45000, ApplicationStatus.ACCEPTED, deposit);

        Mockito.when(creditScoreService.findCreditScoreByCustomerId(1L)).thenReturn(new CreditScoreResponseDTO(LEVEL_2_CREDIT_SCORE_FOR_CUSTOMER));
        Mockito.when(applicationRepository.save(ArgumentMatchers.any())).thenReturn(application);

        DepositRequestDTO depositRequestDTO = new DepositRequestDTO(
                DepositType.HOUSE,
                BigDecimal.valueOf(50000)
        );

        ApplicationRequestDTO applicationRequestDTO = getApplicationRequestDTO(BigDecimal.valueOf(5000), depositRequestDTO);


        ApplicationResponseDTO applicationResponseDTO = applicationService.saveApplication(applicationRequestDTO);

        assertThat(applicationResponseDTO)
                .isNotNull();

        assertThat(applicationResponseDTO.getDepositResponseDTO())
                .isNotNull();
        assertThat(applicationResponseDTO.getDepositResponseDTO().getType())
                .isEqualTo(depositRequestDTO.getDepositType());
        assertThat(applicationResponseDTO.getDepositResponseDTO().getDepositValue())
                .isEqualTo(depositRequestDTO.getValue());
    }

    @Test
    void given_BetweenScoreLevelOneAndTwo_And_LessThanLevelOneIncome_when_saveApplication_then_returnApplicationResponseDTO() {
        Deposit deposit = Deposit.builder()
                .id(1L)
                .depositType(DepositType.HOUSE)
                .value(BigDecimal.valueOf(20000))
                .build();
        Application application = getApplication(12000, ApplicationStatus.ACCEPTED, deposit);

        Mockito.when(creditScoreService.findCreditScoreByCustomerId(1L)).thenReturn(new CreditScoreResponseDTO(LEVEL_1_CREDIT_SCORE_FOR_CUSTOMER));
        Mockito.when(applicationRepository.save(ArgumentMatchers.any())).thenReturn(application);

        DepositRequestDTO depositRequestDTO = new DepositRequestDTO(
                DepositType.HOUSE,
                BigDecimal.valueOf(20000)
        );

        ApplicationRequestDTO applicationRequestDTO = getApplicationRequestDTO(BigDecimal.valueOf(3500), depositRequestDTO);

        applicationRequestDTO.setDepositRequestDTO(depositRequestDTO);

        ApplicationResponseDTO applicationResponseDTO = applicationService.saveApplication(applicationRequestDTO);

        assertThat(applicationResponseDTO)
                .isNotNull();

        assertThat(applicationResponseDTO.getDepositResponseDTO())
                .isNotNull();

        assertThat(applicationResponseDTO.getValue())
                .isEqualTo(application.getValue());

        assertThat(applicationResponseDTO.getStatus())
                .isEqualTo(application.getStatus());
    }

    @Test
    void given_BetweenScoreLevelOneAndTwo_And_BetweenLevelOneAndTwoIncome_when_saveApplication_then_returnApplicationResponseDTO() {
        Deposit deposit = Deposit.builder()
                .id(1L)
                .depositType(DepositType.HOUSE)
                .value(BigDecimal.valueOf(20000))
                .build();

        Application application = getApplication(24000, ApplicationStatus.ACCEPTED, deposit);

        Mockito.when(creditScoreService.findCreditScoreByCustomerId(1L)).thenReturn(new CreditScoreResponseDTO(LEVEL_1_CREDIT_SCORE_FOR_CUSTOMER));
        Mockito.when(applicationRepository.save(ArgumentMatchers.any())).thenReturn(application);

        DepositRequestDTO depositRequestDTO = new DepositRequestDTO(
                DepositType.HOUSE,
                BigDecimal.valueOf(20000)
        );

        ApplicationRequestDTO applicationRequestDTO = getApplicationRequestDTO(BigDecimal.valueOf(7500), depositRequestDTO);

        applicationRequestDTO.setDepositRequestDTO(depositRequestDTO);

        ApplicationResponseDTO applicationResponseDTO = applicationService.saveApplication(applicationRequestDTO);

        assertThat(applicationResponseDTO)
                .isNotNull();

        assertThat(applicationResponseDTO.getDepositResponseDTO())
                .isNotNull();

        assertThat(applicationResponseDTO.getValue())
                .isEqualTo(application.getValue());

        assertThat(applicationResponseDTO.getStatus())
                .isEqualTo(application.getStatus());
    }

    @Test
    void given_BetweenScoreLevelOneAndTwo_And_GreaterLevelTwoIncome_when_saveApplication_then_returnApplicationResponseDTO() {
        Deposit deposit = Deposit.builder()
                .id(1L)
                .depositType(DepositType.HOUSE)
                .value(BigDecimal.valueOf(20000))
                .build();

        Application application = getApplication(12000, ApplicationStatus.ACCEPTED, deposit);

        Mockito.when(creditScoreService.findCreditScoreByCustomerId(1L)).thenReturn(new CreditScoreResponseDTO(LEVEL_1_CREDIT_SCORE_FOR_CUSTOMER));
        Mockito.when(applicationRepository.save(ArgumentMatchers.any())).thenReturn(application);

        DepositRequestDTO depositRequestDTO = new DepositRequestDTO(
                DepositType.HOUSE,
                BigDecimal.valueOf(20000)
        );

        ApplicationRequestDTO applicationRequestDTO = getApplicationRequestDTO(BigDecimal.valueOf(10000), depositRequestDTO);

        applicationRequestDTO.setDepositRequestDTO(depositRequestDTO);

        ApplicationResponseDTO applicationResponseDTO = applicationService.saveApplication(applicationRequestDTO);

        assertThat(applicationResponseDTO)
                .isNotNull();

        assertThat(applicationResponseDTO.getDepositResponseDTO())
                .isNotNull();

        assertThat(applicationResponseDTO.getValue())
                .isEqualTo(application.getValue());

        assertThat(applicationResponseDTO.getStatus())
                .isEqualTo(application.getStatus());
    }

    @Test
    void given_LowScoreAndIncome_when_saveApplication_then_returnRejectedApplicationResponseDTO() {
        Application application = getApplication(0, ApplicationStatus.REJECTED, null);

        Mockito.when(creditScoreService.findCreditScoreByCustomerId(1L)).thenReturn(new CreditScoreResponseDTO(LEVEL_0_CREDIT_SCORE_FOR_CUSTOMER));
        Mockito.when(applicationRepository.save(ArgumentMatchers.any())).thenReturn(application);

        ApplicationRequestDTO applicationRequestDTO = getApplicationRequestDTO(BigDecimal.valueOf(10000), null);

        ApplicationResponseDTO applicationResponseDTO = applicationService.saveApplication(applicationRequestDTO);

        assertThat(applicationResponseDTO)
                .isNotNull();

        assertThat(applicationResponseDTO.getDepositResponseDTO())
                .isNull();

        assertThat(applicationResponseDTO.getValue())
                .isEqualTo(application.getValue());

        assertThat(applicationResponseDTO.getStatus())
                .isEqualTo(application.getStatus());
    }

    @Test
    void given_validIdentityNumberAndBirthDate_when_getApplicationByIdentityNumberAndBirthDate_returnApplicationResponseDTOList() {
        Application application = getApplication(12000, ApplicationStatus.ACCEPTED, null);
        application.setId(1L);

        Mockito.when(applicationRepository.findApplicationByCustomerIdentityNumberAndCustomerBirthDate(customer.getIdentityNumber(), customer.getBirthDate()))
                .thenReturn(List.of(application));

        List<ApplicationResponseDTO> applicationResponseDTOList = applicationService.getApplicationByIdentityNumberAndBirthDate(customer.getIdentityNumber(), customer.getBirthDate());

        assertThat(applicationResponseDTOList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        ApplicationResponseDTO applicationResponseDTO = applicationResponseDTOList.get(0);

        assertThat(applicationResponseDTO.getId())
                .isEqualTo(application.getId());
        assertThat(applicationResponseDTO.getStatus())
                .isEqualTo(application.getStatus());
        assertThat(applicationResponseDTO.getValue())
                .isEqualTo(application.getValue());
    }

    @Test
    void given_invalidIdentityNumberAndBirthDate_when_getApplicationByIdentityNumberAndBirthDate_throwCsEntityNotFoundException() {
        Mockito.when(applicationRepository.findApplicationByCustomerIdentityNumberAndCustomerBirthDate(customer.getIdentityNumber(), customer.getBirthDate()))
                .thenReturn(Collections.emptyList());

        CsEntityNotFoundException entityNotFoundException = assertThrows(CsEntityNotFoundException.class,
                () -> applicationService.getApplicationByIdentityNumberAndBirthDate(customer.getIdentityNumber(), customer.getBirthDate()));

        assertThat(entityNotFoundException).isNotNull().hasMessage(ErrorMessage.APPLICATION_NOT_FOUND.getMessage());
    }

    private void mockCreditSystemSettings() {
        Mockito.when(creditSystemSettings.getDefaultCreditLimitMultiplier()).thenReturn(BigDecimal.valueOf(4));

        Mockito.when(creditSystemSettings.getLevelOneIncome()).thenReturn(BigDecimal.valueOf(5000));
        Mockito.when(creditSystemSettings.getLevelTwoIncome()).thenReturn(BigDecimal.valueOf(10000));

        Mockito.when(creditSystemSettings.getLevelOneResult()).thenReturn(BigDecimal.valueOf(10000));
        Mockito.when(creditSystemSettings.getLevelTwoResult()).thenReturn(BigDecimal.valueOf(20000));

        Mockito.when(creditSystemSettings.getLevelOneScore()).thenReturn(500);
        Mockito.when(creditSystemSettings.getLevelTwoScore()).thenReturn(1000);

        Mockito.when(creditSystemSettings.getLevelOneDepositMultiplier()).thenReturn(BigDecimal.valueOf(0.1));
        Mockito.when(creditSystemSettings.getLevelTwoDepositMultiplier()).thenReturn(BigDecimal.valueOf(0.2));
        Mockito.when(creditSystemSettings.getLevelThreeDepositMultiplier()).thenReturn(BigDecimal.valueOf(0.25));
        Mockito.when(creditSystemSettings.getLevelFourDepositMultiplier()).thenReturn(BigDecimal.valueOf(0.5));
    }

    private Application getApplication(Integer value, ApplicationStatus status, Deposit deposit) {
        return Application.builder()
                .customer(customer)
                .deposit(deposit)
                .status(status)
                .value(BigDecimal.valueOf(value))
                .build();
    }

    private ApplicationRequestDTO getApplicationRequestDTO(BigDecimal value, DepositRequestDTO depositRequestDTO) {
        return new ApplicationRequestDTO(
                IDENTITY_NUMBER,
                "FullName",
                value,
                customer.getPhoneNumber(),
                LocalDate.of(1990, 1, 1),
                depositRequestDTO);
    }
}

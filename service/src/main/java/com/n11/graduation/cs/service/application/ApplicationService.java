package com.n11.graduation.cs.service.application;

import com.n11.graduation.cs.config.CreditSystemSettings;
import com.n11.graduation.cs.constant.ApplicationStatus;
import com.n11.graduation.cs.constant.ErrorMessage;
import com.n11.graduation.cs.dto.application.ApplicationRequestDTO;
import com.n11.graduation.cs.dto.application.ApplicationResponseDTO;
import com.n11.graduation.cs.exception.CsEntityNotFoundException;
import com.n11.graduation.cs.model.Application;
import com.n11.graduation.cs.model.Customer;
import com.n11.graduation.cs.model.Deposit;
import com.n11.graduation.cs.repository.application.ApplicationRepository;
import com.n11.graduation.cs.service.creditscore.CreditScoreService;
import com.n11.graduation.cs.service.customer.CustomerService;
import com.n11.graduation.cs.service.notification.NotificationService;
import com.n11.graduation.cs.util.ConverterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final CreditScoreService creditScoreService;
    private final CustomerService customerService;
    private final CreditSystemSettings creditSystemSettings;
    private final NotificationService notificationService;

    @Transactional
    public ApplicationResponseDTO saveApplication(ApplicationRequestDTO applicationRequestDTO) {
        Customer customer = customerService.findUserByIdentityNumber(applicationRequestDTO.getIdentityNumber());

        Integer creditScoreForCustomer = creditScoreService.findCreditScoreByCustomerId(customer.getId()).getCreditScore();

        Application application = Application.builder().customer(customer).build();

        Deposit deposit = null;
        if (applicationRequestDTO.getDepositRequestDTO() != null) {
            deposit = ConverterUtil.convertDepositRequestToDeposit(applicationRequestDTO.getDepositRequestDTO(), application);
        }

        BigDecimal additionalValue;
        BigDecimal totalValue = BigDecimal.ZERO;
        ApplicationStatus status = ApplicationStatus.REJECTED;

        if (creditScoreForCustomer >= creditSystemSettings.getLevelOneScore() && creditScoreForCustomer < creditSystemSettings.getLevelTwoScore()) {
            if (applicationRequestDTO.getIncome().compareTo(creditSystemSettings.getLevelOneIncome()) < 0) {
                additionalValue = calculateAdditionalValueByDeposit(deposit, creditSystemSettings.getLevelOneDepositMultiplier());
                totalValue = creditSystemSettings.getLevelOneResult().add(additionalValue);
                status = ApplicationStatus.ACCEPTED;
            } else if (applicationRequestDTO.getIncome().compareTo(creditSystemSettings.getLevelOneIncome()) >= 0 && applicationRequestDTO.getIncome().compareTo(creditSystemSettings.getLevelTwoIncome()) < 0) {
                additionalValue = calculateAdditionalValueByDeposit(deposit, creditSystemSettings.getLevelTwoDepositMultiplier());
                totalValue = creditSystemSettings.getLevelTwoResult().add(additionalValue);
                status = ApplicationStatus.ACCEPTED;
            } else {
                additionalValue = calculateAdditionalValueByDeposit(deposit, creditSystemSettings.getLevelThreeDepositMultiplier());
                totalValue = applicationRequestDTO.getIncome().multiply(creditSystemSettings.getDefaultCreditLimitMultiplier().divide(BigDecimal.valueOf(2))).add(additionalValue);
                status = ApplicationStatus.ACCEPTED;
            }
        } else if (creditScoreForCustomer >= creditSystemSettings.getLevelTwoScore()) {
            additionalValue = calculateAdditionalValueByDeposit(deposit, creditSystemSettings.getLevelFourDepositMultiplier());
            totalValue = applicationRequestDTO.getIncome().multiply(creditSystemSettings.getDefaultCreditLimitMultiplier()).add(additionalValue);
            status = ApplicationStatus.ACCEPTED;
        }
        setApplicationFields(application, deposit, totalValue, status);

        Application savedApplication = applicationRepository.save(application);

        ApplicationResponseDTO applicationResponseDTO = ConverterUtil.convertApplicationAndDepositToApplicationResponseDTO(savedApplication);

        notificationService.sendSmsToCustomer(applicationResponseDTO);

        return applicationResponseDTO;
    }

    private void setApplicationFields(Application application, Deposit deposit, BigDecimal totalValue, ApplicationStatus status) {
        application.setStatus(status);
        application.setDeposit(deposit);
        application.setValue(totalValue);
    }

    private BigDecimal calculateAdditionalValueByDeposit(Deposit deposit, BigDecimal coefficient) {
        return deposit != null && deposit.getValue() != null
                ? deposit.getValue().multiply(coefficient)
                : BigDecimal.ZERO;
    }

    public List<ApplicationResponseDTO> getApplicationByIdentityNumberAndBirthDate(String identityNumber, LocalDate birthDate) {
        List<Application> applicationList = applicationRepository.findApplicationByCustomerIdentityNumberAndCustomerBirthDate(identityNumber, birthDate);

        if (applicationList.isEmpty()) {
            throw new CsEntityNotFoundException(ErrorMessage.APPLICATION_NOT_FOUND);
        }

        return applicationList.stream()
                .map(ConverterUtil::convertApplicationAndDepositToApplicationResponseDTO)
                .collect(Collectors.toList());
    }
}

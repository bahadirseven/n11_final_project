package com.n11.graduation.cs.util;

import com.n11.graduation.cs.dto.application.ApplicationResponseDTO;
import com.n11.graduation.cs.dto.creditscore.CreditScoreResponseDTO;
import com.n11.graduation.cs.dto.customer.CustomerRequestDTO;
import com.n11.graduation.cs.dto.customer.CustomerResponseDTO;
import com.n11.graduation.cs.dto.deposit.DepositRequestDTO;
import com.n11.graduation.cs.dto.deposit.DepositResponseDTO;
import com.n11.graduation.cs.model.Application;
import com.n11.graduation.cs.model.CreditScore;
import com.n11.graduation.cs.model.Customer;
import com.n11.graduation.cs.model.Deposit;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConverterUtil {

    public static Customer convertCustomerRequestToCustomer(CustomerRequestDTO customerRequestDTO) {
        return Customer.builder()
                .identityNumber(customerRequestDTO.getIdentityNumber())
                .fullname(customerRequestDTO.getFullName())
                .phoneNumber(customerRequestDTO.getPhoneNumber())
                .birthDate(customerRequestDTO.getBirthDate())
                .income(customerRequestDTO.getIncome())
                .build();
    }

    public static CustomerResponseDTO convertCustomerToCustomerResponse(Customer customer) {
        return CustomerResponseDTO.builder()
                .id(customer.getId())
                .identityNumber(customer.getIdentityNumber())
                .fullname(customer.getFullname())
                .phoneNumber(customer.getPhoneNumber())
                .birthDate(customer.getBirthDate())
                .creditScore(customer.getCreditScore().getScore())
                .income(customer.getIncome())
                .build();
    }

    public static void convertCustomerRequestToCustomerForUpdate(CustomerRequestDTO customerRequestDTO, Customer customer) {
        customer.setFullname(customerRequestDTO.getFullName());
        customer.setPhoneNumber(customerRequestDTO.getPhoneNumber());
        customer.setBirthDate(customerRequestDTO.getBirthDate());
        customer.setIncome(customerRequestDTO.getIncome());
    }

    public static CreditScore convertCustomerRequestToCreditScore(Customer customer) {
        return CreditScore.builder()
                .score(CreditScoreGeneratorUtil.generateRandomCreditScore())
                .customer(customer)
                .build();
    }

    public static CreditScoreResponseDTO convertCreditScoreToCreditScoreResponse(CreditScore creditScore) {
        return new CreditScoreResponseDTO(creditScore.getScore());
    }

    public static Deposit convertDepositRequestToDeposit(DepositRequestDTO depositRequestDTO, Application ownerApplication) {
        return Deposit.builder()
                .depositType(depositRequestDTO.getDepositType())
                .value(depositRequestDTO.getValue())
                .application(ownerApplication)
                .build();
    }

    public static ApplicationResponseDTO convertApplicationAndDepositToApplicationResponseDTO(Application application) {
        DepositResponseDTO depositResponseDTO = application.getDeposit() != null ? convertDepositToDepositResponse(application.getDeposit()) : null;

        return ApplicationResponseDTO.builder()
                .id(application.getId())
                .status(application.getStatus())
                .value(application.getValue())
                .depositResponseDTO(depositResponseDTO)
                .build();
    }

    public static DepositResponseDTO convertDepositToDepositResponse(Deposit deposit) {
        return new DepositResponseDTO(deposit.getDepositType(), deposit.getValue());
    }
}

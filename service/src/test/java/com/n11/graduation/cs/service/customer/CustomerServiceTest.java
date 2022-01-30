package com.n11.graduation.cs.service.customer;

import com.n11.graduation.cs.constant.ErrorMessage;
import com.n11.graduation.cs.dto.customer.CustomerRequestDTO;
import com.n11.graduation.cs.dto.customer.CustomerResponseDTO;
import com.n11.graduation.cs.exception.CsEntityNotFoundException;
import com.n11.graduation.cs.helper.CustomerHelper;
import com.n11.graduation.cs.model.CreditScore;
import com.n11.graduation.cs.model.Customer;
import com.n11.graduation.cs.repository.customer.CustomerRepository;
import com.n11.graduation.cs.util.ConverterUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class CustomerServiceTest {

    private CustomerService customerService;
    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerService(customerRepository);
    }

    @Test
    void given_validCustomerRequestDTO_when_createCustomer_then_returnCustomerResponseDTO() {
        CustomerRequestDTO customerRequestDTO = CustomerHelper.createValidCustomerRequestDTO();
        Customer expectedCustomer = ConverterUtil.convertCustomerRequestToCustomer(customerRequestDTO);

        expectedCustomer.setCreditScore(ConverterUtil.convertCustomerRequestToCreditScore(expectedCustomer));
        expectedCustomer.setId(1L);

        when(customerRepository.save(ArgumentMatchers.any())).thenReturn(expectedCustomer);

        CustomerResponseDTO actualCustomerResponse = customerService.createCustomer(customerRequestDTO);

        assertCustomerResponse(expectedCustomer, actualCustomerResponse);
    }

    @Test
    void given_validCustomerRequestDTO_when_updateCustomer_then_returnCustomerResponseDTO() {
        CustomerRequestDTO customerRequestDTO = CustomerHelper.createValidCustomerRequestDTO();

        Customer expectedCustomer = ConverterUtil.convertCustomerRequestToCustomer(customerRequestDTO);
        expectedCustomer.setId(1L);
        expectedCustomer.setCreditScore(ConverterUtil.convertCustomerRequestToCreditScore(expectedCustomer));

        when(customerRepository.findCustomerByIdentityNumber(expectedCustomer.getIdentityNumber())).thenReturn(Optional.of(expectedCustomer));
        when(customerRepository.save(expectedCustomer)).thenReturn(expectedCustomer);

        CustomerResponseDTO actualCustomerResponse = customerService.updateCustomer(customerRequestDTO);

        assertCustomerResponse(expectedCustomer, actualCustomerResponse);
    }

    @Test
    void given_invalidIdentityNumber_when_updateCustomer_then_throwCsEntityNotFoundException() {
        CustomerRequestDTO invalidRequestDTO = new CustomerRequestDTO();
        invalidRequestDTO.setIdentityNumber("invalidIdentity");

        Mockito.when(customerRepository.findCustomerByIdentityNumber("invalidIdentity")).thenReturn(Optional.ofNullable(null));

        CsEntityNotFoundException exception = assertThrows(CsEntityNotFoundException.class,
                () -> customerService.updateCustomer(invalidRequestDTO));

        assertNotNull(exception);
        assertThat(ErrorMessage.CUSTOMER_NOT_FOUND.getMessage())
                .isEqualTo(exception.getMessage());
    }

    @Test
    void given_validIdentityNumber_when_deleteCustomer_returnNothing() {
        String validIdentityNumber = "validIdentityNumber";
        Customer customer = new Customer();

        when(customerRepository.findCustomerByIdentityNumber(validIdentityNumber)).thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).delete(customer);
        assertDoesNotThrow(() -> customerService.deleteCustomerByIdentityNumber(validIdentityNumber));
    }

    @Test
    void given_invalidIdentityNumber_when_deleteCustomer_then_throwCsEntityNotFoundException() {
        Mockito.when(customerRepository.findCustomerByIdentityNumber("invalidIdentity")).thenReturn(Optional.ofNullable(null));
        CsEntityNotFoundException exception = assertThrows(CsEntityNotFoundException.class,
                () -> customerService.deleteCustomerByIdentityNumber("invalidIdentity"));

        assertNotNull(exception);
        assertThat(ErrorMessage.CUSTOMER_NOT_FOUND.getMessage())
                .isEqualTo(exception.getMessage());
    }

    @Test
    void given_validIdentityNumber_when_findUserByIdentityNumber_then_returnCustomer() {
        String validIdentityNumber = "validIdentity";
        Customer expectedCustomer = Customer.builder()
                .id(1L)
                .identityNumber(validIdentityNumber)
                .phoneNumber("phoneNumberTest")
                .birthDate(LocalDate.of(1990, 01, 01))
                .fullname("fullName")
                .income(BigDecimal.valueOf(1000))
                .build();

        CreditScore creditScore = new CreditScore(1L, 1000, expectedCustomer);

        expectedCustomer.setCreditScore(creditScore);

        Mockito.when(customerRepository.findCustomerByIdentityNumber(validIdentityNumber)).thenReturn(Optional.of(expectedCustomer));

        Customer actualCustomer = customerService.findUserByIdentityNumber(validIdentityNumber);

        assertThat(actualCustomer)
                .isNotNull();

        assertThat(actualCustomer.getId())
                .isNotNull()
                .isEqualTo(expectedCustomer.getId());

        assertThat(actualCustomer.getIdentityNumber())
                .isNotNull()
                .isEqualTo(expectedCustomer.getIdentityNumber());

        assertThat(actualCustomer.getBirthDate())
                .isNotNull()
                .isEqualTo(expectedCustomer.getBirthDate());

        assertThat(actualCustomer.getPhoneNumber())
                .isNotNull()
                .isEqualTo(expectedCustomer.getPhoneNumber());

        assertThat(actualCustomer.getFullname())
                .isNotNull()
                .isEqualTo(expectedCustomer.getFullname());

        assertThat(actualCustomer.getIncome())
                .isNotNull()
                .isEqualTo(expectedCustomer.getIncome());
    }

    @Test
    void given_invalidIdentityNumber_when_findUserByIdentityNumber_then_throwCsEntityNotFoundException() {
        Mockito.when(customerRepository.findCustomerByIdentityNumber("invalidIdentity")).thenReturn(Optional.ofNullable(null));
        CsEntityNotFoundException exception = assertThrows(CsEntityNotFoundException.class,
                () -> customerService.findUserByIdentityNumber("invalidIdentity"));

        assertNotNull(exception);
        assertThat(ErrorMessage.CUSTOMER_NOT_FOUND.getMessage())
                .isEqualTo(exception.getMessage());
    }

    private void assertCustomerResponse(Customer expectedCustomer, CustomerResponseDTO actualCustomerResponse) {
        assertThat(actualCustomerResponse)
                .isNotNull();

        assertThat(actualCustomerResponse.getId())
                .isNotNull()
                .isEqualTo(expectedCustomer.getId());

        assertThat(actualCustomerResponse.getBirthDate())
                .isNotNull()
                .isEqualTo(expectedCustomer.getBirthDate());

        assertThat(actualCustomerResponse.getIncome())
                .isNotNull()
                .isEqualTo(expectedCustomer.getIncome());

        assertThat(actualCustomerResponse.getFullname())
                .isNotNull()
                .isEqualTo(expectedCustomer.getFullname());

        assertThat(actualCustomerResponse.getCreditScore())
                .isNotNull()
                .isEqualTo(expectedCustomer.getCreditScore().getScore());

        assertThat(actualCustomerResponse.getPhoneNumber())
                .isNotNull()
                .isEqualTo(expectedCustomer.getPhoneNumber());

        assertThat(actualCustomerResponse.getIdentityNumber())
                .isNotNull()
                .isEqualTo(expectedCustomer.getIdentityNumber());
    }
}

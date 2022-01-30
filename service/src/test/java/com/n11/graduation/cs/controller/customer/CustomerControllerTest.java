package com.n11.graduation.cs.controller.customer;

import com.n11.graduation.cs.constant.ResponseMessage;
import com.n11.graduation.cs.dto.SuccessResponseDTO;
import com.n11.graduation.cs.dto.customer.CustomerRequestDTO;
import com.n11.graduation.cs.dto.customer.CustomerResponseDTO;
import com.n11.graduation.cs.helper.CustomerHelper;
import com.n11.graduation.cs.service.customer.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class CustomerControllerTest {
    private CustomerController customerController;
    @Mock
    private CustomerService customerService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        customerController = new CustomerController(customerService);
    }

    @Test
    void given_validBody_when_createCustomer_then_returnCustomerResponseDTO() {
        CustomerRequestDTO customerRequestDTO = CustomerHelper.createValidCustomerRequestDTO();
        CustomerResponseDTO expectedBody = CustomerHelper.createCustomerResponseDTO();

        when(customerService.createCustomer(customerRequestDTO)).thenReturn(expectedBody);

        ResponseEntity<CustomerResponseDTO> actualResponseEntity = customerController.createCustomer(customerRequestDTO);

        assertEquals(HttpStatus.CREATED, actualResponseEntity.getStatusCode());

        CustomerResponseDTO actualBody = actualResponseEntity.getBody();

        assertCustomerResponseDTO(actualBody, expectedBody);
    }

    @Test
    void given_validBody_when_updateCustomer_then_returnCustomerResponseDTO() {
        CustomerRequestDTO customerRequestDTO = CustomerHelper.createValidCustomerRequestDTO();
        CustomerResponseDTO expectedBody = CustomerHelper.createCustomerResponseDTO();

        when(customerService.updateCustomer(customerRequestDTO)).thenReturn(expectedBody);

        ResponseEntity<CustomerResponseDTO> actualResponseEntity = customerController.updateCustomer(customerRequestDTO);

        assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());

        CustomerResponseDTO actualBody = actualResponseEntity.getBody();

        assertCustomerResponseDTO(actualBody, expectedBody);
    }

    @Test
    void given_validIdentityNumber_when_deleteCustomer_then_returnSuccessResponseDTO() {
        String expectedIdentityNumber = "12345678901";
        doNothing().when(customerService).deleteCustomerByIdentityNumber(expectedIdentityNumber);

        ResponseEntity<SuccessResponseDTO> actualBody = customerController.deleteCustomerByIdentityNumber(expectedIdentityNumber);

        assertThat(actualBody).isNotNull();
        assertThat(actualBody.getBody()).isNotNull();
        assertThat(actualBody.getBody().getMessage()).isEqualTo(ResponseMessage.SUCCESS_DELETE.getMessage());

    }

    private void assertCustomerResponseDTO(CustomerResponseDTO actualBody, CustomerResponseDTO expectedBody) {
        assertThat(actualBody.getId())
                .isNotNull()
                .isEqualTo(expectedBody.getId());

        assertThat(actualBody.getIdentityNumber())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(expectedBody.getIdentityNumber());

        assertThat(actualBody.getFullname())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(expectedBody.getFullname());

        assertThat(actualBody.getPhoneNumber())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(expectedBody.getPhoneNumber());

        assertThat(actualBody.getIncome())
                .isNotNull()
                .isPositive()
                .isEqualTo(expectedBody.getIncome());

        assertThat(actualBody.getCreditScore())
                .isNotNull()
                .isBetween(1, 1900);

        assertThat(actualBody.getBirthDate())
                .isNotNull()
                .isEqualTo(expectedBody.getBirthDate());
    }
}

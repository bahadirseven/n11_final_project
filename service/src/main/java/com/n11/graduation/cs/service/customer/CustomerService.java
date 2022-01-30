package com.n11.graduation.cs.service.customer;

import com.n11.graduation.cs.constant.ErrorMessage;
import com.n11.graduation.cs.dto.customer.CustomerRequestDTO;
import com.n11.graduation.cs.dto.customer.CustomerResponseDTO;
import com.n11.graduation.cs.exception.CsEntityNotFoundException;
import com.n11.graduation.cs.model.Customer;
import com.n11.graduation.cs.repository.customer.CustomerRepository;
import com.n11.graduation.cs.util.ConverterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO) {
        Customer customer = ConverterUtil.convertCustomerRequestToCustomer(customerRequestDTO);

        customer.setCreditScore(
                ConverterUtil.convertCustomerRequestToCreditScore(customer));

        Customer savedCustomer = customerRepository.save(customer);

        return ConverterUtil.convertCustomerToCustomerResponse(savedCustomer);
    }

    public CustomerResponseDTO updateCustomer(CustomerRequestDTO customerRequestDTO) {
        Customer customer = customerRepository.findCustomerByIdentityNumber(customerRequestDTO.getIdentityNumber())
                .orElseThrow(() -> new CsEntityNotFoundException(ErrorMessage.CUSTOMER_NOT_FOUND));

        ConverterUtil.convertCustomerRequestToCustomerForUpdate(customerRequestDTO, customer);

        Customer updatedCustomer = customerRepository.save(customer);

        return ConverterUtil.convertCustomerToCustomerResponse(updatedCustomer);
    }

    @Transactional
    public void deleteCustomerByIdentityNumber(String identityNumber) {
        Customer customer = customerRepository.findCustomerByIdentityNumber(identityNumber)
                .orElseThrow(() -> new CsEntityNotFoundException(ErrorMessage.CUSTOMER_NOT_FOUND));

        customerRepository.delete(customer);
    }

    public Customer findUserByIdentityNumber(String identityNumber) {
        return customerRepository.findCustomerByIdentityNumber(identityNumber)
                .orElseThrow(() -> new CsEntityNotFoundException(ErrorMessage.CUSTOMER_NOT_FOUND));
    }
}

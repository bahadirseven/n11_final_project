package com.n11.graduation.cs.controller.customer;

import com.n11.graduation.cs.constant.ResponseMessage;
import com.n11.graduation.cs.dto.SuccessResponseDTO;
import com.n11.graduation.cs.dto.customer.CustomerRequestDTO;
import com.n11.graduation.cs.dto.customer.CustomerResponseDTO;
import com.n11.graduation.cs.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"v1/customers"})
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody @Valid CustomerRequestDTO customerRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerService.createCustomer(customerRequestDTO));
    }

    @PutMapping
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@RequestBody @Valid CustomerRequestDTO customerRequestDTO) {
        CustomerResponseDTO responseDTO = customerService.updateCustomer(customerRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{identityNumber}")
    public ResponseEntity<SuccessResponseDTO> deleteCustomerByIdentityNumber(
            @PathVariable("identityNumber") String identityNumber) {
        customerService.deleteCustomerByIdentityNumber(identityNumber);
        return ResponseEntity.ok(new SuccessResponseDTO(ResponseMessage.SUCCESS_DELETE.getMessage()));
    }
}
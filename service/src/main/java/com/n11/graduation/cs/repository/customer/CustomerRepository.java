package com.n11.graduation.cs.repository.customer;

import com.n11.graduation.cs.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findCustomerByIdentityNumber(String identityNumber);
}

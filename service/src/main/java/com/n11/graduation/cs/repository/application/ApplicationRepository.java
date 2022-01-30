package com.n11.graduation.cs.repository.application;

import com.n11.graduation.cs.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findApplicationByCustomerIdentityNumberAndCustomerBirthDate(String identityNumber, LocalDate birthDate);
}

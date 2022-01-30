package com.n11.graduation.cs.controller.application;

import com.n11.graduation.cs.dto.application.ApplicationRequestDTO;
import com.n11.graduation.cs.dto.application.ApplicationResponseDTO;
import com.n11.graduation.cs.helper.ApplicationHelper;
import com.n11.graduation.cs.service.application.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ApplicationControllerTest {
    private ApplicationController applicationController;
    @Mock
    private ApplicationService applicationService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        applicationController = new ApplicationController(applicationService);
    }

    @Test
    void given_validBody_when_saveApplication_then_returnApplicationResponseDTO() {
        ApplicationRequestDTO applicationRequestDTO = ApplicationHelper.createValidApplicationRequestDTO();
        ApplicationResponseDTO expectedBody = ApplicationHelper.createApplicationResponseDTO();

        when(applicationService.saveApplication(applicationRequestDTO)).thenReturn(expectedBody);

        ResponseEntity<ApplicationResponseDTO> actualResponseEntity = applicationController.saveApplication(applicationRequestDTO);

        assertEquals(HttpStatus.CREATED, actualResponseEntity.getStatusCode());

        ApplicationResponseDTO actualBody = actualResponseEntity.getBody();

        assertApplicationResponseDTO(actualBody, expectedBody);
    }

    @Test
    void given_validParameters_when_getApplicationByIdentityNumberAndBirthDate_then_returnApplicationResponseDTO() {
        ApplicationResponseDTO expectedResponseItem = ApplicationHelper.createApplicationResponseDTO();
        List<ApplicationResponseDTO> expectedBody = List.of(expectedResponseItem);

        String identityNumber = "12345678901";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);

        when(applicationService.getApplicationByIdentityNumberAndBirthDate(identityNumber, birthDate)).thenReturn(expectedBody);

        ResponseEntity<List<ApplicationResponseDTO>> actualResponseEntity = applicationController.getApplicationByIdentityNumberAndBirthDate(identityNumber, birthDate);

        assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());

        List<ApplicationResponseDTO> actualBody = actualResponseEntity.getBody();

        assertThat(actualBody)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        assertApplicationResponseDTO(actualBody.get(0), expectedBody.get(0));
    }

    private void assertApplicationResponseDTO(ApplicationResponseDTO actualBody, ApplicationResponseDTO expectedBody) {
        assertThat(actualBody.getId())
                .isNotNull()
                .isEqualTo(expectedBody.getId());

        assertThat(actualBody.getStatus())
                .isNotNull()
                .isEqualTo(expectedBody.getStatus());

        assertThat(actualBody.getValue())
                .isNotNull()
                .isPositive()
                .isEqualTo(expectedBody.getValue());

        assertThat(actualBody.getDepositResponseDTO())
                .isNotNull();

        assertThat(actualBody.getDepositResponseDTO().getType())
                .isNotNull()
                .isEqualTo(expectedBody.getDepositResponseDTO().getType());

        assertThat(actualBody.getDepositResponseDTO().getDepositValue())
                .isNotNull()
                .isEqualTo(expectedBody.getDepositResponseDTO().getDepositValue());
    }
}

package com.n11.graduation.cs.controller.application;

import com.n11.graduation.cs.dto.application.ApplicationCheckRequestDTO;
import com.n11.graduation.cs.dto.application.ApplicationRequestDTO;
import com.n11.graduation.cs.dto.application.ApplicationResponseDTO;
import com.n11.graduation.cs.service.application.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"v1/applications"})
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationResponseDTO> saveApplication(@RequestBody @Valid ApplicationRequestDTO applicationRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(applicationService.saveApplication(applicationRequestDTO));
    }

    @PostMapping("/check")
    public ResponseEntity<List<ApplicationResponseDTO>> getApplicationByIdentityNumberAndBirthDate(@RequestBody @Valid ApplicationCheckRequestDTO applicationCheckRequestDTO) {
        return ResponseEntity
                .ok()
                .body(applicationService.getApplicationByIdentityNumberAndBirthDate(
                        applicationCheckRequestDTO.getIdentityNumber(), applicationCheckRequestDTO.getBirthDate()));
    }
}

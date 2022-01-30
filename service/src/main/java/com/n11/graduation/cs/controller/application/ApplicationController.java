package com.n11.graduation.cs.controller.application;

import com.n11.graduation.cs.dto.application.ApplicationRequestDTO;
import com.n11.graduation.cs.dto.application.ApplicationResponseDTO;
import com.n11.graduation.cs.service.application.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
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

    @GetMapping("/user/{identity}")
    public ResponseEntity<List<ApplicationResponseDTO>> getApplicationByIdentityNumberAndBirthDate(
            @PathVariable("identity") String identityNumber,
            @RequestParam("birthDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate) {
        return ResponseEntity
                .ok()
                .body(applicationService.getApplicationByIdentityNumberAndBirthDate(
                        identityNumber, birthDate));
    }
}

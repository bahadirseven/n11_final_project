package com.n11.graduation.cs.service.notification;

import com.n11.graduation.cs.dto.application.ApplicationResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {
    public void sendSmsToCustomer(ApplicationResponseDTO applicationResponseDTO) {
        log.info(String.format("Başvuru Sonucu: %s / Kredi Değeri: %s", applicationResponseDTO.getStatus().getMessage(), applicationResponseDTO.getValue()));
    }
}

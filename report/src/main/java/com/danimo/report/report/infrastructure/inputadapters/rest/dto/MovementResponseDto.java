package com.danimo.report.report.infrastructure.inputadapters.rest.dto;

import com.danimo.report.report.domain.Movement;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class MovementResponseDto {
    private final UUID id;
    private final String serviceType;
    private final String paymentType;
    private final String description;
    private final BigDecimal amount;
    private final UUID locationId;
    private final String locationName;
    private final LocalDateTime createdAt;

    public static MovementResponseDto fromDomain(Movement movement) {
        return new MovementResponseDto(
                movement.getId().getId(),
                movement.getServiceType().toString().toUpperCase(),
                movement.getPaymentType().toString().toUpperCase(),
                movement.getDescription(),
                movement.getAmount().getAmount(),
                movement.getLocationId(),
                movement.getLocationName(),
                movement.getCreatedAt().getCreatedAt()

        );
    }
}

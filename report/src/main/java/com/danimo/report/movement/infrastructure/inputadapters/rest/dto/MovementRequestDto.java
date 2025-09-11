package com.danimo.report.movement.infrastructure.inputadapters.rest.dto;

import com.danimo.report.movement.application.usecases.createmovement.CreateMovementDto;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
public class MovementRequestDto {
    private String serviceType;
    private String paymentType;
    private String description;
    private BigDecimal amount;
    private UUID locationId;
    private String locationName;

    public CreateMovementDto toAppli(){
        return new CreateMovementDto(serviceType, paymentType, description, amount, locationId, locationName);
    }
}

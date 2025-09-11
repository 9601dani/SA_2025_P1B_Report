package com.danimo.report.movement.application.usecases.createmovement;

import com.danimo.report.movement.domain.*;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
public class CreateMovementDto {
    private String serviceType;
    private String paymentType;
    private String description;
    private BigDecimal amount;
    private UUID locationId;
    private String locationName;

    public Movement toDomain(){
        return new Movement(
                MovementId.generate(),
                ServiceType.fromString(serviceType),
                PaymentType.fromString(paymentType),
                description,
                MovementAmount.fromBigDecimal(amount),
                locationId,
                locationName,
                MovementCreatedAt.generate()
        );
    }
}

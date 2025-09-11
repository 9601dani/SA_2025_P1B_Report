package com.danimo.report.movement.domain;

import com.danimo.report.common.domain.annotations.DomainEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@DomainEntity
@Getter
@AllArgsConstructor
public class Movement {

    private final MovementId id;
    private final ServiceType serviceType;
    private final PaymentType paymentType;
    private final String description;
    private final MovementAmount amount;
    private final UUID locationId;
    private final String locationName;
    private final MovementCreatedAt createdAt;


}

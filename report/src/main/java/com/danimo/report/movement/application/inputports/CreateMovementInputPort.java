package com.danimo.report.movement.application.inputports;

import com.danimo.report.movement.application.usecases.createmovement.CreateMovementDto;
import com.danimo.report.movement.domain.Movement;

public interface CreateMovementInputPort {
    Movement createMovement(CreateMovementDto dto);
}

package com.danimo.report.report.application.inputports;

import com.danimo.report.report.application.usecases.createmovement.CreateMovementDto;
import com.danimo.report.report.domain.Movement;

public interface CreateMovementInputPort {
    Movement createMovement(CreateMovementDto dto);
}

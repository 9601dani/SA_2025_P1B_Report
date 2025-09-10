package com.danimo.report.report.application.outputports.persistence;

import com.danimo.report.report.application.usecases.createmovement.CreateMovementDto;
import com.danimo.report.report.domain.Movement;

public interface StoringMovementsOutputPort {
    Movement save(Movement movement);
}

package com.danimo.report.movement.application.outputports.persistence;

import com.danimo.report.movement.domain.Movement;

public interface StoringMovementsOutputPort {
    Movement save(Movement movement);
}

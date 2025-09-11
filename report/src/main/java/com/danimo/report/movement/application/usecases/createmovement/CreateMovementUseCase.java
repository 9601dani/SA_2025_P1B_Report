package com.danimo.report.movement.application.usecases.createmovement;

import com.danimo.report.common.application.annotations.UseCase;
import com.danimo.report.movement.application.inputports.CreateMovementInputPort;
import com.danimo.report.movement.application.outputports.persistence.StoringMovementsOutputPort;
import com.danimo.report.movement.domain.Movement;
import org.springframework.beans.factory.annotation.Autowired;
@UseCase
public class CreateMovementUseCase implements CreateMovementInputPort {

    private final StoringMovementsOutputPort storingMovementsOutputPort;
    @Autowired
    public CreateMovementUseCase(StoringMovementsOutputPort storingMovementsOutputPort) {
        this.storingMovementsOutputPort = storingMovementsOutputPort;
    }

    @Override
    public Movement createMovement(CreateMovementDto dto) {
        return storingMovementsOutputPort.save(dto.toDomain());
    }
}

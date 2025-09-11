package com.danimo.report.movement.application.usecases.findmovementbylocation;

import com.danimo.report.common.application.annotations.UseCase;
import com.danimo.report.movement.application.inputports.FindingMovementByLocationInputPort;
import com.danimo.report.movement.application.outputports.persistence.FindingMovementsByLocationOutputPort;
import com.danimo.report.movement.domain.Movement;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@UseCase
public class FindMovementsByLocationUseCase implements FindingMovementByLocationInputPort {
    private final FindingMovementsByLocationOutputPort findingMovementsByLocationOutputPort;

    @Autowired
    public FindMovementsByLocationUseCase(FindingMovementsByLocationOutputPort findingMovementsByLocationOutputPort) {
        this.findingMovementsByLocationOutputPort = findingMovementsByLocationOutputPort;
    }

    @Override
    public List<Movement> findMovementByLocation(String locationId, LocalDateTime from, LocalDateTime to) {
        return this.findingMovementsByLocationOutputPort.findMovementsByLocation(locationId,from,to);
    }
}

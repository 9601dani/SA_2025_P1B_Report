package com.danimo.report.movement.application.inputports;

import com.danimo.report.movement.domain.Movement;

import java.time.LocalDateTime;
import java.util.List;

public interface FindingMovementByLocationInputPort {
    List<Movement> findMovementByLocation(String locationId, LocalDateTime from, LocalDateTime to);
}

package com.danimo.report.movement.application.outputports.persistence;

import com.danimo.report.movement.domain.Movement;

import java.time.LocalDateTime;
import java.util.List;

public interface FindingMovementsByDatesOutputPort {
    List<Movement> findMovementsByDates(LocalDateTime from, LocalDateTime to);
}

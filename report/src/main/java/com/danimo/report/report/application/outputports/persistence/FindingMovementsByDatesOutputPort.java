package com.danimo.report.report.application.outputports.persistence;

import com.danimo.report.report.domain.Movement;

import java.time.LocalDateTime;
import java.util.List;

public interface FindingMovementsByDatesOutputPort {
    List<Movement> findMovementsByDates(LocalDateTime from, LocalDateTime to);
}

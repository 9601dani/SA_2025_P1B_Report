package com.danimo.report.report.application.inputports;

import com.danimo.report.report.domain.Movement;

import java.time.LocalDateTime;
import java.util.List;

public interface FindingMovementByLocationInputPort {
    List<Movement> findMovementByLocation(String locationId, LocalDateTime from, LocalDateTime to);
}

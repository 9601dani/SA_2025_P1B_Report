package com.danimo.report.report.application.outputports.persistence;

import com.danimo.report.report.domain.Movement;

import java.time.LocalDateTime;
import java.util.List;

public interface FindingMovementsByLocationOutputPort {
    List<Movement> findMovementsByLocation(String location, LocalDateTime from, LocalDateTime to);
}

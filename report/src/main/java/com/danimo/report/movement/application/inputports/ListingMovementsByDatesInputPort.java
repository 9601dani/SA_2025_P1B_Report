package com.danimo.report.movement.application.inputports;

import com.danimo.report.movement.domain.Movement;

import java.time.LocalDateTime;
import java.util.List;

public interface ListingMovementsByDatesInputPort {
    List<Movement> getListingMovementsByDates(LocalDateTime from, LocalDateTime to);
}

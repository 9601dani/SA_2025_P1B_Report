package com.danimo.report.report.application.inputports;

import com.danimo.report.report.domain.Movement;

import java.time.LocalDateTime;
import java.util.List;

public interface ListingMovementsByDatesInputPort {
    List<Movement> getListingMovementsByDates(LocalDateTime from, LocalDateTime to);
}

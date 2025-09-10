package com.danimo.report.report.application.usecases.listmovementsbydate;

import com.danimo.report.common.application.annotations.UseCase;
import com.danimo.report.report.application.inputports.ListingMovementsByDatesInputPort;
import com.danimo.report.report.application.outputports.persistence.FindingMovementsByDatesOutputPort;
import com.danimo.report.report.domain.Movement;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@UseCase
public class FindMovementsByDatesUseCase implements ListingMovementsByDatesInputPort {
    private final FindingMovementsByDatesOutputPort findingMovementsByDatesOutputPort;

    @Autowired
    public FindMovementsByDatesUseCase(FindingMovementsByDatesOutputPort findingMovementsByDatesOutputPort) {
        this.findingMovementsByDatesOutputPort = findingMovementsByDatesOutputPort;
    }

    @Override
    public List<Movement> getListingMovementsByDates(LocalDateTime from, LocalDateTime to) {
        return findingMovementsByDatesOutputPort.findMovementsByDates(from, to);
    }
}

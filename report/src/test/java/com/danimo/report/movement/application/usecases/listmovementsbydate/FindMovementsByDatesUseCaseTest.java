package com.danimo.report.movement.application.usecases.listmovementsbydate;

import com.danimo.report.movement.application.outputports.persistence.FindingMovementsByDatesOutputPort;
import com.danimo.report.movement.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindMovementsByDatesUseCaseTest {

    private static final String SERVICE_TYPE = "HOTEL";
    private static final String PAYMENT_TYPE = "CREDIT";
    private static final String DESCRIPTION = "Movement Test";
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(100);
    private static final LocalDateTime CREATED_AT = LocalDateTime.now();

    private FindingMovementsByDatesOutputPort outputPort;
    private FindMovementsByDatesUseCase useCase;

    @BeforeEach
    void setUp() {
        outputPort = mock(FindingMovementsByDatesOutputPort.class);
        useCase = new FindMovementsByDatesUseCase(outputPort);
    }

    private Movement buildMovement() {
        return new Movement(
                MovementId.generate(),
                ServiceType.fromString(SERVICE_TYPE),
                PaymentType.fromString(PAYMENT_TYPE),
                DESCRIPTION,
                MovementAmount.fromBigDecimal(AMOUNT),
                null,
                "Store-1",
                MovementCreatedAt.fromLocalDateTime(CREATED_AT)
        );
    }

    @Test
    void getListingMovementsByDates_returnsListOfMovements() {
        // Arrange
        LocalDateTime from = CREATED_AT.minusDays(1);
        LocalDateTime to = CREATED_AT.plusDays(1);
        Movement movement = buildMovement();
        when(outputPort.findMovementsByDates(from, to)).thenReturn(List.of(movement));

        // Act
        List<Movement> result = useCase.getListingMovementsByDates(from, to);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(DESCRIPTION, result.get(0).getDescription());

        ArgumentCaptor<LocalDateTime> fromCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        ArgumentCaptor<LocalDateTime> toCaptor = ArgumentCaptor.forClass(LocalDateTime.class);

        verify(outputPort).findMovementsByDates(fromCaptor.capture(), toCaptor.capture());

        assertEquals(from, fromCaptor.getValue());
        assertEquals(to, toCaptor.getValue());
    }
}

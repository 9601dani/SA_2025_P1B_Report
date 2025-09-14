package com.danimo.report.movement.application.usecases.findmovementbylocation;

import com.danimo.report.movement.application.outputports.persistence.FindingMovementsByLocationOutputPort;
import com.danimo.report.movement.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindMovementsByLocationUseCaseTest {

    private static final String LOCATION_ID = "Store-1";
    private static final String SERVICE_TYPE = "HOTEL";
    private static final String PAYMENT_TYPE = "DEBIT";
    private static final String DESCRIPTION = "Movement Test";
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(100);
    private static final LocalDateTime CREATED_AT = LocalDateTime.now();

    private FindingMovementsByLocationOutputPort outputPort;
    private FindMovementsByLocationUseCase useCase;

    @BeforeEach
    void setUp() {
        outputPort = mock(FindingMovementsByLocationOutputPort.class);
        useCase = new FindMovementsByLocationUseCase(outputPort);
    }

    private Movement buildMovement() {
        return new Movement(
                MovementId.generate(),
                ServiceType.fromString(SERVICE_TYPE),
                PaymentType.fromString(PAYMENT_TYPE),
                DESCRIPTION,
                MovementAmount.fromBigDecimal(AMOUNT),
                null,
                LOCATION_ID,
                MovementCreatedAt.fromLocalDateTime(CREATED_AT)
        );
    }

    @Test
    void findMovementByLocation_returnsListOfMovements() {
        // Arrange
        LocalDateTime from = CREATED_AT.minusDays(1);
        LocalDateTime to = CREATED_AT.plusDays(1);
        Movement movement = buildMovement();
        when(outputPort.findMovementsByLocation(LOCATION_ID, from, to)).thenReturn(List.of(movement));

        // Act
        List<Movement> result = useCase.findMovementByLocation(LOCATION_ID, from, to);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(DESCRIPTION, result.get(0).getDescription());

        ArgumentCaptor<String> locationCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<LocalDateTime> fromCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        ArgumentCaptor<LocalDateTime> toCaptor = ArgumentCaptor.forClass(LocalDateTime.class);

        verify(outputPort).findMovementsByLocation(locationCaptor.capture(), fromCaptor.capture(), toCaptor.capture());

        assertEquals(LOCATION_ID, locationCaptor.getValue());
        assertEquals(from, fromCaptor.getValue());
        assertEquals(to, toCaptor.getValue());
    }
}

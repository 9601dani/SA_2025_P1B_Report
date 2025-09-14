package com.danimo.report.movement.application.usecases.createmovement;

import com.danimo.report.movement.application.outputports.persistence.StoringMovementsOutputPort;
import com.danimo.report.movement.domain.Movement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateMovementUseCaseTest {

    private static final String SERVICE_TYPE = "HOTEL";
    private static final String PAYMENT_TYPE = "DEBIT";
    private static final String DESCRIPTION = "Test Movement";
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(100);
    private static final UUID LOCATION_ID = UUID.randomUUID();
    private static final String LOCATION_NAME = "Store A";

    private StoringMovementsOutputPort storingMovementsOutputPort;
    private CreateMovementUseCase useCase;

    @BeforeEach
    void setUp() {
        storingMovementsOutputPort = mock(StoringMovementsOutputPort.class);
        useCase = new CreateMovementUseCase(storingMovementsOutputPort);
    }

    @Test
    void createMovement_callsPortAndReturnsMovement() {
        // Arrange
        CreateMovementDto dto = new CreateMovementDto(
                SERVICE_TYPE,
                PAYMENT_TYPE,
                DESCRIPTION,
                AMOUNT,
                LOCATION_ID,
                LOCATION_NAME
        );
        Movement movement = dto.toDomain();
        when(storingMovementsOutputPort.save(any(Movement.class))).thenReturn(movement);

        // Act
        Movement result = useCase.createMovement(dto);

        // Assert
        assertNotNull(result);
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(AMOUNT, result.getAmount().getAmount());
        assertEquals(SERVICE_TYPE, result.getServiceType().name());
        assertEquals(PAYMENT_TYPE, result.getPaymentType().name());
        assertEquals(LOCATION_ID, result.getLocationId());
        assertEquals(LOCATION_NAME, result.getLocationName());

        ArgumentCaptor<Movement> captor = ArgumentCaptor.forClass(Movement.class);
        verify(storingMovementsOutputPort).save(captor.capture());
        assertEquals(DESCRIPTION, captor.getValue().getDescription());
    }
}

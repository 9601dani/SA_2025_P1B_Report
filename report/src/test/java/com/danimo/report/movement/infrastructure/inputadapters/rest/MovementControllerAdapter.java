package com.danimo.report.movement.infrastructure.inputadapters.rest;

import com.danimo.report.movement.application.inputports.CreateMovementInputPort;
import com.danimo.report.movement.application.inputports.FindingMovementByLocationInputPort;
import com.danimo.report.movement.application.inputports.ListingMovementsByDatesInputPort;
import com.danimo.report.movement.application.usecases.createmovement.CreateMovementDto;
import com.danimo.report.movement.domain.*;
import com.danimo.report.movement.infrastructure.inputadapters.rest.dto.MovementRequestDto;
import com.danimo.report.movement.infrastructure.inputadapters.rest.dto.MovementResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovementControllerAdapterTest {

    private static final String SERVICE_TYPE = "HOTEL";
    private static final String PAYMENT_TYPE = "DEBIT";
    private static final String DESCRIPTION = "Test Movement";
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(100);
    private static final UUID LOCATION_ID = UUID.randomUUID();
    private static final String LOCATION_NAME = "Store A";
    private static final LocalDateTime CREATED_AT = LocalDateTime.now();

    private CreateMovementInputPort createMovementInputPort;
    private FindingMovementByLocationInputPort findingMovementByLocationInputPort;
    private ListingMovementsByDatesInputPort listingMovementsByDatesInputPort;
    private MovementControllerAdapter controller;

    @BeforeEach
    void setUp() {
        createMovementInputPort = mock(CreateMovementInputPort.class);
        findingMovementByLocationInputPort = mock(FindingMovementByLocationInputPort.class);
        listingMovementsByDatesInputPort = mock(ListingMovementsByDatesInputPort.class);
        controller = new MovementControllerAdapter(
                createMovementInputPort,
                findingMovementByLocationInputPort,
                listingMovementsByDatesInputPort
        );
    }

    private Movement buildMovement() {
        return new Movement(
                MovementId.generate(),
                ServiceType.fromString(SERVICE_TYPE),
                PaymentType.fromString(PAYMENT_TYPE),
                DESCRIPTION,
                MovementAmount.fromBigDecimal(AMOUNT),
                LOCATION_ID,
                LOCATION_NAME,
                MovementCreatedAt.fromLocalDateTime(CREATED_AT)
        );
    }

    @Test
    void createRoom_createsMovementAndReturnsResponse() {
        // Arrange
        MovementRequestDto dto = new MovementRequestDto(
                SERVICE_TYPE,
                PAYMENT_TYPE,
                DESCRIPTION,
                AMOUNT,
                LOCATION_ID,
                LOCATION_NAME
        );
        CreateMovementDto createDto = dto.toAppli();
        Movement movement = buildMovement();

        when(createMovementInputPort.createMovement(any(CreateMovementDto.class))).thenReturn(movement);

        // Act
        ResponseEntity<MovementResponseDto> response = controller.createRoom(dto);

        // Assert
        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(DESCRIPTION, response.getBody().getDescription());

        ArgumentCaptor<CreateMovementDto> captor = ArgumentCaptor.forClass(CreateMovementDto.class);
        verify(createMovementInputPort).createMovement(captor.capture());
        assertEquals(DESCRIPTION, captor.getValue().getDescription());
    }

    @Test
    void getMovementsByLocationAndDates_returnsList() {
        // Arrange
        LocalDateTime from = CREATED_AT.minusDays(1);
        LocalDateTime to = CREATED_AT.plusDays(1);
        Movement movement = buildMovement();
        when(findingMovementByLocationInputPort.findMovementByLocation(
                eq(LOCATION_NAME),
                eq(from),
                eq(to)
        )).thenReturn(List.of(movement));

        // Act
        ResponseEntity<List<MovementResponseDto>> response = controller.getMovementsByLocationAndDates(
                LOCATION_NAME,
                from.toString(),
                to.toString()
        );

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getBody().size());
        assertEquals(DESCRIPTION, response.getBody().get(0).getDescription());
    }

    @Test
    void getMovementsByDates_returnsList() {
        // Arrange
        LocalDateTime from = CREATED_AT.minusDays(1);
        LocalDateTime to = CREATED_AT.plusDays(1);
        Movement movement = buildMovement();
        when(listingMovementsByDatesInputPort.getListingMovementsByDates(from, to)).thenReturn(List.of(movement));

        // Act
        ResponseEntity<List<MovementResponseDto>> response = controller.getMovementsByDates(
                from.toString(),
                to.toString()
        );

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getBody().size());
        assertEquals(DESCRIPTION, response.getBody().get(0).getDescription());
    }
}

package com.danimo.report.movement.infrastructure.outputadapters.persistence;

import com.danimo.report.movement.domain.*;
import com.danimo.report.movement.infrastructure.outputadapters.persistence.entity.MovementDbEntity;
import com.danimo.report.movement.infrastructure.outputadapters.persistence.entity.mapper.MovementPersistenceMapper;
import com.danimo.report.movement.infrastructure.outputadapters.persistence.repository.MovementDbEntityJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovementRepositoryOutputAdapterTest {

    private static final UUID MOVEMENT_ID = UUID.randomUUID();
    private static final String SERVICE_TYPE = "HOTEL";
    private static final String PAYMENT_TYPE = "CREDIT";
    private static final String DESCRIPTION = "Test Movement";
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(100);
    private static final UUID LOCATION_ID = UUID.randomUUID();
    private static final String LOCATION_NAME = "Store A";
    private static final LocalDateTime CREATED_AT = LocalDateTime.now();

    private MovementDbEntityJpaRepository jpaRepository;
    private MovementPersistenceMapper mapper;
    private MovementRepositoryOutputAdapter adapter;

    @BeforeEach
    void setUp() {
        jpaRepository = mock(MovementDbEntityJpaRepository.class);
        mapper = mock(MovementPersistenceMapper.class);
        adapter = new MovementRepositoryOutputAdapter(jpaRepository, mapper);
    }

    private Movement buildMovement() {
        return new Movement(
                MovementId.fromUuid(MOVEMENT_ID),
                ServiceType.fromString(SERVICE_TYPE),
                PaymentType.fromString(PAYMENT_TYPE),
                DESCRIPTION,
                MovementAmount.fromBigDecimal(AMOUNT),
                LOCATION_ID,
                LOCATION_NAME,
                MovementCreatedAt.fromLocalDateTime(CREATED_AT)
        );
    }

    private MovementDbEntity buildDbEntity() {
        return new MovementDbEntity(
                MOVEMENT_ID,
                ServiceType.fromString(SERVICE_TYPE),
                PaymentType.fromString(PAYMENT_TYPE),
                DESCRIPTION,
                AMOUNT,
                LOCATION_ID,
                LOCATION_NAME,
                CREATED_AT
        );
    }

    @Test
    void findMovementsByDates_returnsMappedList() {
        // Arrange
        LocalDateTime from = CREATED_AT.minusDays(1);
        LocalDateTime to = CREATED_AT.plusDays(1);
        MovementDbEntity entity = buildDbEntity();
        Movement movement = buildMovement();

        when(jpaRepository.findByCreatedAtBetween(from, to)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(movement);

        // Act
        List<Movement> result = adapter.findMovementsByDates(from, to);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(DESCRIPTION, result.get(0).getDescription());
        verify(jpaRepository).findByCreatedAtBetween(from, to);
        verify(mapper).toDomain(entity);
    }

    @Test
    void findMovementsByLocation_returnsMappedList() {
        // Arrange
        LocalDateTime from = CREATED_AT.minusDays(1);
        LocalDateTime to = CREATED_AT.plusDays(1);
        MovementDbEntity entity = buildDbEntity();
        Movement movement = buildMovement();

        when(jpaRepository.findByLocationIdAndCreatedAtBetween(LOCATION_ID, from, to)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(movement);

        // Act
        List<Movement> result = adapter.findMovementsByLocation(LOCATION_ID.toString(), from, to);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(DESCRIPTION, result.get(0).getDescription());
        verify(jpaRepository).findByLocationIdAndCreatedAtBetween(LOCATION_ID, from, to);
        verify(mapper).toDomain(entity);
    }

    @Test
    void save_savesAndReturnsMappedMovement() {
        // Arrange
        Movement movement = buildMovement();
        MovementDbEntity entity = buildDbEntity();

        when(mapper.toDbEntity(movement)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(movement);

        // Act
        Movement result = adapter.save(movement);

        // Assert
        assertNotNull(result);
        assertEquals(DESCRIPTION, result.getDescription());

        ArgumentCaptor<MovementDbEntity> captor = ArgumentCaptor.forClass(MovementDbEntity.class);
        verify(mapper).toDbEntity(movement);
        verify(jpaRepository).save(captor.capture());
        assertEquals(DESCRIPTION, captor.getValue().getDescription());
        verify(mapper).toDomain(entity);
    }
}

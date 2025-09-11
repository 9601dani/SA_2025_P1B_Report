package com.danimo.report.movement.infrastructure.outputadapters.persistence;

import com.danimo.report.common.infrastructure.annotations.PersistenceAdapter;
import com.danimo.report.movement.application.outputports.persistence.FindingMovementsByDatesOutputPort;
import com.danimo.report.movement.application.outputports.persistence.FindingMovementsByLocationOutputPort;
import com.danimo.report.movement.application.outputports.persistence.StoringMovementsOutputPort;
import com.danimo.report.movement.domain.Movement;
import com.danimo.report.movement.infrastructure.outputadapters.persistence.entity.MovementDbEntity;
import com.danimo.report.movement.infrastructure.outputadapters.persistence.entity.mapper.MovementPersistenceMapper;
import com.danimo.report.movement.infrastructure.outputadapters.persistence.repository.MovementDbEntityJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@PersistenceAdapter
public class MovementRepositoryOutputAdapter implements FindingMovementsByDatesOutputPort,
        FindingMovementsByLocationOutputPort, StoringMovementsOutputPort {

    private final MovementDbEntityJpaRepository movementDbEntityJpaRepository;
    private final MovementPersistenceMapper movementPersistenceMapper;

    @Autowired
    public MovementRepositoryOutputAdapter(MovementDbEntityJpaRepository movementDbEntityJpaRepository, MovementPersistenceMapper movementPersistenceMapper) {
        this.movementDbEntityJpaRepository = movementDbEntityJpaRepository;
        this.movementPersistenceMapper = movementPersistenceMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movement> findMovementsByDates(LocalDateTime from, LocalDateTime to) {
        return movementDbEntityJpaRepository.findByCreatedAtBetween(from, to)
                .stream()
                .map(movementPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movement> findMovementsByLocation(String location, LocalDateTime from, LocalDateTime to) {
        return movementDbEntityJpaRepository.findByLocationIdAndCreatedAtBetween(
                        UUID.fromString(location), from, to)
                .stream()
                .map(movementPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Movement save(Movement movement) {
        MovementDbEntity entity = movementPersistenceMapper.toDbEntity(movement);
        MovementDbEntity saved = movementDbEntityJpaRepository.save(entity);
        return movementPersistenceMapper.toDomain(saved);
    }
}

package com.danimo.report.report.infrastructure.inputadapters.rest;

import com.danimo.report.common.infrastructure.annotations.WebAdapter;
import com.danimo.report.report.application.inputports.CreateMovementInputPort;
import com.danimo.report.report.application.inputports.FindingMovementByLocationInputPort;
import com.danimo.report.report.application.inputports.ListingMovementsByDatesInputPort;
import com.danimo.report.report.application.usecases.createmovement.CreateMovementDto;
import com.danimo.report.report.domain.Movement;
import com.danimo.report.report.infrastructure.inputadapters.rest.dto.MovementRequestDto;
import com.danimo.report.report.infrastructure.inputadapters.rest.dto.MovementResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Reports", description = "Operaciones relacionadas a los reportes")
@RestController
@RequestMapping("/v1/reports")
@WebAdapter
public class MovementControllerAdapter {
    private final CreateMovementInputPort createMovementInputPort;
    private final FindingMovementByLocationInputPort findingMovementByLocationInputPort;
    private final ListingMovementsByDatesInputPort listingMovementsByDatesInputPort;

    @Autowired
    public MovementControllerAdapter(CreateMovementInputPort createMovementInputPort, FindingMovementByLocationInputPort findingMovementByLocationInputPort, ListingMovementsByDatesInputPort listingMovementsByDatesInputPort) {
        this.createMovementInputPort = createMovementInputPort;
        this.findingMovementByLocationInputPort = findingMovementByLocationInputPort;
        this.listingMovementsByDatesInputPort = listingMovementsByDatesInputPort;
    }

    @Operation(
            summary = "Crear registro de nuevo movimiento",
            description = "Devuelve la información de la habitacion correspondiente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room creada"),
            @ApiResponse(responseCode = "404", description = "Room no creada")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<MovementResponseDto> createRoom(@RequestBody MovementRequestDto dto){
        CreateMovementDto objectAdaptedFromRestToDomain = dto.toAppli();

        Movement movement = createMovementInputPort.createMovement(objectAdaptedFromRestToDomain);

        MovementResponseDto response = MovementResponseDto.fromDomain(movement);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @Operation(
            summary = "Obtener movimientos de un establecimiento",
            description = "Devuelve todos los movimientos de un establecimiento filtrados por rango de fechas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimientos encontrados"),
            @ApiResponse(responseCode = "404", description = "No se encontraron movimientos para el criterio dado")
    })
    @GetMapping("/location/{id}")
    @Transactional
    public ResponseEntity<List<MovementResponseDto>> getMovementsByLocationAndDates(
            @PathVariable("id") String locationId,
            @RequestParam("from") String from,
            @RequestParam("to") String to) {

        List<Movement> movements = findingMovementByLocationInputPort.findMovementByLocation(
                locationId,
                LocalDateTime.parse(from),
                LocalDateTime.parse(to)
        );

        List<MovementResponseDto> response = movements.stream()
                .map(MovementResponseDto::fromDomain)
                .toList();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener movimientos en un rango de fechas",
            description = "Devuelve todos los movimientos registrados en el sistema filtrados por rango de fechas, sin importar el establecimiento."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimientos encontrados"),
            @ApiResponse(responseCode = "404", description = "No se encontraron movimientos en el rango dado")
    })
    @GetMapping("/dates")
    @Transactional
    public ResponseEntity<List<MovementResponseDto>> getMovementsByDates(
            @RequestParam("from") String from,
            @RequestParam("to") String to) {

        List<Movement> movements = listingMovementsByDatesInputPort.getListingMovementsByDates(
                LocalDateTime.parse(from),
                LocalDateTime.parse(to)
        );

        List<MovementResponseDto> response = movements.stream()
                .map(MovementResponseDto::fromDomain)
                .toList();

        return ResponseEntity.ok(response);
    }


}

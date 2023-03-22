package com.musalasoft.droneapi.controller;

import com.musalasoft.droneapi.dto.DroneDTO;
import com.musalasoft.droneapi.dto.MedicationLoadRequestDTO;
import com.musalasoft.droneapi.dto.ResponseDTO;
import com.musalasoft.droneapi.service.DroneLoadService;
import com.musalasoft.droneapi.service.DroneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Dipendra Bista
 * @since 2023/3/10
 * <p>
 * Dispatcher controller is the controller which communicates the drones
 * </p/
 */
@Api(description = "This Controller allows client to Communicate With Drone.")


@OpenAPIDefinition(servers = {@Server(url = "http://localhost:${server.port}")},
        info = @Info(title = "Drone Rest API", version = "v1", description = "Drone Service Api which allows client to communicate with Drone"))
@Slf4j
@RestController
@RequestMapping("/api/dispatcher-controller/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE, onConstructor = @_(@Autowired))
public class DispatcherController {
    DroneService droneService;
    DroneLoadService droneLoadService;

    @PostMapping(path = "register", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Register Drone")
    public ResponseDTO register(@Validated @NotNull @RequestBody DroneDTO droneDTO) {
        log.info("Registering  new Drone : ", droneDTO);
        return ResponseDTO.builder()
                .data(droneService.registerDrone(droneDTO))
                .build();
    }

    @PostMapping(path = "load-medication", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Loading a drone with medication items")
    public ResponseDTO loadMedication(@Valid @NotNull @RequestBody MedicationLoadRequestDTO medicationLoadRequestDTO) {
        log.info("Load drone {} with medications {}", medicationLoadRequestDTO.getSerialNumber(), medicationLoadRequestDTO.getMedicationCodes());
        return ResponseDTO.builder()
                .data(droneLoadService.loadMedication(medicationLoadRequestDTO))
                .build();
    }

    @GetMapping(path = "check-battery-level", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Check drone battery level for a given drone")
    public ResponseDTO checkBatteryLevel(@NotNull @RequestParam String serialNumber) {
        log.info("Checking battery levels of given Drone {}", serialNumber);
        return ResponseDTO.builder()
                .data(droneService.findDroneBatteryCapacity(serialNumber))
                .build();
    }
}

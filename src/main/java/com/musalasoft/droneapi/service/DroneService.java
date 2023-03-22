package com.musalasoft.droneapi.service;

import com.musalasoft.droneapi.constants.AppConstants;
import com.musalasoft.droneapi.constants.State;
import com.musalasoft.droneapi.dto.DroneDTO;
import com.musalasoft.droneapi.dto.mapper.DroneMapper;
import com.musalasoft.droneapi.entity.Drone;
import com.musalasoft.droneapi.exception.object.AlreadyExistException;
import com.musalasoft.droneapi.repository.DroneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
public class DroneService {

    private DroneRepository droneRepository;
    private DroneMapper droneMapper;

    public DroneService(DroneRepository droneRepository,
                        DroneMapper droneMapper
    ) {
        this.droneRepository = droneRepository;
        this.droneMapper = droneMapper;
    }


    public Drone registerDrone(DroneDTO droneDTO) {
        log.info("Converting DroneDTO to Drone Entity and persisting into Database");
        if (droneRepository.count() == AppConstants.MAX_FLEET_SIZE)
            throw new RuntimeException("Drone Fleet Size Exceeded");
        droneRepository.findById(droneDTO.getSerialNumber())
                .ifPresent((drone) -> {
                    throw AlreadyExistException.of("Drone " + drone.getSerialNumber() + " already exist !");
                });
        /**
         * Drone state set to IDLE state initially regardless of the state passed from the client
         */
        droneDTO.setState(State.IDLE);
        return droneRepository.saveAndFlush(droneMapper.from(droneDTO));
    }

    public List<DroneDTO> getAvailableDrones() {
        log.info("Retrieving list of available drones ");
        List<State> states = new ArrayList<>();
        states.add(State.IDLE);
        states.add(State.LOADED);
        return droneRepository
                .findAllByStateInAndBatteryCapacityGreaterThanEqual(states, 25.0)
                .stream()
                .filter(drone -> drone.getDroneLoads().stream().mapToDouble(droneLoad -> droneLoad.getMedication().getWeight()).sum() < drone.getWeightLimit())
                .map(droneMapper::from).collect(Collectors.toList());
    }

    public Double findDroneBatteryCapacity(String serialNumber) {
        log.info("Retrieving Battery capacity for {} drone ", serialNumber);
        return getDrone(serialNumber).getBatteryCapacity();
    }

    private Drone getDrone(String serialNumber) {
        return droneRepository.findById(serialNumber)
                .orElseThrow(() -> new EntityNotFoundException("Drone " + serialNumber + " Does not Exist "));
    }
}
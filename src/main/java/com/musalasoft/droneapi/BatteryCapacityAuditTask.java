package com.musalasoft.droneapi;

import com.musalasoft.droneapi.entity.DroneAuditLog;
import com.musalasoft.droneapi.repository.DroneAuditLogRepository;
import com.musalasoft.droneapi.repository.DroneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BatteryCapacityAuditTask {
    private DroneAuditLogRepository droneAuditLogRepository;
    private DroneRepository droneRepository;

    @Autowired
    public BatteryCapacityAuditTask(
            DroneAuditLogRepository droneAuditLogRepository,
            DroneRepository droneRepository) {

        this.droneAuditLogRepository = droneAuditLogRepository;
        this.droneRepository = droneRepository;

    }

    @Scheduled(fixedRateString = "${scheduler.interval}")
    public void auditBatteryCapacity() {
        droneRepository.findAll()
                .forEach(drone -> {
                    droneAuditLogRepository.save(DroneAuditLog.builder()
                            .serialNumber(drone.getSerialNumber())
                            .batteryCapacity(drone.getBatteryCapacity())
                            .build());
                    log.info("serial number - {}, capacity - {}", drone.getSerialNumber(), drone.getBatteryCapacity());
                });
    }
}

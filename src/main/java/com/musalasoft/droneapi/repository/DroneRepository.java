package com.musalasoft.droneapi.repository;

import com.musalasoft.droneapi.constants.State;
import com.musalasoft.droneapi.entity.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {

    @Modifying
    @Query(value = "update Drone d set d.state = :state where  d.serialNumber= :serialno")
    void updateDroneState(@Param("state") State state, @Param("serialno") String serialno);

}

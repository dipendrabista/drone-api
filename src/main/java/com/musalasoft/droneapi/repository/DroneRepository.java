package com.musalasoft.droneapi.repository;

import com.musalasoft.droneapi.entity.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {


}

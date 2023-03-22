package com.musalasoft.droneapi.dto.mapper;

import com.musalasoft.droneapi.dto.DroneDTO;
import com.musalasoft.droneapi.entity.Drone;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface DroneMapper {
    DroneDTO from(Drone drone);

    Drone from(DroneDTO droneDTO);
}

package com.musalasoft.droneapi.dto;

import com.musalasoft.droneapi.constants.Model;
import com.musalasoft.droneapi.constants.State;
import com.musalasoft.droneapi.validation.ModelWeightLimit;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;

@Data
@ModelWeightLimit(message = "Drone weight limit does not fall under given drone model")
public class DroneDTO {
    @Size(min = 1, max = 100)
    private String serialNumber;
    @Enumerated(EnumType.STRING)
    private Model model;
    @Range(min = 0, max = 500, message = "Drone Weight limit must be between 0-500gm")
    private Double weightLimit;
    @Range(min = 0, max = 100, message = "Drone Battery Capacity must be between 0-100%")
    private Double batteryCapacity;
    @Enumerated(EnumType.STRING)
    private State state;
}

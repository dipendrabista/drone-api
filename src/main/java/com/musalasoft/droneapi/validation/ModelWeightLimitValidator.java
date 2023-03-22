package com.musalasoft.droneapi.validation;

import com.musalasoft.droneapi.dto.DroneDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ModelWeightLimitValidator implements ConstraintValidator<ModelWeightLimit, DroneDTO> {

    @Override
    public boolean isValid(DroneDTO droneDTO, ConstraintValidatorContext context) {
        if (droneDTO == null || droneDTO.getModel() == null || droneDTO.getWeightLimit() == null) {
            // If any of the fields are null, let the other validators handle it
            return true;
        }
        switch (droneDTO.getModel()) {
            case LIGHTWEIGHT:
                return droneDTO.getWeightLimit() >= 0 && droneDTO.getWeightLimit() < 125;
            case MIDDLEWEIGHT:
                return droneDTO.getWeightLimit() >= 125 && droneDTO.getWeightLimit() < 250;
            case CRUISERWEIGHT:
                return droneDTO.getWeightLimit() >= 250 && droneDTO.getWeightLimit() < 375;
            case HEAVYWEIGHT:
                return droneDTO.getWeightLimit() >= 375 && droneDTO.getWeightLimit() <= 500;
            default:
                return true;
        }
    }
}

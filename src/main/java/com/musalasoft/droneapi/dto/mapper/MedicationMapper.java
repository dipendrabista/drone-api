package com.musalasoft.droneapi.dto.mapper;

import com.musalasoft.droneapi.dto.MedicationDTO;
import com.musalasoft.droneapi.entity.Medication;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MedicationMapper {
    MedicationDTO from(Medication medication);
}

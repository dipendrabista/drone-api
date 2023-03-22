package com.musalasoft.droneapi.model;

import com.musalasoft.droneapi.entity.Medication;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicationWithQuantity {
    Medication medication;
    Integer quantity;
}

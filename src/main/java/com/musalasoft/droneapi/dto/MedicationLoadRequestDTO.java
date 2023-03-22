package com.musalasoft.droneapi.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Data
public class MedicationLoadRequestDTO {
    private String serialNumber;
    private List<MedicationCode> medicationCodes;

    @Data
    public static class MedicationCode {

        String code;

        @Min(1)
        int quantity = 1;
    }
}


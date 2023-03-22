package com.musalasoft.droneapi.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import java.sql.Blob;

@Data
public class MedicationDTO {
    @Pattern(
            regexp = "[A-Z0-9_]+",
            message = "only upper case letters, underscore and numbers allowed"
    )
    private String code;
    @Pattern(
            regexp = "[a-zA-Z_0-9-]+",
            message = "only letters, numbers, underscore and hyphen allowed"
    )
    private String name;
    private Double weight;
    private Blob image;
    private Integer quantity;
}

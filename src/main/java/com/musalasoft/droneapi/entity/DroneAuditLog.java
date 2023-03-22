package com.musalasoft.droneapi.entity;

import com.musalasoft.droneapi.entity.audit.CreateUpdateAudit;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class DroneAuditLog extends CreateUpdateAudit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(0)
    @Max(100)
    private Double batteryCapacity;
    @Size(min = 1, max = 100)
    private String serialNumber;
}

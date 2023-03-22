package com.musalasoft.droneapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.musalasoft.droneapi.constants.Model;
import com.musalasoft.droneapi.constants.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Setter
@Getter
@NoArgsConstructor
public class Drone {
    @Id
    @Size(min = 1, max = 100)
    private String serialNumber;
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private Model model;
    @Min(0)
    @Max(500)
    @NotNull
    @Column(name = "weight_limit", columnDefinition = "DOUBLE NOT NULL")
    private Double weightLimit;
    @Min(0)
    @Max(100)
    @Column(name = "battery_capacity", columnDefinition = "DOUBLE NOT NULL")
    private Double batteryCapacity;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10) NOT NULL")
    private State state;

    @OneToMany(mappedBy = "drone", fetch = FetchType.LAZY)
    @JsonBackReference
    List<DroneLoad> droneLoads;
}

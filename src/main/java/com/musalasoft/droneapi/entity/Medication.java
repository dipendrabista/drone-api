package com.musalasoft.droneapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.sql.Blob;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Medication {
    @Id
    @Pattern(
            regexp = "[A-Z0-9_]+",
            message = "only upper case letters, underscore and numbers allowed"
    )
    private String code;
    @Column(nullable = false)
    @Pattern(
            regexp = "[a-zA-Z_0-9-]+",
            message = "only letters, numbers, underscore and hyphen allowed"
    )
    private String name;
    private Double weight;
    private Blob image;
    @JsonBackReference
    @OneToMany(mappedBy = "medication", fetch = FetchType.LAZY)
    List<DroneLoad> droneLoads;
}

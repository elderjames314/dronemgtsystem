package com.blusalt.dronemgtsystem.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.blusalt.dronemgtsystem.enums.DroneModel;
import com.blusalt.dronemgtsystem.enums.DroneStates;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
@Entity(name = "drones")
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max=100)
    @Column(name = "serial_number")
    private String serialNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "model")
    private DroneModel model;

    @NotNull
    @Column(name = "weight_limit")
    private double weightLimit;

    @NotNull
    private int batteryCapacity = 100;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private DroneStates state = DroneStates.IDLE;

    @OneToMany(mappedBy = "drone")
    private List<Delivery> deliveries;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;


}

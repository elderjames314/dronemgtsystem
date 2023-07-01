package com.blusalt.dronemgtsystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatedDrone {
    private Long id;
    private String serialNumber;
    private String model;
    private int batteryCapacity;
    private Double weightLimit;
}

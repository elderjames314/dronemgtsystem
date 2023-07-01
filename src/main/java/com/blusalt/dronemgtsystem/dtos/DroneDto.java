package com.blusalt.dronemgtsystem.dtos;

import com.blusalt.dronemgtsystem.enums.DroneModel;
import com.blusalt.dronemgtsystem.exceptions.InvalidRequestException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DroneDto {
    private String serialNumber;
    private String model;
    private String weight;
    private int batteryCapacity;

    public static DroneDto developDroneModel(DroneDto droneDto) {
        DroneDtoBuilder builder = DroneDto.builder();

        // Validate and set the serial number
        String serialNumber = droneDto.getSerialNumber();
        if (isEmpty(serialNumber)) {
            throw new InvalidRequestException("Serial number cannot be null or empty");
        }
        builder.serialNumber(serialNumber);

        // Validate and set the weight
        String weight = droneDto.getWeight();
        if (isEmpty(weight)) {
            throw new InvalidRequestException("Weight cannot be empty");
        }
        builder.weight(weight);// optional, if not specified, it will default to 

        // Validate and set the battery capacity
        int batteryCapacity = droneDto.getBatteryCapacity();
        if (batteryCapacity <= 0) {
            throw new InvalidRequestException("Battery capacity must be greater than 0");
        }
        builder.batteryCapacity(batteryCapacity);

        return builder.build();
    }

    private static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}

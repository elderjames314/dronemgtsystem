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
    private String weightLimit;
    private int batteryCapacity;

    public static DroneDto developDroneModel(DroneDto droneDto) {
        validateDroneModel(droneDto.getModel());
        validateSerialNumber(droneDto.getSerialNumber());
        validateWeightLimit(droneDto.getWeightLimit());
        validateBatteryCapacity(droneDto.getBatteryCapacity());

        return droneDto;
    }

    public DroneModel getModelPassed(String model) {
        return DroneModel.valueOf(model);
    }

    private static void validateDroneModel(String model) {
        if (model == null) {
            throw new InvalidRequestException("Please specify the drone model");
        }
        try {
            DroneModel.valueOf(model.toUpperCase());
        } catch (IllegalArgumentException e) {
           throw new InvalidRequestException("Please specify the correct model eg: Lightweight, Cruiserweight, Heavyweight or MiddleWeight");
        }
    }

    private static void validateSerialNumber(String serialNumber) {
        if (isEmpty(serialNumber)) {
            throw new InvalidRequestException("Serial number cannot be null or empty");
        }
        if(serialNumber.length() > 100) {
            throw new InvalidRequestException("Serial number can only 100 max chars");
        }
    }

    private static void validateWeightLimit(String weightLimit) {
        if (isEmpty(weightLimit)) {
            throw new InvalidRequestException("Weight limit cannot be null or empty");
        }
        try {
            double weight = Double.parseDouble(weightLimit);
            if (weight > 500) {
                throw new InvalidRequestException("Weight limit cannot exceed 100");
            }
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("Please specify the correct weight limit as a numeric value");
        }
    }

    private static void validateBatteryCapacity(int batteryCapacity) {
        if (batteryCapacity <= 0) {
            throw new InvalidRequestException("Battery capacity must be greater than 0");
        }
    }

    private static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}

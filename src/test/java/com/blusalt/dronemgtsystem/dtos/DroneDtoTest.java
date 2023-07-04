package com.blusalt.dronemgtsystem.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.blusalt.dronemgtsystem.enums.DroneModel;
import com.blusalt.dronemgtsystem.exceptions.InvalidRequestException;

public class DroneDtoTest {

    private DroneDto droneDto = new DroneDto();

    @Test
    public void developDroneModel_ValidDto_ShouldReturnSameDto() {
        // Arrange
        DroneDto droneDto = DroneDto.builder()
                .serialNumber("12345")
                .model("HEAVYWEIGHT")
                .weightLimit("50")
                .batteryCapacity(5000)
                .build();

        // Act
        DroneDto developedDto = DroneDto.developDroneModel(droneDto);

        // Assert
        assertSame(droneDto, developedDto);
    }

    @Test
    public void developDroneModel_NullModel_ShouldThrowInvalidRequestException() {
        // Arrange
        DroneDto droneDto = DroneDto.builder()
                .serialNumber("12345")
                .weightLimit("50")
                .batteryCapacity(5000)
                .build();

        // Act & Assert
        assertThrows(InvalidRequestException.class, () -> DroneDto.developDroneModel(droneDto));
    }

    @Test
    public void developDroneModel_NullSerialNumber_ShouldThrowInvalidRequestException() {
        // Arrange
        DroneDto droneDto = DroneDto.builder()
                .model("LIGHTWEIGHT")
                .weightLimit("50")
                .batteryCapacity(5000)
                .build();

        // Act & Assert
        assertThrows(InvalidRequestException.class, () -> DroneDto.developDroneModel(droneDto));
    }

    @Test
    public void developDroneModel_NullWeightLimit_ShouldThrowInvalidRequestException() {
        // Arrange
        DroneDto droneDto = DroneDto.builder()
                .serialNumber("12345")
                .model("MODEL_X")
                .batteryCapacity(5000)
                .build();

        // Act & Assert
        assertThrows(InvalidRequestException.class, () -> DroneDto.developDroneModel(droneDto));
    }

    @Test
    public void developDroneModel_InvalidWeightLimit_ShouldThrowInvalidRequestException() {
        // Arrange
        DroneDto droneDto = DroneDto.builder()
                .serialNumber("12345")
                .model("CRUISERWEIGHT")
                .weightLimit("abc")
                .batteryCapacity(5000)
                .build();

        // Act & Assert
        assertThrows(InvalidRequestException.class, () -> DroneDto.developDroneModel(droneDto));
    }

    @Test
    public void developDroneModel_ExceedingWeightLimit_ShouldThrowInvalidRequestException() {
        // Arrange
        DroneDto droneDto = DroneDto.builder()
                .serialNumber("12345")
                .model("HEAVYWEIGHT")
                .weightLimit("1500")
                .batteryCapacity(5000)
                .build();

        // Act & Assert
        assertThrows(InvalidRequestException.class, () -> DroneDto.developDroneModel(droneDto));
    }

    @Test
    public void developDroneModel_NonPositiveBatteryCapacity_ShouldThrowInvalidRequestException() {
        // Arrange
        DroneDto droneDto = DroneDto.builder()
                .serialNumber("12345")
                .model("LIGHTWEIGHT")
                .weightLimit("50")
                .batteryCapacity(0)
                .build();

        // Act & Assert
        assertThrows(InvalidRequestException.class, () -> DroneDto.developDroneModel(droneDto));
    }

    @Test
    public void developDroneModel_ValidWeightLimitAsString_ShouldConvertToDouble() {
        // Arrange
        DroneDto droneDto = DroneDto.builder()
                .serialNumber("12345")
                .model("LIGHTWEIGHT")
                .weightLimit("50")
                .batteryCapacity(5000)
                .build();

        // Act
        DroneDto developedDto = DroneDto.developDroneModel(droneDto);

        // Assert
        assertEquals(50.0, Double.parseDouble(developedDto.getWeightLimit()));
    }

    @Test
    public void getModelPassed_ValidModelString_ShouldReturnCorrespondingEnum() {
        // Arrange
        String modelString = "LIGHTWEIGHT";

        // Act
        DroneModel droneModel = droneDto.getModelPassed(modelString);

        // Assert
        assertEquals(DroneModel.LIGHTWEIGHT, droneModel);
    }

    @Test
    public void getModelPassed_InvalidModelString_ShouldThrowIllegalArgumentException() {
        // Arrange
        String modelString = "INVALID_MODEL";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> droneDto.getModelPassed(modelString));
    }

    @Test
    public void getModelPassed_NullModelString_ShouldThrowNullPointerException() {
        // Arrange
        String modelString = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> droneDto.getModelPassed(modelString));
    }
}

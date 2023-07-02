package com.blusalt.dronemgtsystem.operation.drone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.blusalt.dronemgtsystem.dtos.DroneDto;
import com.blusalt.dronemgtsystem.enums.DroneModel;
import com.blusalt.dronemgtsystem.model.Drone;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

public class DroneBaseFactoryTest {
    @Test
    public void createDrone_ShouldCreateDroneWithCorrectProperties() {
        // Arrange
        DroneDto droneDto = new DroneDto();
        droneDto.setBatteryCapacity(5000);
        droneDto.setSerialNumber("12345");
        droneDto.setWeightLimit("100");
        droneDto.setModel("Lightweight");
        DroneModel droneModel = DroneModel.HEAVYWEIGHT;

        DroneBaseFactory factory = new DroneBaseFactory();

        // Act
        Drone drone = factory.createDrone(droneDto, droneModel);

        // Assert
        assertEquals(droneModel, drone.getModel());
        assertEquals(5000, drone.getBatteryCapacity());
        assertEquals("12345", drone.getSerialNumber());
        assertEquals(5000, drone.getWeightLimit());
    }
}

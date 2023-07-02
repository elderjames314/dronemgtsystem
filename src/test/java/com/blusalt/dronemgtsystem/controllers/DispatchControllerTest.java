package com.blusalt.dronemgtsystem.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.blusalt.dronemgtsystem.dtos.CreatedDrone;
import com.blusalt.dronemgtsystem.dtos.DroneDto;
import com.blusalt.dronemgtsystem.enums.DroneModel;
import com.blusalt.dronemgtsystem.exceptions.InvalidRequestException;
import com.blusalt.dronemgtsystem.model.Drone;
import com.blusalt.dronemgtsystem.operation.drone.DroneFactory;
import com.blusalt.dronemgtsystem.utils.DroneConstant;
import com.blusalt.dronemgtsystem.utils.Response;

public class DispatchControllerTest {
    @Mock
    private DroneFactory droneFactory;

    private DispatchController dispatchController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        dispatchController = new DispatchController(droneFactory);
    }

    @Test
    public void registerDrone_ValidDto_ShouldReturnCreatedDrone() {
        // Arrange
        DroneDto droneDto = createValidDroneDto();
        Drone drone = createMockDrone();
        CreatedDrone expectedCreatedDrone = mapToCreatedDrone(drone);
        Response<CreatedDrone> expectedResponse = Response.getResponse(expectedCreatedDrone, null, true,
                DroneConstant.SUCCESS, HttpStatus.CREATED.value());

        when(droneFactory.createDrone(any(DroneDto.class), any(DroneModel.class))).thenReturn(drone);

        // Act
        ResponseEntity<Response<CreatedDrone>> responseEntity = dispatchController.registerDrone(droneDto);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    public void registerDrone_NullModel_ShouldThrowInvalidRequestException() {
        // Arrange
        DroneDto droneDto = createValidDroneDto();
        droneDto.setModel(null);

        // Act & Assert
        assertThrows(InvalidRequestException.class, () -> dispatchController.registerDrone(droneDto));
    }

    private DroneDto createValidDroneDto() {
        return DroneDto.builder()
                .serialNumber("12345")
                .model("LIGHTWEIGHT")
                .weightLimit("50")
                .batteryCapacity(5000)
                .build();
    }

    private Drone createMockDrone() {
        Drone drone = new Drone();
        drone.setId(1L);
        drone.setSerialNumber("12345");
        drone.setModel(DroneModel.LIGHTWEIGHT);
        drone.setWeightLimit(50);
        drone.setBatteryCapacity(5000);
        return drone;
    }

    private CreatedDrone mapToCreatedDrone(Drone drone) {
        return CreatedDrone.builder()
                .id(drone.getId())
                .serialNumber(drone.getSerialNumber())
                .batteryCapacity(drone.getBatteryCapacity())
                .weightLimit(drone.getWeightLimit())
                .build();
    }
}

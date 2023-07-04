package com.blusalt.dronemgtsystem.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.blusalt.dronemgtsystem.dtos.CreatedDrone;
import com.blusalt.dronemgtsystem.dtos.DeliveryDto;
import com.blusalt.dronemgtsystem.dtos.DroneDto;
import com.blusalt.dronemgtsystem.model.Drone;
import com.blusalt.dronemgtsystem.model.Medication;
import com.blusalt.dronemgtsystem.operation.drone.DroneFactory;
import com.blusalt.dronemgtsystem.repository.DroneRepository;
import com.blusalt.dronemgtsystem.services.DroneService;
import com.blusalt.dronemgtsystem.utils.Response;

public class DispatchControllerTest {

    @Mock
    private DroneFactory droneFactory;

    @Mock
    private DroneService droneService;

    @Mock
    private DroneRepository droneRepository;

    private DispatchController dispatchController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dispatchController = new DispatchController(droneFactory, droneService);
    }

    @Test
    void testRegisterDrone() {
        // Mock the droneDto
        DroneDto droneDto = new DroneDto();
        droneDto.setModel("LIGHTWEIGHT");
        droneDto.setSerialNumber("12345");
        droneDto.setBatteryCapacity(100);
        droneDto.setWeightLimit("50");

        // Mock the created drone
        Drone drone = new Drone();
        drone.setId(1L);
        drone.setSerialNumber("12345");
        drone.setBatteryCapacity(100);
        drone.setWeightLimit(50);

        // Mock the drone factory
        when(droneFactory.createLightweightDrone(droneDto)).thenReturn(drone);

        // Call the registerDrone method
        ResponseEntity<Response<CreatedDrone>> responseEntity = dispatchController.registerDrone(droneDto);

        // Verify the response
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        Response<CreatedDrone> response = responseEntity.getBody();
        assertNotNull(response);
        // assertTrue(response.su());

        CreatedDrone createdDrone = response.getData();
        assertNotNull(createdDrone);
        assertEquals(1L, createdDrone.getId());
        assertEquals("12345", createdDrone.getSerialNumber());
        assertEquals(100, createdDrone.getBatteryCapacity());
        assertEquals(50, createdDrone.getWeightLimit());

    }

    @Test
    void deliverMedicationsLoadedOnDrone_shouldReturnSuccessResponse() {
        DeliveryDto deliveryDto = new DeliveryDto();

        ResponseEntity<Response<?>> responseEntity = dispatchController.deliverMedicationsLoadedOnDrone(deliveryDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(droneService).deliverMedicationsLoadedOnDrone(deliveryDto);
    }

    @Test
    void getLoadedMedicationsForDrone_shouldReturnListOfMedications() {
        Long droneId = 1L;
        List<Medication> medications = Collections.singletonList(new Medication());

        when(droneService.getLoadedMedicationsForDrone(droneId)).thenReturn(medications);

        ResponseEntity<Response<?>> responseEntity = dispatchController.getLoadedMedicationsForDrone(droneId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(medications, responseEntity.getBody().getData());

        verify(droneService).getLoadedMedicationsForDrone(droneId);
    }

    @Test
    void getAvailableDronesForLoading_shouldReturnListOfDrones() {
        List<Drone> drones = Collections.singletonList(new Drone());

        when(droneService.getAvailableDronesForLoading()).thenReturn(drones);

        ResponseEntity<Response<?>> responseEntity = dispatchController.getAvailableDronesForLoading();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(drones, responseEntity.getBody().getData());

        verify(droneService).getAvailableDronesForLoading();
    }

    @Test
    void getDroneBatteryLevel_shouldReturnBatteryLevel() {
        Long droneId = 1L;
        int batteryLevel = 80;

        when(droneService.getDroneBatteryLevel(droneId)).thenReturn(batteryLevel);

        ResponseEntity<Response<?>> responseEntity = dispatchController.getDroneBatteryLevel(droneId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(batteryLevel, responseEntity.getBody().getData());

        verify(droneService).getDroneBatteryLevel(droneId);
    }
}

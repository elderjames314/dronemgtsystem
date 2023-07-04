package com.blusalt.dronemgtsystem.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
import com.blusalt.dronemgtsystem.enums.DroneModel;
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
    public void testRegisterDrone() {
        // Mock data
        DroneDto droneDto = new DroneDto();
        droneDto.setModel("LIGHTWEIGHT");
        droneDto.setSerialNumber("DR123");
        droneDto.setWeightLimit("2.5");
        droneDto.setBatteryCapacity(100);

        Drone drone = new Drone();
        drone.setId(1L);
        drone.setSerialNumber("DR123");
        drone.setBatteryCapacity(100);
        drone.setWeightLimit(2.5);
        drone.setModel(DroneModel.LIGHTWEIGHT);

        CreatedDrone createdDrone = CreatedDrone.builder()
                .id(drone.getId())
                .serialNumber(drone.getSerialNumber())
                .batteryCapacity(drone.getBatteryCapacity())
                .weightLimit(drone.getWeightLimit())
                .model(drone.getModel().name())
                .build();

        // Configure mocks
        when(droneFactory.createLightweightDrone(any(DroneDto.class))).thenReturn(drone);

        // Call the method under test
        ResponseEntity<Response<CreatedDrone>> responseEntity = dispatchController.registerDrone(droneDto);

        // Verify the interactions
        verify(droneFactory).createLightweightDrone(droneDto);

        // Assert the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Response<CreatedDrone> responseBody = responseEntity.getBody();
        assertEquals(createdDrone, responseBody.getData());
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

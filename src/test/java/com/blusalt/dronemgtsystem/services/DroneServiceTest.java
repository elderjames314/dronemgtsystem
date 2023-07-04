package com.blusalt.dronemgtsystem.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.blusalt.dronemgtsystem.dtos.DeliveryDto;
import com.blusalt.dronemgtsystem.enums.DroneStates;
import com.blusalt.dronemgtsystem.exceptions.InvalidRequestException;
import com.blusalt.dronemgtsystem.exceptions.NotFoundException;
import com.blusalt.dronemgtsystem.model.Delivery;
import com.blusalt.dronemgtsystem.model.Drone;
import com.blusalt.dronemgtsystem.model.Medication;
import com.blusalt.dronemgtsystem.operation.dronestate.BatteryLevelAuditLogger;
import com.blusalt.dronemgtsystem.operation.dronestate.DroneContext;
import com.blusalt.dronemgtsystem.repository.DeliveryRepository;
import com.blusalt.dronemgtsystem.repository.DroneRepository;
import com.blusalt.dronemgtsystem.repository.MedicationRepository;

@ExtendWith(MockitoExtension.class)
public class DroneServiceTest {

    @Mock
    private DroneRepository droneRepository;
    @Mock
    private MedicationRepository medicationRepository;
    @Mock
    private DeliveryRepository deliveryRepository;
    @Mock
    private DroneContext droneContext;
    @Mock
    private BatteryLevelAuditLogger batteryLevelAuditLogger;

    @InjectMocks
    private DroneService droneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        droneService = new DroneService(droneRepository, medicationRepository, deliveryRepository, droneContext);
    }

    @Test
    void deliverMedicationsLoadedOnDrone_ValidDelivery_SuccessfulDelivery() {
        // Arrange
        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setDroneId(1L);
        deliveryDto.setMedicationIds(Arrays.asList(1L, 2L));
        deliveryDto.setLocation("Delivery location");

        Drone drone = new Drone();
        drone.setId(1L);
        drone.setState(DroneStates.IDLE);
        drone.setWeightLimit(100);
        drone.setBatteryCapacity(50);

        Medication medication1 = new Medication();
        medication1.setId(1L);
        medication1.setWeight(20);

        Medication medication2 = new Medication();
        medication2.setId(2L);
        medication2.setWeight(30);

        when(droneRepository.findById(1L)).thenReturn(Optional.of(drone));
        when(medicationRepository.findByIdIn(Arrays.asList(1L, 2L)))
                .thenReturn(Arrays.asList(medication1, medication2));

        // Act
        droneService.deliverMedicationsLoadedOnDrone(deliveryDto);

        // Assert
        ArgumentCaptor<Delivery> deliveryCaptor = ArgumentCaptor.forClass(Delivery.class);
        verify(deliveryRepository).save(deliveryCaptor.capture());

        Delivery capturedDelivery = deliveryCaptor.getValue();
        assertNotNull(capturedDelivery);
        assertEquals(drone, capturedDelivery.getDrone());
        assertEquals(Arrays.asList(medication1, medication2), capturedDelivery.getMedications());
        assertNotNull(capturedDelivery.getDeliveryLocation());
        assertNotNull(capturedDelivery.getDeliveryDate());

        verify(droneContext).setDroneId(1L);
        verify(droneContext).loadMedications(Arrays.asList(medication1, medication2));

    }

    @Test
    void deliverMedicationsLoadedOnDrone_DroneNotFound_ThrowsNotFoundException() {
        // Arrange
        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setDroneId(1L);
        deliveryDto.setMedicationIds(Collections.singletonList(1L));

        when(droneRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> droneService.deliverMedicationsLoadedOnDrone(deliveryDto));

        verify(droneContext, never()).setDroneId(anyLong());
        verify(droneContext, never()).loadMedications(anyList());
        verify(deliveryRepository, never()).save(any(Delivery.class));
        verify(batteryLevelAuditLogger, never()).createBatteryLevelAuditLog(anyLong(), anyInt());
    }

    @Test
    void deliverMedicationsLoadedOnDrone_DroneNotInIdleState_ThrowsInvalidRequestException() {
        // Arrange
        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setDroneId(1L);
        deliveryDto.setMedicationIds(Collections.singletonList(1L));

        Drone drone = new Drone();
        drone.setState(DroneStates.DELIVERED);

        when(droneRepository.findById(1L)).thenReturn(java.util.Optional.of(drone));

        // Act & Assert
        assertThrows(InvalidRequestException.class, () -> droneService.deliverMedicationsLoadedOnDrone(deliveryDto));

        verify(droneContext, never()).setDroneId(anyLong());
        verify(droneContext, never()).loadMedications(anyList());
        verify(deliveryRepository, never()).save(any(Delivery.class));
        verify(batteryLevelAuditLogger, never()).createBatteryLevelAuditLog(anyLong(), anyInt());
    }

    @Test
    void deliverMedicationsLoadedOnDrone_ExceedsDroneWeightLimit_ThrowsInvalidRequestException() {
        // Arrange
        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setDroneId(1L);
        deliveryDto.setMedicationIds(Arrays.asList(1L, 2L));

        Drone drone = new Drone();
        drone.setId(1L);
        drone.setState(DroneStates.IDLE);
        drone.setWeightLimit(30);

        Medication medication1 = new Medication();
        medication1.setId(1L);
        medication1.setWeight(20);

        Medication medication2 = new Medication();
        medication2.setId(2L);
        medication2.setWeight(30);

        when(droneRepository.findById(1L)).thenReturn(java.util.Optional.of(drone));
        when(medicationRepository.findByIdIn(Arrays.asList(1L, 2L)))
                .thenReturn(Arrays.asList(medication1, medication2));

        // Act & Assert
        assertThrows(InvalidRequestException.class, () -> droneService.deliverMedicationsLoadedOnDrone(deliveryDto));

        verify(droneContext, never()).setDroneId(anyLong());
        verify(droneContext, never()).loadMedications(anyList());
        verify(deliveryRepository, never()).save(any(Delivery.class));
        verify(batteryLevelAuditLogger, never()).createBatteryLevelAuditLog(anyLong(), anyInt());
    }

    @Test
    public void testGetLoadedMedicationsForDrone_DroneNotInLoadingState_ShouldThrowInvalidRequestException() {
        // Arrange
        Long droneId = 1L;

        // Mock the drone
        Drone drone = new Drone();
        drone.setId(droneId);
        drone.setState(DroneStates.IDLE);

        when(droneRepository.findById(droneId)).thenReturn(java.util.Optional.of(drone));

        // Act and Assert
        assertThrows(InvalidRequestException.class, () -> droneService.getLoadedMedicationsForDrone(droneId));
    }

    @Test
    public void testGetLoadedMedicationsForDrone_droneInLoadedState() {
        // Create test objects
        Long droneId = 1L;
        DroneRepository droneRepository = mock(DroneRepository.class);
        DroneService droneService = new DroneService(droneRepository, null, null, null);

        Drone drone = new Drone();
        drone.setState(DroneStates.LOADED);

        Delivery delivery1 = new Delivery();
        delivery1.setMedications(Collections.singletonList(new Medication()));

        Delivery delivery2 = new Delivery();
        delivery2.setMedications(Arrays.asList(new Medication(), new Medication()));

        drone.setDeliveries(Arrays.asList(delivery1, delivery2));

        // Set up the repository mock behavior
        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));

        // Call the method under test
        List<Medication> loadedMedications = droneService.getLoadedMedicationsForDrone(droneId);

        // Verify the result
        assertEquals(3, loadedMedications.size());
    }

    @Test
    public void testGetLoadedMedicationsForDrone_droneNotInLoadedState() {
        // Create test objects
        Long droneId = 1L;
        DroneRepository droneRepository = mock(DroneRepository.class);
        DroneService droneService = new DroneService(droneRepository, null, null, null);

        Drone drone = new Drone();
        drone.setState(DroneStates.IDLE);

        // Set up the repository mock behavior
        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));

        // Call the method under test and verify that an exception is thrown
        assertThrows(InvalidRequestException.class, () -> droneService.getLoadedMedicationsForDrone(droneId));
    }

    @Test
    public void testGetAvailableDronesForLoading_DronesAvailable_ShouldReturnAvailableDrones() {
        // Arrange
        Drone drone1 = new Drone();
        drone1.setId(1L);
        drone1.setState(DroneStates.IDLE);

        Drone drone2 = new Drone();
        drone2.setId(2L);
        drone2.setState(DroneStates.IDLE);

        when(droneRepository.findByState(DroneStates.IDLE.name())).thenReturn(Arrays.asList(drone1, drone2));

        // Act
        List<Drone> availableDrones = droneService.getAvailableDronesForLoading();

        // Assert
        assertEquals(Arrays.asList(drone1, drone2), availableDrones);
    }

    @Test
    public void testGetAvailableDronesForLoading_NoDronesAvailable_ShouldReturnEmptyList() {
        when(droneRepository.findByState(DroneStates.IDLE.name())).thenReturn(Collections.emptyList());

        // Act
        List<Drone> availableDrones = droneService.getAvailableDronesForLoading();

        // Assert
        assertTrue(availableDrones.isEmpty());
    }

    @Test
    public void testGetDroneBatteryLevel_DroneExists_ShouldReturnBatteryLevel() {
        // Arrange
        Long droneId = 1L;
        int expectedBatteryLevel = 75;

        Drone drone = new Drone();
        drone.setId(droneId);
        drone.setBatteryCapacity(expectedBatteryLevel);

        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));

        // Act
        int batteryLevel = droneService.getDroneBatteryLevel(droneId);

        // Assert
        assertEquals(expectedBatteryLevel, batteryLevel);
    }

    @Test
    public void testGetDroneBatteryLevel_DroneNotExists_ShouldThrowNotFoundException() {
        // Arrange
        Long droneId = 1L;

        when(droneRepository.findById(droneId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> droneService.getDroneBatteryLevel(droneId));
    }

}

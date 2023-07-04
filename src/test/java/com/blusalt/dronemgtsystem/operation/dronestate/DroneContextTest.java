package com.blusalt.dronemgtsystem.operation.dronestate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.blusalt.dronemgtsystem.operation.dronestate.BatteryLevelObserver;
import com.blusalt.dronemgtsystem.repository.BatteryAuditLogRepository;
import com.blusalt.dronemgtsystem.repository.DroneRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Observer;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.blusalt.dronemgtsystem.enums.DroneStateName;
import com.blusalt.dronemgtsystem.model.BatteryAuditLog;
import com.blusalt.dronemgtsystem.model.Drone;
import com.blusalt.dronemgtsystem.model.Medication;

@ExtendWith(MockitoExtension.class)
public class DroneContextTest {

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private BatteryAuditLogRepository batteryAuditRepository;

    @Mock
    private BatteryLevelObserver batteryLevelObserver;

    @InjectMocks
    private DroneContext droneContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        

    }

    // @Test
    // public void testLoadMedications_ShouldChangeStateToLoadedAndDecreaseBatteryLevel() {
    //     // Arrange
    //     Long droneId = 1L;
    //     int initialBatteryLevel = 100;
    //     List<Medication> medications = new ArrayList<>();
    //     Medication medication1 = new Medication();
    //     medication1.setCode("344L");
    //     medication1.setName("med1");
    //     medication1.setWeight(34.3);

    //     Medication medication2 = new Medication();
    //     medication2.setCode("344L");
    //     medication2.setName("med1");
    //     medication2.setWeight(34.3);

    //     medications.add(medication1);
    //     medications.add(medication2);
    //     DroneStateName initialState = DroneStateName.IDLE;

    //     Drone drone = new Drone();
    //     drone.setBatteryCapacity(initialBatteryLevel);

    //     droneContext.addObserver(batteryLevelObserver);

    //     when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));

    //     droneContext.setBatteryCapacity(initialBatteryLevel);
    //     droneContext.setDroneId(droneId);
    //     droneContext.changeState(new IdleState());

    //     // Act
    //     droneContext.loadMedications(medications);

    //     // Assert
    //     ArgumentCaptor<DroneStateName> stateArgumentCaptor = ArgumentCaptor.forClass(DroneStateName.class);
    //     verify(batteryLevelObserver).setDroneId(droneId);
    //     verify(batteryLevelObserver).update(droneContext, droneContext);

    //     verify(droneRepository).findById(droneId);
    //     verify(droneRepository).save(drone);

    //     verify(batteryAuditRepository).save(any(BatteryAuditLog.class));

    //     assertEquals(DroneStateName.LOADED, droneContext.getCurrentState());
    //     assertEquals(medications, droneContext.getMedications());
    //     assertEquals(initialBatteryLevel - (initialBatteryLevel * 0.1), droneContext.getBatteryCapacity(), 0.001);
    // }

    @Test
    void testCheckBatteryLevel() {
        // Create a mock DroneState
        DroneState currentState = mock(DroneState.class);

        // Create an instance of the DroneContext with a specific droneId
        DroneContext droneContext = new DroneContext(1234L);
        droneContext.changeState(currentState);

        // Call the checkBatteryLevel method
        droneContext.checkBatteryLevel();

        // Verify that the current state handles the battery check correctly
        verify(currentState).handleBatteryCheck(droneContext);
    }

    @Test
    void testReturnToBase() {
        // Create a mock DroneState
        DroneState currentState = mock(DroneState.class);

        // Create an instance of the DroneContext with a specific droneId
        DroneContext droneContext = new DroneContext(1234L);
        droneContext.changeState(currentState);

        // Call the returnToBase method
        droneContext.returnToBase();

        // Verify that the current state handles the return action correctly
        verify(currentState).handleReturn(droneContext);
    }

    @Test
    void testSetAndGetMedications() {
        // Create an instance of the DroneContext
        DroneContext droneContext = new DroneContext(1234L);

        // Create a sample medications list
        List<Medication> medications = Collections.emptyList();

        // Set the medications
        droneContext.setMedications(medications);

        // Get the medications
        List<Medication> retrievedMedications = droneContext.getMedications();

        // Verify that the medications are set and retrieved correctly
        assertEquals(medications, retrievedMedications);
    }


    @Test
    void testChangeState() {
        // Create an instance of the DroneContext
        DroneContext droneContext = new DroneContext(1234L);

        // Create a mock DroneState
        DroneState newState = mock(DroneState.class);

        // Specify the behavior of the getStateName() method
        when(newState.getStateName()).thenReturn(DroneStateName.IDLE);

        // Change the state
        droneContext.changeState(newState);

        // Get the current state
        DroneStateName currentStateName = droneContext.getCurrentState();

        // Verify that the state is changed correctly
        assertEquals(DroneStateName.IDLE, currentStateName);
    }

    @Test
    void testGetCurrentState() {
        // Create an instance of the DroneContext
        DroneContext droneContext = new DroneContext(1234L);

        // Get the current state
        DroneStateName currentStateName = droneContext.getCurrentState();

        // Verify that the current state is correct
        assertEquals(DroneStateName.IDLE, currentStateName);
    }

    @Test
    void testNotifyObservers() {
        // Create a mock Observer
        Observer observer = mock(Observer.class);

        // Create an instance of the DroneContext
        DroneContext droneContext = new DroneContext(1234L);

        // Add the observer
        droneContext.addObserver(observer);

        // Call the notifyObservers method
        droneContext.notifyObservers();

        // Verify that the observer is notified
        verify(observer).update(droneContext, droneContext);
    }
}

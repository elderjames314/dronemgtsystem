package com.blusalt.dronemgtsystem.operation.dronestate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.blusalt.dronemgtsystem.operation.dronestate.BatteryLevelObserver;

import java.util.Collections;
import java.util.List;
import java.util.Observer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.blusalt.dronemgtsystem.enums.DroneStateName;
import com.blusalt.dronemgtsystem.model.Medication;

@ExtendWith(MockitoExtension.class)
public class DroneContextTest {

    @Test
    void testLoadMedications() {
        // Create a mock DroneState
        DroneState currentState = mock(DroneState.class);

        // Create a mock BatteryLevelAuditLogger
        BatteryLevelAuditLogger batteryLevelAuditLogger = mock(BatteryLevelAuditLogger.class);

        // Create an instance of the DroneContext with a specific droneId
        DroneContext droneContext = new DroneContext(1234L);
        droneContext.changeState(currentState);
        droneContext.setBatteryCapacity(100);

        // Set up the medications list
        List<Medication> medications = Collections.emptyList();
        droneContext.setMedications(medications);

        // Create a BatteryLevelObserver instance
        BatteryLevelObserver batteryLevelObserver = new BatteryLevelObserver(droneContext.getDroneId(),
                batteryLevelAuditLogger);

        // Add the BatteryLevelObserver as an observer to the droneContext
        droneContext.addObserver(batteryLevelObserver);

        // Call the loadMedications method
        droneContext.loadMedications(medications);

        // Verify that the current state handles the medication loading correctly
        verify(currentState).handleLoadMedication(droneContext, medications);

        // Verify that the battery level decreases as expected
        assertEquals(90, droneContext.getBatteryCapacity());

        // Verify that the BatteryLevelObserver is added as an observer
        List<Observer> observers = droneContext.getObservers();
        assertEquals(2, observers.size());
        assertTrue(observers.get(0) instanceof BatteryLevelObserver);
        assertEquals(1234L, ((BatteryLevelObserver) observers.get(0)).getDroneId());
        assertEquals(batteryLevelAuditLogger, ((BatteryLevelObserver) observers.get(0)).getBatteryLevelAuditLogger());
    }

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
    void testSetAndGetBatteryCapacity() {
        // Create an instance of the DroneContext
        DroneContext droneContext = new DroneContext(1234L);

        // Set the battery capacity
        droneContext.setBatteryCapacity(100);

        // Get the battery capacity
        int retrievedBatteryCapacity = droneContext.getBatteryCapacity();

        // Verify that the battery capacity is set and retrieved correctly
        assertEquals(100, retrievedBatteryCapacity);
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

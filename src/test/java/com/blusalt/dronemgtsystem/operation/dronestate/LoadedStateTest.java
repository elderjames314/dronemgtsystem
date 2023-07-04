package com.blusalt.dronemgtsystem.operation.dronestate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.blusalt.dronemgtsystem.enums.DroneStateName;
import com.blusalt.dronemgtsystem.model.Medication;

public class LoadedStateTest {
    @Test
    public void testHandleLoadMedication() {
        // Create test objects
        DroneContext context = mock(DroneContext.class);
        List<Medication> medications = new ArrayList<>();

        // Create LoadedState instance
        LoadedState loadedState = new LoadedState();

        // Call the method under test
        loadedState.handleLoadMedication(context, medications);

        // Verify that no action is performed in the Loaded state
        verifyNoInteractions(context);
    }

    @Test
    public void testHandleBatteryCheck_batteryCapacityLessThan25() {
        // Create test objects
        DroneContext context = mock(DroneContext.class);
        LoadedState loadedState = new LoadedState();

        // Set up the context
        when(context.getBatteryCapacity()).thenReturn(20);

        // Call the method under test
        loadedState.handleBatteryCheck(context);

        // Verify that the state is changed to ReturningState
        verify(context).changeState(any(ReturningState.class));
    }

    @Test
    public void testHandleBatteryCheck_batteryCapacityGreaterThan25() {
        // Create test objects
        DroneContext context = mock(DroneContext.class);
        LoadedState loadedState = new LoadedState();

        // Set up the context
        when(context.getBatteryCapacity()).thenReturn(30);

        // Call the method under test
        loadedState.handleBatteryCheck(context);

        // Verify that the state is changed to DeliveringState
        verify(context).changeState(any(DeliveringState.class));
    }

    @Test
    public void testHandleReturn() {
        // Create test objects
        DroneContext context = mock(DroneContext.class);
        LoadedState loadedState = new LoadedState();

        // Call the method under test
        loadedState.handleReturn(context);

        // Verify that no action is performed in the Loaded state
        verifyNoInteractions(context);
    }

    @Test
    public void testGetStateName() {
        // Create LoadedState instance
        LoadedState loadedState = new LoadedState();

        // Call the method under test
        DroneStateName stateName = loadedState.getStateName();

        // Verify the state name
        assertEquals(DroneStateName.LOADED, stateName);
    }
}

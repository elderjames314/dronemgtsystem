package com.blusalt.dronemgtsystem.operation.dronestate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.blusalt.dronemgtsystem.enums.DroneStateName;

public class LoadedStateTest {
    @Test
    void testHandleBatteryCheck_BatteryLevelBelowThreshold() {
        // Create an instance of the LoadedState
        LoadedState loadedState = new LoadedState();

        // Create an instance of the DroneContext with battery capacity below the
        // threshold
        DroneContext droneContext = new DroneContext(1234L);
        droneContext.setBatteryCapacity(20);

        // Call the handleBatteryCheck method
        loadedState.handleBatteryCheck(droneContext);

        // Verify that the state is changed to ReturningState
        DroneStateName currentStateName = droneContext.getCurrentState();
        assertEquals(DroneStateName.RETURNING, currentStateName);
    }

    @Test
    void testHandleBatteryCheck_BatteryLevelAboveThreshold() {
        // Create an instance of the LoadedState
        LoadedState loadedState = new LoadedState();

        // Create an instance of the DroneContext with battery capacity above the threshold
        DroneContext droneContext = new DroneContext(1234L);
        droneContext.setBatteryCapacity(30);

        // Call the handleBatteryCheck method
        loadedState.handleBatteryCheck(droneContext);

        // Verify that the state is changed to DeliveringState
        DroneStateName currentStateName = droneContext.getCurrentState();
        assertEquals(DroneStateName.DELIVERING, currentStateName);
    }

    @Test
    void testGetStateName() {
        // Create an instance of the LoadedState
        LoadedState loadedState = new LoadedState();

        // Call the getStateName method
        DroneStateName stateName = loadedState.getStateName();

        // Verify that the state name is LOADED
        assertEquals(DroneStateName.LOADED, stateName);
    }
}

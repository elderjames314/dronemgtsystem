package com.blusalt.dronemgtsystem.operation.dronestate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.blusalt.dronemgtsystem.enums.DroneStateName;

public class ReturningStateTest {
    
    @Test
    void testGetStateName() {
        // Create an instance of the ReturningState
        ReturningState returningState = new ReturningState();

        // Call the getStateName method
        DroneStateName stateName = returningState.getStateName();

        // Verify that the state name is RETURNING
        assertEquals(DroneStateName.RETURNING, stateName);
    }
}

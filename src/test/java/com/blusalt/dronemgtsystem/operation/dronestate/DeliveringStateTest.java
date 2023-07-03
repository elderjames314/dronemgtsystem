package com.blusalt.dronemgtsystem.operation.dronestate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import com.blusalt.dronemgtsystem.enums.DroneStateName;

public class DeliveringStateTest {

    @Test
    void testHandleReturn() {
        // Create a mock DroneContext
        DroneContext context = mock(DroneContext.class);

        // Create an instance of the DeliveringState
        DeliveringState deliveringState = new DeliveringState();

        // Call the handleReturn method
        deliveringState.handleReturn(context);

        // Verify that the state transitions to ReturningState
        verify(context).changeState(any(ReturningState.class));
    }

    @Test
    void testGetStateName() {
        // Create an instance of the DeliveringState
        DeliveringState deliveringState = new DeliveringState();

        // Call the getStateName method
        DroneStateName stateName = deliveringState.getStateName();

        // Assert that the state name is correct
        assertEquals(DroneStateName.DELIVERING, stateName);
    }
}

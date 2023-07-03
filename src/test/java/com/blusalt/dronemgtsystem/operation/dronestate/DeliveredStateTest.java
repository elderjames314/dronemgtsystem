package com.blusalt.dronemgtsystem.operation.dronestate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import com.blusalt.dronemgtsystem.enums.DroneStateName;

public class DeliveredStateTest {

    @Test
    void testHandleReturn() {
        // Create a mock DroneContext
        DroneContext context = mock(DroneContext.class);

        // Create an instance of the DeliveredState
        DeliveredState deliveredState = new DeliveredState();

        // Call the handleReturn method
        deliveredState.handleReturn(context);

        // Verify that the state transitions to ReturningState
        verify(context).changeState(any(ReturningState.class));
    }

    @Test
    void testGetStateName() {
        // Create an instance of the DeliveredState
        DeliveredState deliveredState = new DeliveredState();

        // Call the getStateName method
        DroneStateName stateName = deliveredState.getStateName();

        // Assert that the state name is correct
        assertEquals(DroneStateName.DELIVERED, stateName);
    }
}

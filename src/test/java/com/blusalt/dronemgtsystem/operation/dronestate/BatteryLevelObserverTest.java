package com.blusalt.dronemgtsystem.operation.dronestate;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.blusalt.dronemgtsystem.enums.DroneStateName;

@ExtendWith(MockitoExtension.class)
public class BatteryLevelObserverTest {

    @Mock
    private DroneContext droneContext;

    @Mock
    private BatteryLevelAuditLogger batteryLevelAuditLogger;

    @InjectMocks
    private BatteryLevelObserver batteryLevelObserver;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdate_WhenDroneStateIsLoaded() {
        // Set up the mock droneContext
        when(droneContext.getCurrentState()).thenReturn(DroneStateName.LOADED);
        when(droneContext.getBatteryCapacity()).thenReturn(80);

        // Set the droneId before creating the BatteryLevelObserver instance
        Long droneId = 1L;

        // Create the BatteryLevelObserver instance with the droneId and batteryLevelAuditLogger mock
        BatteryLevelObserver batteryLevelObserver = new BatteryLevelObserver(droneId, batteryLevelAuditLogger);

        // Call the update method of the batteryLevelObserver
        batteryLevelObserver.update(droneContext, null);

        // Verify that the createBatteryLevelAuditLog method was called with the correct arguments
        verify(batteryLevelAuditLogger).createBatteryLevelAuditLog(eq(droneId), eq(80));
    }

    @Test
    void testUpdate_WhenDroneStateIsNotLoaded() {
        // Set up the mock droneContext
        when(droneContext.getCurrentState()).thenReturn(DroneStateName.DELIVERING);

        // Call the update method of the batteryLevelObserver
        batteryLevelObserver.update(droneContext, null);

        // Verify that the createBatteryLevelAuditLog method was not called
        verify(batteryLevelAuditLogger, never()).createBatteryLevelAuditLog(anyLong(), anyInt());
    }
}

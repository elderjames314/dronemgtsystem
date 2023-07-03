package com.blusalt.dronemgtsystem.operation.dronestate;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.blusalt.dronemgtsystem.model.Drone;

@ExtendWith(MockitoExtension.class)
public class BatteryLevelMonitorTest {

    @Mock
    private BatteryLevelAuditLogger batteryLevelAuditLogger;

    @InjectMocks
    private BatteryLevelMonitor batteryLevelMonitor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testStartMonitoring() {
        // Create a list of sample drones
        Drone drone1 = new Drone();
        drone1.setId(1L);
        drone1.setBatteryCapacity(80);

        Drone drone2 = new Drone();
        drone2.setId(2L);
        drone2.setBatteryCapacity(60);

        List<Drone> drones = Arrays.asList(drone1, drone2);

        // Create a mock for the BatteryLevelAuditLogger
        BatteryLevelAuditLogger batteryLevelAuditLogger = mock(BatteryLevelAuditLogger.class);

        // Create a mock for the ScheduledExecutorService
        ScheduledExecutorService executorService = mock(ScheduledExecutorService.class);

        // Create an instance of the BatteryLevelMonitor
        BatteryLevelMonitor batteryLevelMonitor = new BatteryLevelMonitor(drones, batteryLevelAuditLogger);
        batteryLevelMonitor.setExecutorService(executorService);

        // Start the monitoring
        batteryLevelMonitor.startMonitoring();

        // Wait for a certain period to allow the monitoring to run
        // Adjust the sleep time according to the CHECK_INTERVAL_SECONDS in the
        // BatteryLevelMonitor class
        try {
            Thread.sleep(2000); // Wait for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify that the checkBatteryLevels method was called for each drone
        verify(batteryLevelAuditLogger, times(drones.size())).createBatteryLevelAuditLog(anyLong(), anyInt());

        // verify that the checkBatteryLevels method was called
        verify(batteryLevelAuditLogger).createBatteryLevelAuditLog(1L, 80);
        verify(batteryLevelAuditLogger).createBatteryLevelAuditLog(2L, 60);
    }
}

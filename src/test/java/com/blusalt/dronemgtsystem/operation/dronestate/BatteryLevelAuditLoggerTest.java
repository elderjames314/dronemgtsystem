package com.blusalt.dronemgtsystem.operation.dronestate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.blusalt.dronemgtsystem.model.BatteryAuditLog;
import com.blusalt.dronemgtsystem.model.Drone;
import com.blusalt.dronemgtsystem.repository.BatteryAuditLogRepository;
import com.blusalt.dronemgtsystem.repository.DroneRepository;

@ExtendWith(MockitoExtension.class)
public class BatteryLevelAuditLoggerTest {
    @Mock
    private DroneRepository droneRepository;

    @Mock
    private BatteryAuditLogRepository batteryAuditRepository;

    @InjectMocks
    private BatteryLevelAuditLogger batteryLevelAuditLogger;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        batteryLevelAuditLogger = new BatteryLevelAuditLogger(droneRepository, batteryAuditRepository);
    }

    @Test
    public void testCreateBatteryLevelAuditLog() {
        // Create a sample drone and battery level
        Long droneId = 1L;
        int batteryLevel = 80;

        Drone drone = new Drone();
        drone.setId(droneId);
        drone.setSerialNumber("DRN001");
        // Set other properties of the drone

        // Create a sample battery audit log
        BatteryAuditLog batteryAuditLog = new BatteryAuditLog();
        batteryAuditLog.setBatteryCapacity(String.valueOf(batteryLevel));
        batteryAuditLog.setBatteryDetails("Battery details");
        batteryAuditLog.setDroneSerialNumber(drone.getSerialNumber());
        batteryAuditLog.setMessage("Loading successful and battery decreases accordingly");

        // Mock the drone repository to return the sample drone when findById is called
        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));

        // Mock the battery audit log repository to capture the saved audit log
        ArgumentCaptor<BatteryAuditLog> auditLogCaptor = ArgumentCaptor.forClass(BatteryAuditLog.class);
        when(batteryAuditRepository.save(auditLogCaptor.capture())).thenReturn(batteryAuditLog);

        // Call the method under test
        batteryLevelAuditLogger.createBatteryLevelAuditLog(droneId, batteryLevel);

        // Verify that the drone battery level was updated
        verify(droneRepository).save(drone);

        // Verify that the battery audit log was saved with the correct values
        BatteryAuditLog savedAuditLog = auditLogCaptor.getValue();
        assertEquals(String.valueOf(batteryLevel), savedAuditLog.getBatteryCapacity());
        assertEquals("Battery details", savedAuditLog.getBatteryDetails());
        assertEquals(drone.getSerialNumber(), savedAuditLog.getDroneSerialNumber());
        assertEquals("Loading successful and battery deacreases accordingly", savedAuditLog.getMessage());
    }
}

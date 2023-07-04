package com.blusalt.dronemgtsystem.operation.dronestate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.blusalt.dronemgtsystem.enums.DroneStateName;
import com.blusalt.dronemgtsystem.model.BatteryAuditLog;
import com.blusalt.dronemgtsystem.model.Drone;
import com.blusalt.dronemgtsystem.repository.BatteryAuditLogRepository;
import com.blusalt.dronemgtsystem.repository.DroneRepository;

@ExtendWith(MockitoExtension.class)
public class BatteryLevelObserverTest {

    @Mock
    private DroneRepository droneRepository;
    @Mock
    private BatteryAuditLogRepository batteryAuditLogRepository;

    @InjectMocks
    private BatteryLevelObserver batteryLevelObserver;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

   @Test
    public void testUpdate_DroneContextLoadedState_ShouldUpdateBatteryAndCreateAuditLog() {
        // Arrange
        Long droneId = 1L;
        int batteryLevel = 80;
        DroneContext droneContext = mock(DroneContext.class);
        Drone drone = new Drone();
        drone.setSerialNumber("DRN123");
        batteryLevelObserver.setDroneId(1L);
        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));
        when(droneContext.getCurrentState()).thenReturn(DroneStateName.LOADED);
        when(droneContext.getBatteryCapacity()).thenReturn(batteryLevel);

        // Act
        batteryLevelObserver.update(droneContext, droneContext);

        // Assert
        ArgumentCaptor<Drone> droneArgumentCaptor = ArgumentCaptor.forClass(Drone.class);
        verify(droneRepository).save(droneArgumentCaptor.capture());
        Drone updatedDrone = droneArgumentCaptor.getValue();
        assertEquals(batteryLevel, updatedDrone.getBatteryCapacity());

        ArgumentCaptor<BatteryAuditLog> auditLogArgumentCaptor = ArgumentCaptor.forClass(BatteryAuditLog.class);
        verify(batteryAuditLogRepository).save(auditLogArgumentCaptor.capture());
        BatteryAuditLog auditLog = auditLogArgumentCaptor.getValue();
        assertEquals(String.valueOf(batteryLevel), auditLog.getBatteryCapacity());
        assertEquals("Battery details", auditLog.getBatteryDetails());
        assertEquals("DRN123", auditLog.getDroneSerialNumber());
        assertEquals("Loading successful and battery deacreases accordingly", auditLog.getMessage());
    }
}

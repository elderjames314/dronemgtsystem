package com.blusalt.dronemgtsystem.operation.dronestate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.glassfish.jaxb.core.v2.model.core.ID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.blusalt.dronemgtsystem.enums.DroneStateName;
import com.blusalt.dronemgtsystem.enums.DroneStates;
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

    private BatteryLevelObserver batteryLevelObserver;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        batteryLevelObserver = new BatteryLevelObserver(droneRepository, batteryAuditLogRepository);
    }

    @Test
    public void testUpdate_LoadedState() {
        // Mock data
        Long droneId = 1L;
        int remainingBatteryLevel = 80;

        DroneContext droneContext = new DroneContext();
        droneContext.setCurrentState(new LoadedState());

        Drone drone = new Drone();
        drone.setId(droneId);
        drone.setBatteryCapacity(0);
        drone.setState(DroneStates.IDLE);

        BatteryAuditLog batteryAuditLog = new BatteryAuditLog();
        batteryAuditLog.setBatteryCapacity(String.valueOf(remainingBatteryLevel));
        batteryAuditLog.setBatteryDetails("Battery details");
        batteryAuditLog.setDroneSerialNumber(drone.getSerialNumber());
        batteryAuditLog.setMessage("Loading successful and battery decreases accordingly");

        // Configure mocks
        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));
        when(batteryAuditLogRepository.save(any(BatteryAuditLog.class))).thenReturn(batteryAuditLog);

        // Call the method under test
        batteryLevelObserver.setDroneId(droneId);
        batteryLevelObserver.setRemainingBatteryLevel(remainingBatteryLevel);
        batteryLevelObserver.update(droneContext, null);

        // Verify the interactions
        verify(droneRepository, times(2)).save(drone);

        // Assert the state changes
        assertEquals(remainingBatteryLevel, drone.getBatteryCapacity());
        assertEquals(DroneStates.LOADED, drone.getState());
    }
}

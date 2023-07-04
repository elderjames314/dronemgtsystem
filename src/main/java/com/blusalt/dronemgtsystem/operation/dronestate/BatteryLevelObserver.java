package com.blusalt.dronemgtsystem.operation.dronestate;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.blusalt.dronemgtsystem.enums.DroneStateName;
import com.blusalt.dronemgtsystem.enums.DroneStates;
import com.blusalt.dronemgtsystem.model.BatteryAuditLog;
import com.blusalt.dronemgtsystem.model.Drone;
import com.blusalt.dronemgtsystem.repository.BatteryAuditLogRepository;
import com.blusalt.dronemgtsystem.repository.DroneRepository;

/**
 * The BatteryLevelObserver focuses on observing the state changes of individual
 * drones
 * and performing actions accordingly, such as updating the battery level and
 * triggering events
 * based on the state transitions
 */
@Component
@RequiredArgsConstructor
public class BatteryLevelObserver implements Observer {

    private final DroneRepository droneRepository;

    private final BatteryAuditLogRepository BatteryAuditRepository;

    private Long droneId;
    private int remainingBatteryLevel;

    public long getDroneId() {
        return droneId;
    }

    public void setDroneId(long droneId) {
        this.droneId = droneId;
    }

    public void setRemainingBatteryLevel(int batteryLevel) {
        this.remainingBatteryLevel = batteryLevel;
    }

    public int getRemainingBatteryLevel() {
        return remainingBatteryLevel;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof DroneContext) {
            DroneContext droneContext = (DroneContext) o;
            Optional<Drone> drone = droneRepository.findById(droneId);

            if (drone.isPresent()) {
                if (droneContext.getCurrentState() == DroneStateName.LOADED) {
                    drone.get().setBatteryCapacity(getRemainingBatteryLevel());
                    drone.get().setState(DroneStates.valueOf(droneContext.getCurrentState().name()));
                    droneRepository.save(drone.get());
                    // battery audit log
                    BatteryAuditLog batteryAuditLog = new BatteryAuditLog();
                    batteryAuditLog.setBatteryCapacity(String.valueOf(getRemainingBatteryLevel()));
                    batteryAuditLog.setBatteryDetails("Battery details");
                    batteryAuditLog.setDroneSerialNumber(drone.get().getSerialNumber());
                    batteryAuditLog.setMessage("Loading successful and battery deacreases accordingly");

                    BatteryAuditRepository.save(batteryAuditLog);

                }

                // update the drone for other states eg delivery etc
                drone.get().setState(DroneStates.valueOf(droneContext.getCurrentState().name()));
                droneRepository.save(drone.get());
            }

            

        }
    }
}

package com.blusalt.dronemgtsystem.operation.dronestate;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blusalt.dronemgtsystem.enums.DroneStateName;
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

    @Autowired
    private DroneRepository droneRepository;
    
    @Autowired
    private BatteryAuditLogRepository BatteryAuditRepository;

    private Long droneId;

    public BatteryLevelObserver(Long droneId) {
        this.droneId = droneId;
    }

    public long getDroneId() {
        return droneId;
    }

    public void setDroneId(long droneId) {
        this.droneId = droneId;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof DroneContext) {
            DroneContext droneContext = (DroneContext) o;
            if (droneContext.getCurrentState() == DroneStateName.LOADED) {
                int batteryLevel = droneContext.getBatteryCapacity();
                Optional<Drone> drone = droneRepository.findById(droneId);
                if (drone.isPresent()) {
                    drone.get().setBatteryCapacity(batteryLevel);
                    droneRepository.save(drone.get());

                    // battery audit log
                    BatteryAuditLog batteryAuditLog = new BatteryAuditLog();
                    batteryAuditLog.setBatteryCapacity(String.valueOf(batteryLevel));
                    batteryAuditLog.setBatteryDetails("Battery details");
                    batteryAuditLog.setDroneSerialNumber(drone.get().getSerialNumber());
                    batteryAuditLog.setMessage("Loading successful and battery deacreases accordingly");

                    BatteryAuditRepository.save(batteryAuditLog);

                }

            }
        }
    }
}

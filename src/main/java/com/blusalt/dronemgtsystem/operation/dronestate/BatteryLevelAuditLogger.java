package com.blusalt.dronemgtsystem.operation.dronestate;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blusalt.dronemgtsystem.model.BatteryAuditLog;
import com.blusalt.dronemgtsystem.model.Drone;
import com.blusalt.dronemgtsystem.repository.BatteryAuditLogRepository;
import com.blusalt.dronemgtsystem.repository.DroneRepository;

@Component
public class BatteryLevelAuditLogger {
    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private BatteryAuditLogRepository BatteryAuditRepository;

     public void createBatteryLevelAuditLog(Long droneId, int batteryLevel) {
        // update battery level
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

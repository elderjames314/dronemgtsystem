package com.blusalt.dronemgtsystem.operation.dronestate;

import java.util.Observable;
import java.util.Observer;
import com.blusalt.dronemgtsystem.enums.DroneStateName;

/**
 * The BatteryLevelObserver focuses on observing the state changes of individual
 * drones
 * and performing actions accordingly, such as updating the battery level and
 * triggering events
 * based on the state transitions
 */

public class BatteryLevelObserver implements Observer {

    private Long droneId;

    private BatteryLevelAuditLogger batteryLevelAuditLogger;

    public BatteryLevelObserver(Long droneId, BatteryLevelAuditLogger batteryLevelAuditLogger) {
        this.droneId = droneId;
        this.batteryLevelAuditLogger = batteryLevelAuditLogger;
    }

    public long getDroneId() {
        return droneId;
    }

    public BatteryLevelAuditLogger getBatteryLevelAuditLogger() {
        return batteryLevelAuditLogger;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof DroneContext) {
            DroneContext droneContext = (DroneContext) o;
            if (droneContext.getCurrentState() == DroneStateName.LOADED) {
                int batteryLevel = droneContext.getBatteryCapacity();
                getBatteryLevelAuditLogger().createBatteryLevelAuditLog(getDroneId(), batteryLevel);
            }
        }
    }

    // Implement other observers: MedicationLoadObserver, DroneStateChangeObserver
    // in the future
}

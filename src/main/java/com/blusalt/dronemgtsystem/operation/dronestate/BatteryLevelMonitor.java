package com.blusalt.dronemgtsystem.operation.dronestate;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.blusalt.dronemgtsystem.model.Drone;

/**
 * The BatteryLevelMonitor class, on the other hand, can be responsible for
 * periodically checking
 * the battery levels of all drones and creating audit log entries. This way,
 * you can have a centralized
 * monitoring mechanism that operates independently of the individual drone
 * states.
 */

public class BatteryLevelMonitor {

    private static final int CHECK_INTERVAL_SECONDS = 60;

    private final List<Drone> drones;

    private  BatteryLevelAuditLogger batteryLevelAuditLogger;

    private ScheduledExecutorService executorService;

    public BatteryLevelMonitor(List<Drone> drones, BatteryLevelAuditLogger batteryLevelAuditLogger) {
        this.drones = drones;
        this.batteryLevelAuditLogger = batteryLevelAuditLogger;
    }

    public void setBatteryLevelAuditLogger(BatteryLevelAuditLogger batteryLevelAuditLogger) {
        this.batteryLevelAuditLogger = batteryLevelAuditLogger;
    }

    public void startMonitoring() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::checkBatteryLevels, 0, CHECK_INTERVAL_SECONDS, TimeUnit.SECONDS);
    }

    public void stopMonitoring() {
        executorService.shutdown();
    }

    private void checkBatteryLevels() {
        for (Drone drone : drones) {
            int batteryLevel = drone.getBatteryCapacity();
            batteryLevelAuditLogger.createBatteryLevelAuditLog(drone.getId(), batteryLevel);
        }
    }

    public void setExecutorService(ScheduledExecutorService executorService) {
        this.executorService = executorService;
    }

}

package com.blusalt.dronemgtsystem.services;

import org.springframework.stereotype.Service;

import com.blusalt.dronemgtsystem.operation.dronestate.BatteryLevelMonitor;

import jakarta.annotation.PostConstruct;

/**
 * The BatteryLevelMonitoringService is a service bean that gets initialized after the Spring context is 
 * created (thanks to the @PostConstruct annotation). In its startMonitoring method, 
 * it invokes the startMonitoring method of the BatteryLevelMonitor, which starts monitoring the battery 
 * levels of the drones at the specified interval.
 */

@Service
public class BatteryLevelMonitoringService {

    private final BatteryLevelMonitor batteryLevelMonitor;

    public BatteryLevelMonitoringService(BatteryLevelMonitor batteryLevelMonitor) {
        this.batteryLevelMonitor = batteryLevelMonitor;
    }

    @PostConstruct
    public void startMonitoring() {
        batteryLevelMonitor.startMonitoring();
    }
    
}

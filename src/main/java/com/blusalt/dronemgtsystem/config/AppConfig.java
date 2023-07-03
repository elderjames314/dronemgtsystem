package com.blusalt.dronemgtsystem.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.blusalt.dronemgtsystem.model.Drone;
import com.blusalt.dronemgtsystem.operation.dronestate.BatteryLevelAuditLogger;
import com.blusalt.dronemgtsystem.operation.dronestate.BatteryLevelMonitor;
import com.blusalt.dronemgtsystem.repository.DroneRepository;

@Configuration
public class AppConfig {

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private BatteryLevelAuditLogger batteryLevelAuditLogger;

    @Bean
    public List<Drone> drones() {
        return droneRepository.findAll();
    }

    @Bean
    public BatteryLevelMonitor batteryLevelMonitor(List<Drone> drones) {
        return new BatteryLevelMonitor(drones, batteryLevelAuditLogger);
    }
    
}

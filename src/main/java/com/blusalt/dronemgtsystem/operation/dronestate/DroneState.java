package com.blusalt.dronemgtsystem.operation.dronestate;

import java.util.List;

import org.springframework.stereotype.Component;

import com.blusalt.dronemgtsystem.enums.DroneStateName;
import com.blusalt.dronemgtsystem.model.Medication;

public interface DroneState {
    void handleLoadMedication(DroneContext context, List<Medication> medications);

    void handleBatteryCheck(DroneContext context);

    void handleReturn(DroneContext context);

    DroneStateName getStateName();
}

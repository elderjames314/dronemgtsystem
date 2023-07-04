package com.blusalt.dronemgtsystem.operation.dronestate;

import java.util.List;

import org.springframework.stereotype.Component;

import com.blusalt.dronemgtsystem.enums.DroneStateName;
import com.blusalt.dronemgtsystem.model.Medication;

@Component
public class DeliveredState implements DroneState {
     @Override
    public void handleLoadMedication(DroneContext context, List<Medication> medications) {
        // No action in the Delivered state
    }

    @Override
    public void handleBatteryCheck(DroneContext context) {
        // No action in the Delivered state
    }

    @Override
    public void handleReturn(DroneContext context) {
        context.changeState(new ReturningState());
    }

    @Override
    public DroneStateName getStateName() {
        return DroneStateName.DELIVERED;
    }
}

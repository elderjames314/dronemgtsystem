package com.blusalt.dronemgtsystem.operation.dronestate;

import java.util.List;

import com.blusalt.dronemgtsystem.enums.DroneStateName;
import com.blusalt.dronemgtsystem.model.Medication;

public class DeliveringState implements DroneState {
    @Override
    public void handleLoadMedication(DroneContext context, List<Medication> medications) {
        // No action in the Delivering state
    }

    @Override
    public void handleBatteryCheck(DroneContext context) {
        // No action in the Delivering state
    }

    @Override
    public void handleReturn(DroneContext context) {
        context.changeState(new ReturningState());
    }

    @Override
    public DroneStateName getStateName() {
        return DroneStateName.DELIVERING;
    }
}

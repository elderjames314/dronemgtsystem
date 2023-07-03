package com.blusalt.dronemgtsystem.operation.dronestate;

import java.util.List;

import com.blusalt.dronemgtsystem.enums.DroneStateName;
import com.blusalt.dronemgtsystem.model.Medication;

public class LoadedState implements DroneState {
    @Override
    public void handleLoadMedication(DroneContext context, List<Medication> medications) {
        // No action in the Loaded state
    }

    @Override
    public void handleBatteryCheck(DroneContext context) {
        // Check battery level and transition to the appropriate state
        if (context.getBatteryCapacity() < 25) {
            context.changeState(new ReturningState());
        } else {
            context.changeState(new DeliveringState());
        }
    }

    @Override
    public void handleReturn(DroneContext context) {
        // No action in the Loaded state
    }

    @Override
    public DroneStateName getStateName() {
        return DroneStateName.LOADED;
    }
}

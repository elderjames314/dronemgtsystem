package com.blusalt.dronemgtsystem.operation.dronestate;

import java.util.List;

import com.blusalt.dronemgtsystem.enums.DroneStateName;
import com.blusalt.dronemgtsystem.model.Medication;

public class IdleState implements DroneState {

    @Override
    public void handleLoadMedication(DroneContext context, List<Medication> medications) {
        context.setMedications(medications);
        context.changeState(new LoadedState());
    }

    @Override
    public void handleBatteryCheck(DroneContext context) {
        // No action in the Idle state
    }

    @Override
    public void handleReturn(DroneContext context) {
        // No action in the Idle state
    }

    @Override
    public DroneStateName getStateName() {
        return DroneStateName.IDLE;
    }

}

package com.blusalt.dronemgtsystem.operation.dronestate;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.blusalt.dronemgtsystem.enums.DroneStateName;
import com.blusalt.dronemgtsystem.model.Medication;

@Component
@Primary
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

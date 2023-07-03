package com.blusalt.dronemgtsystem.operation.dronestate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.blusalt.dronemgtsystem.enums.DroneStateName;
import com.blusalt.dronemgtsystem.model.Medication;

public class IdleStateTest {
    @Test
    void testHandleLoadMedication() {
        // Create an instance of the IdleState
        IdleState idleState = new IdleState();

        // Create an instance of the DroneContext
        DroneContext droneContext = new DroneContext(1234L);

        // Create a list of medications
        List<Medication> medications = new ArrayList<>();
        Medication medication1 = new Medication();
        medication1.setCode("45L");
        medication1.setName("Med1");
        Medication medication2 = new Medication();
        medication2.setCode("45UIL");
        medication2.setName("Med2");
        medications.add(medication1);
        ;
        medications.add(medication2);

        // Call the handleLoadMedication method
        idleState.handleLoadMedication(droneContext, medications);

        // Verify that the medications are set in the DroneContext
        List<Medication> retrievedMedications = droneContext.getMedications();
        assertEquals(medications, retrievedMedications);

        // Verify that the state is changed to LoadedState
        DroneStateName currentStateName = droneContext.getCurrentState();
        assertEquals(DroneStateName.LOADED, currentStateName);
    }

    @Test
    void testGetStateName() {
        // Create an instance of the IdleState
        IdleState idleState = new IdleState();

        // Call the getStateName method
        DroneStateName stateName = idleState.getStateName();

        // Verify that the state name is IDLE
        assertEquals(DroneStateName.IDLE, stateName);
    }
}

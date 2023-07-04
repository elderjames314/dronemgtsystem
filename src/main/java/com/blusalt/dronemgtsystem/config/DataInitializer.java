package com.blusalt.dronemgtsystem.config;

import com.blusalt.dronemgtsystem.enums.DroneModel;
import com.blusalt.dronemgtsystem.enums.DroneState;
import com.blusalt.dronemgtsystem.model.Drone;
import com.blusalt.dronemgtsystem.model.Medication;
import com.blusalt.dronemgtsystem.repository.DeliveryRepository;
import com.blusalt.dronemgtsystem.repository.DroneRepository;
import com.blusalt.dronemgtsystem.repository.MedicationRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;

    public DataInitializer(DroneRepository droneRepository, DeliveryRepository deliveryRepository,
            MedicationRepository medicationRepository) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        createMedications();
        createDrones();
    }

    private void createDrones() {
        for (int i = 1; i <= 10; i++) {
            Drone drone = new Drone();
            drone.setSerialNumber("Drone-" + i);
            drone.setModel(DroneModel.LIGHTWEIGHT);
            drone.setWeightLimit(10.0);
            drone.setState(DroneState.IDLE);
            drone.setBatteryCapacity(100);
            droneRepository.save(drone);
        }
    }

    private void createMedications() {
        for (int i = 1; i <= 100; i++) {
            Medication medication = new Medication();
            medication.setName("Medication" + i);
            medication.setWeight(0.5);
            medication.setCode("CODE" + i);
            medicationRepository.save(medication);
        }
    }

}

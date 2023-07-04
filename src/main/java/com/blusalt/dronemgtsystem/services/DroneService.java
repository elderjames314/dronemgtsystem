package com.blusalt.dronemgtsystem.services;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.blusalt.dronemgtsystem.dtos.DeliveryDto;
import com.blusalt.dronemgtsystem.enums.DroneStates;
import com.blusalt.dronemgtsystem.exceptions.InvalidRequestException;
import com.blusalt.dronemgtsystem.exceptions.NotFoundException;
import com.blusalt.dronemgtsystem.model.Delivery;
import com.blusalt.dronemgtsystem.model.Drone;
import com.blusalt.dronemgtsystem.model.Medication;
import com.blusalt.dronemgtsystem.operation.dronestate.DroneContext;
import com.blusalt.dronemgtsystem.operation.dronestate.DroneState;
import com.blusalt.dronemgtsystem.repository.DeliveryRepository;
import com.blusalt.dronemgtsystem.repository.DroneRepository;
import com.blusalt.dronemgtsystem.repository.MedicationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DroneService {
    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;
    private final DeliveryRepository deliveryRepository;
    private final DroneContext droneContext;

    public void deliverMedicationsLoadedOnDrone(DeliveryDto deliveryDto) {

        Drone drone = getDroneById(deliveryDto.getDroneId());

        validateDroneStateForLoading(drone);

        List<Long> medicationIds = deliveryDto.getMedicationIds();
        List<Medication> medications = getMedicationsByIds(medicationIds);
        validateDroneWeightCapacity(drone, medications);

        droneContext.setDroneId(drone.getId()); // Set the droneId in the DroneContext
        loadMedicationsOnDrone(medications);

        Delivery delivery = createDelivery(drone, medications, deliveryDto);
        saveDelivery(delivery);
    }

    public List<Medication> getLoadedMedicationsForDrone(Long droneId) {
        Drone drone = getDroneById(droneId);
        if (drone.getState() != DroneStates.LOADED) {
            throw new InvalidRequestException("Drone is not in LOADED state");
        }

        List<Delivery> deliveries = drone.getDeliveries();
        if (deliveries != null) {
            return deliveries.stream()
                    .flatMap(delivery -> delivery.getMedications().stream())
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<Drone> getAvailableDronesForLoading() {
        return droneRepository.findByState(DroneStates.IDLE.name());
    }

    public int getDroneBatteryLevel(Long droneId) {
        Drone drone = getDroneById(droneId);
        return drone.getBatteryCapacity();
    }



    private Drone getDroneById(Long droneId) {
        Optional<Drone> droneOptional = droneRepository.findById(droneId);
        return droneOptional.orElseThrow(() -> new NotFoundException("Drone not found with ID: " + droneId));
    }

    private List<Medication> getMedicationsByIds(List<Long> medicationIds) {
        return medicationRepository.findByIdIn(medicationIds);
    }

    private void validateDroneStateForLoading(Drone drone) {
        if (!drone.getState().equals(DroneStates.IDLE)) {
            throw new InvalidRequestException("Drone is not in IDLE state");
        }
    }

    private void validateDroneWeightCapacity(Drone drone, List<Medication> medications) {
        double totalWeight = medications.stream()
                .mapToDouble(Medication::getWeight)
                .sum();
        if (totalWeight > drone.getWeightLimit()) {
            throw new InvalidRequestException("Drone cannot carry medications exceeding weight limit");
        }
    }

    private void loadMedicationsOnDrone(List<Medication> medications) {
        droneContext.loadMedications(medications); // No need to pass the DroneContext as a parameter anymore
    }

    private Delivery createDelivery(Drone drone, List<Medication> medications, DeliveryDto deliveryDto) {
        Delivery delivery = new Delivery();
        delivery.setDrone(drone);
        delivery.setMedications(medications);
        delivery.setDeliveryLocation(deliveryDto.getLocation());
        delivery.setDeliveryDate(LocalDateTime.now());
        return delivery;
    }

    private void saveDelivery(Delivery delivery) {
        deliveryRepository.save(delivery);
    }
}

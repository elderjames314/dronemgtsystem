package com.blusalt.dronemgtsystem.controllers;

import com.blusalt.dronemgtsystem.dtos.CreatedDrone;
import com.blusalt.dronemgtsystem.dtos.DeliveryDto;
import com.blusalt.dronemgtsystem.dtos.DroneDto;
import com.blusalt.dronemgtsystem.enums.DroneModel;
import com.blusalt.dronemgtsystem.exceptions.InvalidRequestException;
import com.blusalt.dronemgtsystem.model.Drone;
import com.blusalt.dronemgtsystem.model.Medication;
import com.blusalt.dronemgtsystem.operation.drone.DroneFactory;
import com.blusalt.dronemgtsystem.services.DroneService;
import com.blusalt.dronemgtsystem.utils.Response;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/drone")
@RequiredArgsConstructor
public class DispatchController {

    private final DroneFactory droneFactory;
    private final DroneService droneService;

    @PostMapping
    public ResponseEntity<Response<CreatedDrone>> registerDrone(@RequestBody DroneDto droneDto) {
        DroneDto validatedDroneDto = DroneDto.developDroneModel(droneDto);
        Drone drone = createDroneFromDto(validatedDroneDto);

        CreatedDrone createdDrone = mapToCreatedDrone(drone);

        Response<CreatedDrone> response = Response.success(createdDrone);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/deliver")
    public ResponseEntity<Response<?>> deliverMedicationsLoadedOnDrone(@RequestBody DeliveryDto deliveryDto) {
        droneService.deliverMedicationsLoadedOnDrone(deliveryDto);
        Response<?> response = Response.success(null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{droneId}/medications")
    public ResponseEntity<Response<?>> getLoadedMedicationsForDrone(@PathVariable Long droneId) {
        List<Medication> medications = droneService.getLoadedMedicationsForDrone(droneId);
        Response<?> response = Response.success(medications);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/available")
    public ResponseEntity<Response<?>> getAvailableDronesForLoading() {
        List<Drone> availableDrones = droneService.getAvailableDronesForLoading();
        Response<?> response = Response.success(availableDrones);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{droneId}/battery")
    public ResponseEntity<Response<?>> getDroneBatteryLevel(@PathVariable Long droneId) {
        int batteryLevel = droneService.getDroneBatteryLevel(droneId);
        Response<?> response = Response.success(batteryLevel);
        return ResponseEntity.ok(response);
    }

    private Drone createDroneFromDto(DroneDto droneDto) {
        DroneModel droneModel = droneDto.getModelPassed(droneDto.getModel());
        switch (droneModel) {
            case LIGHTWEIGHT:
                return droneFactory.createLightweightDrone(droneDto);
            case MIDDLEWEIGHT:
                return droneFactory.createMiddleweightDrone(droneDto);
            case HEAVYWEIGHT:
                return droneFactory.createHeavyweightDrone(droneDto);
            case CRUISERWEIGHT:
                return droneFactory.createHeavyweightDrone(droneDto);
            default:
                throw new InvalidRequestException("Invalid drone model: " + droneModel);
        }
    }

    private CreatedDrone mapToCreatedDrone(Drone drone) {
        return CreatedDrone.builder()
                .id(drone.getId())
                .serialNumber(drone.getSerialNumber())
                .batteryCapacity(drone.getBatteryCapacity())
                .weightLimit(drone.getWeightLimit())
                .model(drone.getModel().name())
                .build();
    }
}

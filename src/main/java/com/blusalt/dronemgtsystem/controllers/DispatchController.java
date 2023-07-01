package com.blusalt.dronemgtsystem.controllers;

import com.blusalt.dronemgtsystem.dtos.CreatedDrone;
import com.blusalt.dronemgtsystem.dtos.DroneDto;
import com.blusalt.dronemgtsystem.enums.DroneModel;
import com.blusalt.dronemgtsystem.exceptions.InvalidRequestException;
import com.blusalt.dronemgtsystem.model.Drone;
import com.blusalt.dronemgtsystem.operation.drone.DroneBaseFactory;
import com.blusalt.dronemgtsystem.operation.drone.MiddleweightDroneFactory;
import com.blusalt.dronemgtsystem.utils.DroneConstant;
import com.blusalt.dronemgtsystem.utils.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/drone")
@RequiredArgsConstructor
public class DispatchController {

    private final DroneBaseFactory droneBaseFactory;

    @PostMapping
    public ResponseEntity<Response<CreatedDrone>> registerDrone(@RequestBody DroneDto droneDto) {
        DroneDto validatedDroneDto = DroneDto.developDroneModel(droneDto);
        Drone drone = createDroneFromDto(validatedDroneDto);

        CreatedDrone createdDrone = mapToCreatedDrone(drone);

        Response<CreatedDrone> response = Response.getResponse(createdDrone, null, true, DroneConstant.SUCCESS,
                HttpStatus.CREATED.value());
        return ResponseEntity.ok().body(response);
    }

    private Drone createDroneFromDto(DroneDto droneDto) {
        DroneModel droneModel = droneDto.getModelPassed(droneDto.getModel());
        return droneBaseFactory.createDrone(droneDto, droneModel);
    }

    private CreatedDrone mapToCreatedDrone(Drone drone) {
        return CreatedDrone.builder()
                .id(drone.getId())
                .serialNumber(drone.getSerialNumber())
                .batteryCapacity(drone.getBatteryCapacity())
                .weightLimit(drone.getWeightLimit())
                .build();
    }
}

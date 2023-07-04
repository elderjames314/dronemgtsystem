package com.blusalt.dronemgtsystem.operation.drone;

import com.blusalt.dronemgtsystem.dtos.DroneDto;
import com.blusalt.dronemgtsystem.enums.DroneModel;
import com.blusalt.dronemgtsystem.model.Drone;
import com.blusalt.dronemgtsystem.repository.DroneRepository;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class DroneBaseFactory implements DroneFactory {

    @Autowired
    private DroneRepository droneRepository;

    @Override
    public Drone createDrone(DroneDto droneDto, DroneModel droneModel) {
        switch (droneModel) {
            case LIGHTWEIGHT:
                return createLightweightDrone(droneDto);
            case MIDDLEWEIGHT:
                return createMiddleweightDrone(droneDto);
            case HEAVYWEIGHT:
                return createHeavyweightDrone(droneDto);
            default:
                throw new IllegalArgumentException("Invalid drone model: " + droneModel);
        }
    }

    @Override
    public Drone createLightweightDrone(DroneDto droneDto) {
        return getDrone(droneDto, DroneModel.LIGHTWEIGHT);
    }

    @Override
    public Drone createMiddleweightDrone(DroneDto droneDto) {
        return getDrone(droneDto, DroneModel.MIDDLEWEIGHT);
    }

    @Override
    public Drone createHeavyweightDrone(DroneDto droneDto) {
        return getDrone(droneDto, DroneModel.HEAVYWEIGHT);
    }

    @Override
    public Drone createCruiserweightDrone(DroneDto droneDto) {
        return getDrone(droneDto, DroneModel.CRUISERWEIGHT);
    }

    private Drone saveDrone(Drone drone) {
        return droneRepository.save(drone);
    }

    private Drone getDrone(DroneDto droneDto, DroneModel droneModel) {
        Drone drone = new Drone();
        drone.setBatteryCapacity(droneDto.getBatteryCapacity());
        drone.setSerialNumber(droneDto.getSerialNumber());
        drone.setWeightLimit(Double.parseDouble(droneDto.getWeightLimit()));
        drone.setModel(droneModel);
        return saveDrone(drone);
    }

}

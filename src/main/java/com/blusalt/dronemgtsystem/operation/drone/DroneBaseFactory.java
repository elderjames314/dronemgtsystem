package com.blusalt.dronemgtsystem.operation.drone;

import com.blusalt.dronemgtsystem.dtos.DroneDto;
import com.blusalt.dronemgtsystem.enums.DroneModel;
import com.blusalt.dronemgtsystem.model.Drone;

public abstract class DroneBaseFactory implements DroneFactory {

    public Drone createDrone(DroneDto droneDto, DroneModel droneModel) {
        DroneDto developedDroneModel = DroneDto.developDroneModel(droneDto);
        Drone drone = new Drone();
        drone.setBatteryCapacity(developedDroneModel.getBatteryCapacity());
        drone.setModel(droneModel);
        drone.setSerialNumber(developedDroneModel.getSerialNumber());
        drone.setWeightLimit(developedDroneModel.getBatteryCapacity());
        return drone;
    }
}

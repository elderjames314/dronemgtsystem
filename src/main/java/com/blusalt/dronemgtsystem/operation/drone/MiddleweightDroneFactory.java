package com.blusalt.dronemgtsystem.operation.drone;

import com.blusalt.dronemgtsystem.dtos.DroneDto;
import com.blusalt.dronemgtsystem.enums.DroneModel;
import com.blusalt.dronemgtsystem.model.Drone;

public class MiddleweightDroneFactory extends DroneBaseFactory {
    @Override
    public Drone createDrone(DroneDto droneDto, DroneModel droneModel) {
        return createDrone(droneDto, DroneModel.MIDDLEWEIGHT);
    }

}

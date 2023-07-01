package com.blusalt.dronemgtsystem.operation.drone;

import com.blusalt.dronemgtsystem.dtos.DroneDto;
import com.blusalt.dronemgtsystem.model.Drone;

public interface DroneFactory {
    Drone createDrone(DroneDto droneDto);
}

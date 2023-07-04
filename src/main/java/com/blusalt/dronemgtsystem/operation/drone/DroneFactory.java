package com.blusalt.dronemgtsystem.operation.drone;

import com.blusalt.dronemgtsystem.dtos.DroneDto;
import com.blusalt.dronemgtsystem.enums.DroneModel;
import com.blusalt.dronemgtsystem.model.Drone;


public interface DroneFactory {
   Drone createDrone(DroneDto droneDto, DroneModel droneModel);
   Drone createLightweightDrone(DroneDto droneDto);
   Drone createMiddleweightDrone(DroneDto droneDto);
   Drone createHeavyweightDrone(DroneDto droneDto);
   Drone createCruiserweightDrone(DroneDto droneDto);
}

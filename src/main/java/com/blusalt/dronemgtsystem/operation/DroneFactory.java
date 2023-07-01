package com.blusalt.dronemgtsystem.operation;

import com.blusalt.dronemgtsystem.enums.DroneModel;
import com.blusalt.dronemgtsystem.enums.DroneState;
import com.blusalt.dronemgtsystem.model.Drone;

public class DroneFactory {
    public static Drone createdDrone(String serialNumber, String model, double weight, int batteryCapacity) {
        Drone drone = new Drone();
        drone.setModel(DroneModel.valueOf(model));
        drone.setBatteryCapacity(batteryCapacity);
        drone.setWeightLimit(weight);
        drone.setState(DroneState.IDLE);
        return drone;
    }
}

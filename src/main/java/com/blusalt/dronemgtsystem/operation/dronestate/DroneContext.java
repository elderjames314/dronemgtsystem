package com.blusalt.dronemgtsystem.operation.dronestate;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blusalt.dronemgtsystem.enums.DroneStateName;
import com.blusalt.dronemgtsystem.model.Medication;
import com.blusalt.dronemgtsystem.repository.BatteryAuditLogRepository;
import com.blusalt.dronemgtsystem.repository.DroneRepository;

@Component
@RequiredArgsConstructor
public class DroneContext extends Observable {
    private DroneState currentState;
    private List<Medication> medications;
    private int batteryCapacity;
    private Long droneId;
    private List<Observer> observers;


    public DroneContext(Long droneId) {
        currentState = new IdleState();
        this.droneId = droneId;
    }

    public List<Observer> getObservers() {
        synchronized (this) {
            return new ArrayList<>(observers);
        }
    }

    public void updateBatteryLevel(int batteryLevel) {
        setBatteryCapacity(batteryLevel);

        // Notify the observers about the updated battery level
        notifyObservers();
    }

    public void setDroneId(Long droneId) {
        this.droneId = droneId;
    }

    public Long getDroneId() {
        return droneId;
    }

    public void loadMedications(List<Medication> medications) {
        currentState.handleLoadMedication(this, medications);

        // Decrease the battery level after loading medications
        decreaseBatteryLevel();

        notifyObservers();
    }

    public void addObserver(Observer observer) {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(observer);
    }


    private void decreaseBatteryLevel() {
        // Assumption: I decrease the battery level by 10% upon each loading....
        double decreasePercentage = 0.10;
        int currentBatteryLevel = getBatteryCapacity();
        int decreasedBatteryLevel = (int) (currentBatteryLevel - (currentBatteryLevel * decreasePercentage));

        setBatteryCapacity(decreasedBatteryLevel);

        BatteryLevelObserver batteryLevelObserver = new BatteryLevelObserver(droneId);
        addObserver(batteryLevelObserver);
    }

    public void checkBatteryLevel() {
        currentState.handleBatteryCheck(this);
    }

    public void returnToBase() {
        currentState.handleReturn(this);
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void changeState(DroneState newState) {
        synchronized (this) {
            currentState = newState;
            setChanged();
            notifyObservers();
        }
    }

    public DroneStateName getCurrentState() {
        return currentState.getStateName();
    }

    public void notifyObservers() {
        synchronized (this) {
            if (observers != null) {
                for (Observer observer : observers) {
                    observer.update(this, this);
                }
            }
        }
    }
}

package com.blusalt.dronemgtsystem.operation.dronestate;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.springframework.beans.factory.annotation.Autowired;

import com.blusalt.dronemgtsystem.enums.DroneStateName;
import com.blusalt.dronemgtsystem.model.Medication;

public class DroneContext extends Observable {
    private DroneState currentState;
    private List<Medication> medications;
    private int batteryCapacity;
    private Long droneId;
    private List<Observer> observers;

    @Autowired
    private BatteryLevelAuditLogger batteryLevelAuditLogger;

    public DroneContext(Long droneId) {
        currentState = new IdleState();
        this.droneId = droneId;
    }

    public List<Observer> getObservers() {
        synchronized (this) {
            return new ArrayList<>(observers);
        }
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

        // Pass the droneId to the BatteryLevelObserver's constructor
        BatteryLevelObserver batteryLevelObserver = new BatteryLevelObserver(getDroneId(), batteryLevelAuditLogger);

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
            setChanged(); // Notify observers that the state has changed
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
